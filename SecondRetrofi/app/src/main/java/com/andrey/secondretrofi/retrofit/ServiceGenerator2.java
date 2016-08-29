package com.andrey.secondretrofi.retrofit;


import com.andrey.secondretrofi.models.AnswerTest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ServiceGenerator2 {

    private static ArtistInfoApi instance;

    public static final String baseUrl = "http://studio1101.co.nf/";

    public interface ArtistInfoApi {

        @POST("api1.php")
        Call<AnswerTest> getAnswerTest(@Body AnswerTest answerTest);
    }

    //@Field for POST Request

    public static ArtistInfoApi getInstance() {
        if (instance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
            instance = retrofit.create(ArtistInfoApi.class);
            return instance;
        }
        return instance;
    }
}