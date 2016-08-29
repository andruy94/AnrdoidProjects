package com.andrey.secondretrofi.retrofit;


import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public static <S> S createService(Class<S> serviceClass,String Url) {
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(Url)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

}
/*
            //добавим интерсептор, чтобы повысить уровень логирования и отдебажить запросы
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));*/