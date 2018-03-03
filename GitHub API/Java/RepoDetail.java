package com.example.carloscampos.github_carlosapi;

/**
 * Created by carloscampos on 10/02/18.
 */

public class RepoDetail {

    private String reponame,repodescription,repolanguage,repocreated;

    public RepoDetail(String reponame, String repodescription, String repolanguage, String repocreated) {
        this.setReponame(reponame);
        this.setRepodescription(repodescription);
        this.setRepolanguage(repolanguage);
        this.setRepocreated(repocreated);
    }
    //Getters
    public String getReponame() {
        return reponame;
    }

    public String getRepodescription() {
        return repodescription;
    }

    public String getRepolanguage() {
        return repolanguage;
    }

    public String getRepocreated() {
        return repocreated;
    }

    //Setters
    public void setReponame(String reponame) {
        if (reponame != "null") {
            this.reponame = reponame;
        } else {
            this.reponame = "I don't have a name.., sorry";
        }
    }

    public void setRepodescription(String repodescription) {
        if (repodescription != "null") {
            this.repodescription = repodescription;
        } else {
            this.repodescription = "Sorry, i can't find an description for this repo..";
        }
    }

    public void setRepolanguage(String repolanguage) {
        if (repolanguage != "null") {
            this.repolanguage = repolanguage;
        }
        else{
            this.repolanguage="Sorry, i can't find an description for this repo..";
        }
    }

    public void setRepocreated(String repocreated) {
        if(repocreated!= "null"){
            this.repocreated = repocreated;
        }else{
            this.repocreated="Creation date is not available";
        }
    }
}
