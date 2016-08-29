package com.andrey.secondretrofi.retrofit;


import com.andrey.secondretrofi.models.AnswerTest;
import com.andrey.secondretrofi.models.ApiRequest2;
import com.andrey.secondretrofi.models.Rebus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ServiceGenerator {

    private static ArtistInfoApi instance;

    public static final String baseUrl = "http://studio1101.co.nf/";

    public interface ArtistInfoApi {

        @POST("test.php")
        Call<Rebus> getAnswerTest(@Body ApiRequest2 apiRequest2);
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