package com.andrey.secondretrofi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andruy94 on 8/29/2016.
 */
public class AnswerTest {
    @SerializedName("login")
    @Expose
    public String login;

    public AnswerTest(String login){
        this.login=login;
    }
}
