package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Voters {
    @SerializedName("id")
    private int id;

    @SerializedName("identifier")
    private String identifier;

    @SerializedName("serial")
    private String serial;

    @SerializedName("initiatives")
    private List<String> initiatives;

    public Voters(int id, String identifier, String serial, List<String> initiatives) {
        this.id = id;
        this.identifier = identifier;
        this.serial = serial;
        this.initiatives = initiatives;
    }

    public Voters() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public List<String> getInitiatives() {
        return initiatives;
    }

    public void setInitiatives(List<String> initiatives) {
        this.initiatives = initiatives;
    }
}
