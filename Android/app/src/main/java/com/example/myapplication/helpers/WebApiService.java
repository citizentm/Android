package com.example.myapplication.helpers;

import com.example.myapplication.models.InitiativeModel;
import com.example.myapplication.models.ProblemModel;
import com.example.myapplication.models.Voters;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebApiService {
    @Headers("Content-Type: application/json")
    @GET("problems")
    Call<List<ProblemModel>> getData();

    @POST("problems")
    Call<ProblemModel> postIssue(@Body ProblemModel model);

    @GET("initiatives")
    Call<List<InitiativeModel>> getInitiatives();

    @POST("voters/{id}")
    Call<List<InitiativeModel>> postVote(@Path(value = "id") int id, @Body Voters voter);
}
