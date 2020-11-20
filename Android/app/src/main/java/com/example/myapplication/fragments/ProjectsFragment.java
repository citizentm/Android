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
import com.example.myapplication.models.ProjectModel;

import java.util.ArrayList;

public class ProjectsFragment extends Fragment {

    private RecyclerView projectsListRv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_projects, container, false);
        initializeViews(root);
        setRecyclerView();
        return root;
    }

    private void setRecyclerView() {
        ArrayList<ProjectModel> projectsList = new ArrayList<>();
        projectsList.add(new ProjectModel("Playground Sagului", "A brand new playground for the children of western Timisoara.", 10000, 342));
        projectsList.add(new ProjectModel("Park", "A brand new playground for the children of western Timisoara.", 54302, 1002));

        projectsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        ProjectsAdapter projectsAdapter = new ProjectsAdapter(projectsList);
        projectsListRv.setAdapter(projectsAdapter);
    }

    private void initializeViews(View root) {
        projectsListRv = root.findViewById(R.id.rv_projects_list);
    }
}