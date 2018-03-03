package com.example.carloscampos.github_carlosapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Actividade_principal extends Activity {

    //GLOBAL VARIABLES
    ProgressDialog pd;
    static List<RepoDetail> repoDetails;
    ListView lv;

    /* LIFE CICLE*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividade_principal);
        repoDetails = new ArrayList<>();
        lv = findViewById(R.id.repos_list);
        Requests repos_call = new Requests("https://api.github.com/users/CarlosMiguelAzevedoCampos/repos");
        repos_call.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    public void onStop() {
        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
    /*END OF LIFE CICLE*/

    /*REQUEST's*/
    protected class Requests extends AsyncTask<Void, Void, String> {
        String url;

        Requests(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Actividade_principal.this);
            pd.setMessage("Loading..");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpHandler handler = new HttpHandler();
            String aux = handler.makeServiceCall(url);
            return aux;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();

            try {
                JSONArray obj = new JSONArray(s);
                if(s!=null) //check if the return is null
                    RefreshListDetail(obj);
            } catch (JSONException e) {
                Toast.makeText(Actividade_principal.this, "Ocorreu um erro na comunicaçaõ", Toast.LENGTH_SHORT).show();
                Log.e("Fail", "JSONException: " + e.getMessage());
            }
        }
    }

    private void RefreshListDetail(JSONArray obj) throws JSONException {
        repoDetails.clear();
        for (int i = 0; i < obj.length(); i++) {
            repoDetails.add(new RepoDetail(obj.getJSONObject(i).getString("name"),
                    obj.getJSONObject(i).getString("description"),
                    obj.getJSONObject(i).getString("language"),
                    obj.getJSONObject(i).getString("created_at")
                    ));
        }

        RepoDisplay detail = new RepoDisplay(repoDetails);
        lv.setAdapter(detail);
    }
    /*END REQUEST*/

    /*FILL FIELDS*/
    class RepoDisplay extends ArrayAdapter<RepoDetail> {

        public RepoDisplay(List<RepoDetail> repoDetail) {
            super(Actividade_principal.this, R.layout.repo_info, repoDetail);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listItem = inflater.inflate(R.layout.repo_info, null, true);
            TextView repo_name = listItem.findViewById(R.id.repo_name_text);
            TextView repo_description = listItem.findViewById(R.id.repo_description_text);
            TextView repo_date = listItem.findViewById(R.id.repo_date_text);
            TextView repo_language = listItem.findViewById(R.id.repo_language_text);
            Button btn_more = listItem.findViewById(R.id.btn_more);

            repo_name.setText(repoDetails.get(position).getReponame());
            repo_description.setText(repoDetails.get(position).getRepodescription());
            repo_date.setText(repoDetails.get(position).getRepocreated());
            repo_language.setText(repoDetails.get(position).getRepolanguage());

            btn_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenAnotherActivity(Actividade_repo_files.class, repoDetails.get(position).getReponame());
                }
            });
            return listItem;
        }
    }

    /*END FILL FIELDS*/


    /*Open New Activity*/
    private void OpenAnotherActivity(Class<?> subActivity, String reponame) {
        Intent newActivity = new Intent(this, subActivity);
        newActivity.putExtra("reponame", reponame); //get's the repo name
        startActivity(newActivity);
    }
    /*END NEW ACTIVITY*/
}
