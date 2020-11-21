package com.example.myapplication.helpers;

import android.util.Log;

import com.example.myapplication.models.ProblemModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClientManager {
    public interface OnDataReceived<T> {
        /**
         * This method is called whenever an api call has been successful.
         *
         * @param data The data from the api call with a generic type.
         */
        void dataReceived(T data);

        /**
         * This method is called whenever an api call has failed.
         */
        void onFailed();
    }

    private static final HttpClientManager instance = new HttpClientManager();
    private WebApiService service;

    public static HttpClientManager getInstance() {
        return instance;
    }

    private HttpClientManager() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Gson gson = new GsonBuilder().
                setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://citizentm.arpadgabor.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        service = retrofit.create(WebApiService.class);
    }

    public void getProblems(final OnDataReceived<List<ProblemModel>> callback){
        Call<List<ProblemModel>> serverCall = service.getData();
        serverCall.enqueue(new Callback<List<ProblemModel>>() {
            @Override
            public void onResponse(Call<List<ProblemModel>> call, Response<List<ProblemModel>> response) {
                if (response.isSuccessful())
                {
                    List<ProblemModel> myResponse = response.body();
                    callback.dataReceived(myResponse);
                }
            }

            @Override
            public void onFailure(Call<List<ProblemModel>> call, Throwable t) {

            }
        });
    }

    public void postProblem(ProblemModel problemModel, final OnDataReceived<ProblemModel> callback){
        Call<ProblemModel> serverCall = service.postIssue(problemModel);
        serverCall.enqueue(new Callback<ProblemModel>() {
            @Override
            public void onResponse(Call<ProblemModel> call, Response<ProblemModel> response) {
                if (response.isSuccessful())
                {
                    ProblemModel myResponse = response.body();
                    callback.dataReceived(myResponse);
                }
            }

            @Override
            public void onFailure(Call<ProblemModel> call, Throwable t) {

            }
        });

    }
}