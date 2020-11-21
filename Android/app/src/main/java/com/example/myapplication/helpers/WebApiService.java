package com.example.myapplication.helpers;

import com.example.myapplication.models.ProblemModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebApiService {
    @GET("repos")
    Call<ProblemModel> getData();
}
