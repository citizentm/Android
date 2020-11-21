package com.example.myapplication.helpers;

import com.example.myapplication.models.ProblemModel;

import java.util.List;

public class StorageHelper {
    private static StorageHelper storageHelper;
    private List<ProblemModel> problemsList;

    public static StorageHelper getInstance(){
        if (storageHelper == null){
            storageHelper = new StorageHelper();
        }
        return storageHelper;
    }

    public List<ProblemModel> getProblemsList() {
        return problemsList;
    }

    public void setProblemsList(List<ProblemModel> problemsList) {
        this.problemsList = problemsList;
    }
}
