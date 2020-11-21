package com.example.myapplication.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProblemModel implements Serializable {
    @SerializedName("id")
    private int id;

    private LatLng position;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("description")
    private String description;

    @SerializedName("created_at")
    private String postedDate;

    @SerializedName("resolvedAt")
    private String resolvedDate;

    @SerializedName("isResolved")
    private boolean isResolved;

    private List<String> photosUrlList;

    @SerializedName("images")
    private List<ImageModel> photosList;

    @SerializedName("votes")
    private int upvotes;

    public ProblemModel(int id, LatLng position, String description, String postedDate, List<String> photosUrlList, boolean isResolved) {
        this.id = id;
        this.isResolved = isResolved;
        this.position = position;
        this.description = description;
        this.postedDate = postedDate;
        this.photosUrlList = photosUrlList;
    }

    public ProblemModel() {
    }

    public List<ImageModel> getPhotosList() {
        return photosList;
    }

    public void setPhotosList(List<ImageModel> photosList) {
        this.photosList = photosList;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
        this.setLatitude(position.latitude);
        this.setLongitude(position.longitude);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public List<String> getPhotosUrlList() {
        return photosUrlList;
    }

    public void setPhotosUrlList(List<String> photosUrlList) {
        this.photosUrlList = photosUrlList;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(String resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }
}
