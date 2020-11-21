package com.example.myapplication.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.util.List;

public class ProblemsCluster implements ClusterItem {
    private int id;
    private LatLng position;
    private String postedDate;
    private String description;
    private boolean isResolved;
    private List<String> photosUrl;

    public ProblemsCluster(int id, LatLng position, String title, String snippet) {
        this.id = id;
        this.position = position;
        this.postedDate = title;
        this.description = snippet;
    }

    public ProblemsCluster(int id, LatLng position, String postedDate, String description, boolean isResolved, List<String> photosUrl) {
        this.id = id;
        this.position = position;
        this.postedDate = postedDate;
        this.description = description;
        this.isResolved = isResolved;
        this.photosUrl = photosUrl;
    }

    public int getId() {
        return id;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return position;
    }

    @Nullable
    @Override
    public String getTitle() {
        return "";
    }

    @Nullable
    @Override
    public String getSnippet() {
        return "";
    }

    public boolean isResolved() {
        return isResolved;
    }

    public List<String> getPhotosUrl() {
        return photosUrl;
    }

    @Nullable
    public String getPostedDate() {
        return postedDate;
    }

    @Nullable
    public String getDescription() {
        return description;
    }
}
