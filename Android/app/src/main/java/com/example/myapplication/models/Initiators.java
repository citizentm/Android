package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class Initiators {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public Initiators(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Initiators() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
