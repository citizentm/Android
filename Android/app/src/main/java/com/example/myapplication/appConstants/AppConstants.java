package com.example.myapplication.appConstants;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AppConstants {
    public final static String PROJECT_MODEL_KEY = "projectModelKey";
    public final static String SHARED_PREFS_NAME = "voteDetails";
    public final static String SAVED_CNP = "savedCnp";
    public final static String SAVED_ID_CARD = "savedIdCard";
    public final static String ON_ADD_ISSUE = "onAddIssue";
    public final static int INTENT_RESULT_NUMBER = 9531;
    public final static StorageReference FIREBASE_PROBLEMS_PHOTOS = FirebaseStorage.getInstance().getReference("problems");

}
