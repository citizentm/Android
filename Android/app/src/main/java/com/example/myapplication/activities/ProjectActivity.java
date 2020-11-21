package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.appConstants.AppConstants;
import com.example.myapplication.models.ProjectModel;
import com.google.gson.Gson;

public class ProjectActivity extends AppCompatActivity {

    private TextView titleTv;
    private TextView descriptionTv;
    private TextView financingNeededTv;
    private TextView ownersTv;
    private TextView upvotesTv;
    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        initializeViews();
        new ProjectModel();
        Gson gson = new Gson();
        String aux = getIntent().getStringExtra(AppConstants.PROJECT_MODEL_KEY);
        ProjectModel projectModel = gson.fromJson(aux, ProjectModel.class);
        postId = projectModel.getId();
        setValues(projectModel);
    }

    public static String floatToLong(float d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

    private void setValues(ProjectModel projectModel) {
        titleTv.setText(projectModel.getName());
        descriptionTv.setText(projectModel.getDescription());
        financingNeededTv.setText(floatToLong(projectModel.getFinancingNeededAmount()) +" lei");
        ownersTv.setText(projectModel.getOwnersDetails());
        upvotesTv.setText(String.valueOf(projectModel.getVotes()));
    }

    private void initializeViews() {
        titleTv= findViewById(R.id.tv_project_title);
        descriptionTv= findViewById(R.id.tv_project_description);
        financingNeededTv= findViewById(R.id.tv_project_financing);
        ownersTv= findViewById(R.id.tv_project_owners);
        upvotesTv= findViewById(R.id.tv_project_upvotes);
    }

    public void voteForThisProject(View view) {

    }
}