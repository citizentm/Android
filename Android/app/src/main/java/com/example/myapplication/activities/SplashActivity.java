package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.helpers.HttpClientManager;
import com.example.myapplication.helpers.StorageHelper;
import com.example.myapplication.models.ProblemModel;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();

        HttpClientManager.getInstance().getProblems(new HttpClientManager.OnDataReceived<List<ProblemModel>>() {
            @Override
            public void dataReceived(List<ProblemModel> data) {
                if (StorageHelper.getInstance().getProblemsList() == null)
                {
                    StorageHelper.getInstance().setProblemsList(data);
                }
                else {
                    StorageHelper.getInstance().getProblemsList().addAll(data);
                }
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }

            @Override
            public void onFailed() {

            }
        });
    }
}