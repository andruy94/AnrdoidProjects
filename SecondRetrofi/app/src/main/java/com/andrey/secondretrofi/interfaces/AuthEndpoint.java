package com.andrey.secondretrofi.interfaces;



import com.andrey.secondretrofi.models.ApiRequest;
import com.andrey.secondretrofi.models.Rebus;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthEndpoint {
    @FormUrlEncoded
    @POST("api1.php")


    Call<Rebus> getRebus(
            @Field("Points") String request
            );
}
