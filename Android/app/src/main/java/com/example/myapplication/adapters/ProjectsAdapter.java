package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.activities.ProjectActivity;
import com.example.myapplication.models.ProjectModel;
import com.google.gson.Gson;

import java.util.List;

import static com.example.myapplication.activities.ProjectActivity.floatToLong;
import static com.example.myapplication.appConstants.AppConstants.PROJECT_MODEL_KEY;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsViewHolder> {
    private List<ProjectModel> projectsList;
    private Context context;

    @NonNull
    @Override
    public ProjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.row_projects, parent, false);
        return new ProjectsViewHolder(v);
    }

    public ProjectsAdapter(List<ProjectModel> projectsList) {
        this.projectsList = projectsList;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProjectsViewHolder holder, int position) {
        final ProjectModel projectModel = projectsList.get(position);
        holder.setValues(projectModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProjectActivity.class);
                Gson gson = new Gson();
                String toSend = gson.toJson(projectModel);
                intent.putExtra(PROJECT_MODEL_KEY, toSend);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }
}

class ProjectsViewHolder extends RecyclerView.ViewHolder{
    private TextView nameTv;
    private TextView descriptionTv;
    private TextView amountNeededTv;
    private TextView votesTv;

    public ProjectsViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews(itemView);
    }

    public void setValues(ProjectModel projectModel){
        if (projectModel == null){
            return;
        }
        nameTv.setText(projectModel.getName());
        descriptionTv.setText(projectModel.getDescription());
        amountNeededTv.setText(floatToLong(projectModel.getFinancingNeededAmount()) + " lei");
        votesTv.setText(String.valueOf(projectModel.getVotes()));
    }

    private void initializeViews(View itemView) {
        nameTv = itemView.findViewById(R.id.tv_row_projects_name);
        descriptionTv = itemView.findViewById(R.id.tv_row_projects_description);
        amountNeededTv = itemView.findViewById(R.id.tv_row_projects_amount);
        votesTv = itemView.findViewById(R.id.tv_row_projects_upvotes);
    }
}
