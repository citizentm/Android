package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.ProjectModel;

import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsViewHolder> {
    private List<ProjectModel> projectsList;

    @NonNull
    @Override
    public ProjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_projects, parent, false);
        return new ProjectsViewHolder(v);
    }

    public ProjectsAdapter(List<ProjectModel> projectsList) {
        this.projectsList = projectsList;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }
}

class ProjectsViewHolder extends RecyclerView.ViewHolder{
    private TextView name;
    private TextView description;
    private TextView amountNeeded;
    private TextView votes;

    public ProjectsViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews(itemView);
    }

    private void initializeViews(View itemView) {
        name = itemView.findViewById(R.id.tv_row_projects_name);
    }
}
