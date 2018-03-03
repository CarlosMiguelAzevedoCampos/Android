package com.example.carloscampos.github_carlosapi;

/**
 * Created by carloscampos on 10/02/18.
 */

public class RepoFiles {

    private String filename;
    private String filetype;
    private String downloadurl;
    private int size;

    public RepoFiles(String filename, String filetype, String downloadurl, int size){
           setFilename(filename);
           setFiletype(filetype);
           setDownloadurl(downloadurl);
           setSize(size);
    }

    //Getters
    public String getFilename() {
        return filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public int getSize() {
        return size;
    }
    public String getDownloadurl() {
        return downloadurl;
    }

    //Setters
    private void setFilename(String filename) {
        if(filename!="null") {
            this.filename = filename;
        }
        else{
            this.filename = "I don't have a name, sorry..";
        }
    }
    private void setFiletype(String filetype) {
        if (filetype != "null") {
            this.filetype = filetype;
        }
        else{
            this.filetype = "I don't my Type..";
        }
    }

    private void setDownloadurl(String downloadurl) {
        if(downloadurl != "null") {
            this.downloadurl = downloadurl;
        }
        else{
            this.downloadurl = "Download Link is not available";
        }
    }

    private void setSize(int size) {
        this.size = size;
    }
}
