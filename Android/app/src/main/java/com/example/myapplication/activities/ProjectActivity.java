package com.example.myapplication.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.appConstants.AppConstants;
import com.example.myapplication.models.InitiativeModel;
import com.example.myapplication.models.Initiators;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

public class ProjectActivity extends AppCompatActivity {

    private TextView titleTv;
    private TextView descriptionTv;
    private TextView financingNeededTv;
    private TextView ownersTv;
    private TextView upvotesTv;
    private int projectId;
    private String projectName;
    private String cnp = "";
    private String idCard = "";
    private InitiativeModel initiativeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        initializeViews();
        new InitiativeModel();
        Gson gson = new Gson();
        String aux = getIntent().getStringExtra(AppConstants.PROJECT_MODEL_KEY);
        initiativeModel = gson.fromJson(aux, InitiativeModel.class);
        projectId = initiativeModel.getId();
        projectName = initiativeModel.getName();
        setValues(initiativeModel);
        getDataFromSharedPreferences();
    }

    public static String floatToLong(float d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

    private void setValues(InitiativeModel initiativeModel) {
        String euro = "\u20ac";
        titleTv.setText(initiativeModel.getName());
        descriptionTv.setText(initiativeModel.getDescription());
        financingNeededTv.setText(floatToLong(initiativeModel.getFinancingNeededAmount()) +euro);
        ownersTv.setText(initiativeModel.getOwnersDetails());
        upvotesTv.setText(String.valueOf(initiativeModel.getVotes()));
        StringBuilder initiators = new StringBuilder();
        if (initiativeModel.getInitiatorsList()== null || initiativeModel.getInitiatorsList().size() ==0){
            return;
        }
        boolean first = true;
        for (Initiators initiator: initiativeModel.getInitiatorsList()) {
            if (first){
                initiators.append(initiator.getName());
                first= false;
            }
            else{
                initiators.append(", " + initiator.getName());
            }
        }
        ownersTv.setText(initiators.toString());
    }

    private void initializeViews() {
        titleTv= findViewById(R.id.tv_project_title);
        descriptionTv= findViewById(R.id.tv_project_description);
        financingNeededTv= findViewById(R.id.tv_project_financing);
        ownersTv= findViewById(R.id.tv_project_owners);
        upvotesTv= findViewById(R.id.tv_project_upvotes);
    }

    public void voteForThisProject(View view) {
        final TextInputEditText inputEditText;
        TextInputLayout textInputLayout;
        final TextInputEditText seriesEditText;
        TextInputLayout seriesInputLayout;
        final CheckBox saveDataForFutureVotesCb;
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        new AlertDialog.Builder(this, R.style.InputDialogTheme);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.view_vote_input, (ViewGroup) findViewById(R.id.et_input_cnp) , false);
        inputEditText = viewInflated.findViewById(R.id.et_input_cnp);
        textInputLayout = viewInflated.findViewById(R.id.til_input_cnp);
        seriesEditText = viewInflated.findViewById(R.id.et_input_series);
        seriesInputLayout = viewInflated.findViewById(R.id.til_input_series);
        saveDataForFutureVotesCb = viewInflated.findViewById(R.id.cb_input_remember);
        saveDataForFutureVotesCb.setChecked(true);
        alert.setView(viewInflated);
        alert.setTitle("Vote for " + projectName + " ?");
        textInputLayout.setHint("CNP");
        seriesInputLayout.setHint("ID Card");
        if(!cnp.isEmpty()){
            inputEditText.setText(cnp);
        }
        if (!idCard.isEmpty()){
            seriesEditText.setText(idCard);
        }
        alert.setPositiveButton("Vote", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (inputEditText.getText() == null || inputEditText.getText().toString().isEmpty())
                {
                    return;
                }
                if (seriesEditText.getText() == null || seriesEditText.getText().toString().isEmpty())
                {
                    return;
                }
                cnp = inputEditText.getText().toString();
                idCard= seriesEditText.getText().toString();
                if (saveDataForFutureVotesCb.isChecked()){
                    saveDetailsInSharedPreferences(cnp, idCard);
                }
                upvotesTv.setText(String.valueOf(initiativeModel.getVotes() + 1));
                Toast.makeText(ProjectActivity.this, "Thank your for being a part of the community!", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }

    private void getDataFromSharedPreferences(){
        SharedPreferences prefs = getSharedPreferences(AppConstants.SHARED_PREFS_NAME, MODE_PRIVATE);
        cnp = prefs.getString(AppConstants.SAVED_CNP, "");
        idCard = prefs.getString(AppConstants.SAVED_ID_CARD, "");
    }

    private void saveDetailsInSharedPreferences(String cnpNumber, String idCardSeries){
        SharedPreferences.Editor editor = getSharedPreferences(AppConstants.SHARED_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(AppConstants.SAVED_CNP, cnpNumber);
        editor.putString(AppConstants.SAVED_ID_CARD, idCardSeries);
        editor.apply();
    }
}