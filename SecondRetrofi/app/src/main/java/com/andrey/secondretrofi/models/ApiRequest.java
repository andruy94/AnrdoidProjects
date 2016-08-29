package com.andrey.secondretrofi.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 07.07.2016.
 */
public class ApiRequest {
   public  String login;
    public ApiRequest(  String login  ){

        this.login=login;
    }
    /*public List<Integer> points = new ArrayList<Integer>();
    public String password;
    public List<Integer> picsId = new ArrayList<Integer>();
    public String login;
    public ApiRequest(  String login,
    String password,
    List<Integer> picsId,
    List<Integer> points
     ){
        this.points=points;
        this.password=password;
        this.picsId=picsId;
        this.login=login;
    }*/
}
