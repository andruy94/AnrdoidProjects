package com.example.macbookair.myapplication.interfaces;


import com.example.macbookair.myapplication.models.Esse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface StudEndpoint {

    @FormUrlEncoded
    @POST("go")
    Call<List<Esse>> getEsseList(//абстарктный метод
            @Field("ssearch")//пишем в переменную ssearch
            String searchInput,
            @Field("searchLang")//пишем в переменную searchLang
            String searchLang
    );
}
