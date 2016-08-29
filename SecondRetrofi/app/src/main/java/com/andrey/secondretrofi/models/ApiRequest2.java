package com.andrey.secondretrofi.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andruy94 on 8/29/2016.
 */
public class ApiRequest2 {

    public String login;
    public String password;
    public List<Integer> Pics_id = new ArrayList<Integer>();
    public List<Integer> Points = new ArrayList<Integer>();

    public ApiRequest2(  String login,
                        String password,
                        List<Integer> picsId,
                        List<Integer> points
    ){
       this.Points=points;
        this.password=password;
        this.Pics_id=picsId;
        this.login=login;
    }
}
