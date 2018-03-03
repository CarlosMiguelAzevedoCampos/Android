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

public class Actividade_repo_files extends Activity {

    //GLOBAL VARIABLES
    ProgressDialog pd;
    static List<RepoFiles> repoDetails;
    ListView lv;

    /* LIFE CICLE*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividade_repo_files);
        repoDetails = new ArrayList<>();
        lv = findViewById(R.id.repos_list);
        Intent intent = getIntent(); //Need this to get the extras from the Actividade_principal
        //this is better than "http:\\asdasdas.com\"+reponame+"
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("https://api.github.com/repos/CarlosMiguelAzevedoCampos/");
        urlBuilder.append(intent.getExtras().getString("reponame")); //reponame clicked
        urlBuilder.append("/contents");

        Requests repos_call = new Requests(urlBuilder.toString()); //will make a call to the created url
        repos_call.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onDestroy() {
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
            pd = new ProgressDialog(Actividade_repo_files.this);
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
                Toast.makeText(Actividade_repo_files.this, "Ocorreu um erro na comunicaçaõ", Toast.LENGTH_SHORT).show();
                Log.e("Fail", "JSONException: " + e.getMessage());
            }
        }
    }

    private void RefreshListDetail(JSONArray obj) throws JSONException {
        repoDetails.clear();
        for (int i = 0; i < obj.length(); i++) {
            repoDetails.add(new RepoFiles(obj.getJSONObject(i).getString("name"),
            obj.getJSONObject(i).getString("type"), obj.getJSONObject(i).getString("download_url"),
                    obj.getJSONObject(i).getInt("size")
            ));
        }

        RepoDisplay detail = new RepoDisplay(repoDetails);
        lv.setAdapter(detail);
    }
    /*END REQUEST*/

    /*FILL FIELDS*/
    class RepoDisplay extends ArrayAdapter<RepoFiles> {

        public RepoDisplay(List<RepoFiles> repoDetail) {
            super(Actividade_repo_files.this, R.layout.repos_box, repoDetail);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listItem = inflater.inflate(R.layout.repos_box, null, true);

            TextView file_name = listItem.findViewById(R.id.file_name_text);
            TextView file_size = listItem.findViewById(R.id.file_size_text);
            TextView file_download = listItem.findViewById(R.id.file_download_text);
            TextView file_type = listItem.findViewById(R.id.file_type_text);

            file_name.setText(repoDetails.get(position).getFilename());
            file_size.setText(String.valueOf(repoDetails.get(position).getSize()));
            file_download.setText(repoDetails.get(position).getDownloadurl());
            file_type.setText(repoDetails.get(position).getFiletype());
            return listItem;
        }
    }

    /*END FILL FIELDS*/
}
