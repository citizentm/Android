package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.ProjectsAdapter;
import com.example.myapplication.helpers.HttpClientManager;
import com.example.myapplication.models.InitiativeModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectsFragment extends Fragment {

    private RecyclerView projectsListRv;
    private TextView noProjectsTv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_projects, container, false);
        initializeViews(root);
        getProjectsList();
        return root;
    }

    private void getProjectsList(){
        HttpClientManager.getInstance().getInitiatives(new HttpClientManager.OnDataReceived<List<InitiativeModel>>() {
            @Override
            public void dataReceived(List<InitiativeModel> data) {
                setRecyclerView(data);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    private void setRecyclerView(List<InitiativeModel> projectsList) {
        if (projectsList == null || projectsList.size() == 0){
            noProjectsTv.setVisibility(View.VISIBLE);
        }
        projectsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        ProjectsAdapter projectsAdapter = new ProjectsAdapter(projectsList);
        projectsListRv.setAdapter(projectsAdapter);
    }

    private void initializeViews(View root) {
        projectsListRv = root.findViewById(R.id.rv_projects_list);
        noProjectsTv = root.findViewById(R.id.tv_projects_no_projects);
    }
}