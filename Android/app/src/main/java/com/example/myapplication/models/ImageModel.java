package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class ImageModel {
    @SerializedName("id")
    private int id;
    @SerializedName("url")
    private String url;

    public ImageModel() {
    }

    public ImageModel(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public ImageModel(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
