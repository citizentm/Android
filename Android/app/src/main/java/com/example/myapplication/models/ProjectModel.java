package com.example.myapplication.models;

public class ProjectModel {
    private int id;
    private String name;
    private String financingDate;
    private String description;
    private float financingNeededAmount;
    private String ownersDetails;
    private int votes;

    public ProjectModel(int id, String name, String financingDate, String description, float financingNeededAmount, String ownersDetails, int votes) {
        this.id = id;
        this.name = name;
        this.financingDate = financingDate;
        this.description = description;
        this.financingNeededAmount = financingNeededAmount;
        this.ownersDetails = ownersDetails;
        this.votes = votes;
    }

    public ProjectModel(String name, String description, float financingNeededAmount, int votes) {
        this.name = name;
        this.description = description;
        this.financingNeededAmount = financingNeededAmount;
        this.votes = votes;
    }

    public ProjectModel() {
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

    public String getFinancingDate() {
        return financingDate;
    }

    public void setFinancingDate(String financingDate) {
        this.financingDate = financingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getFinancingNeededAmount() {
        return financingNeededAmount;
    }

    public void setFinancingNeededAmount(float financingNeededAmount) {
        this.financingNeededAmount = financingNeededAmount;
    }

    public String getOwnersDetails() {
        return ownersDetails;
    }

    public void setOwnersDetails(String ownersDetails) {
        this.ownersDetails = ownersDetails;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
