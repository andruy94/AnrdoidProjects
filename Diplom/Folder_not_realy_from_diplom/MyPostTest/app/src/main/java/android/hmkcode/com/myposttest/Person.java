package android.hmkcode.com.myposttest;

/**
 * Created by Андрей on 30.03.2016.
 */

public class Person {

    private String name;
    private String country;
    private String twitter;

//getters & setters....
    public void setName(String name){
        this.name=name;
    }

    public void setCountry(String country){
        this.country=country;
    }

    public void setTwitter(String twitter){
        this.twitter=twitter;
    }

    public String getName(){
        return name;
    }

    public String getCountry(){
        return country;
    }

    public String getTwitter(){
        return twitter;
    }

}
