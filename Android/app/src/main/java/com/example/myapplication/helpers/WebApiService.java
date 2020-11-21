package com.example.myapplication.helpers;

import com.example.myapplication.models.ProblemModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebApiService {
    @Headers("Content-Type: application/json")
    @GET("problems")
    Call<List<ProblemModel>> getData();

    @POST("problems")
    Call<ProblemModel> postIssue(@Body ProblemModel model);
}
