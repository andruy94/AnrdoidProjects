package com.andrey.secondretrofi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Андрей on 07.07.2016.
 */
public class Rebus {
    @SerializedName("id")//Анотация для Сериализации
    @Expose
    private List<Integer> Pic_id;

    @SerializedName("Url")
    @Expose
    private List<String> Url;

    @SerializedName("Answer")
    @Expose
    private List<String> Answer;

    @SerializedName("Name")
    @Expose
    private List<String> FileName;

    @SerializedName("Points")
    @Expose
    private List<Integer> Points;

    @SerializedName("Hint")
    @Expose
    private List<String> Hint;



    @Override
    public String toString() {
        return "Pic_id"+
                Pic_id+
                "\nAnswer"+
                Answer+
                "\nHint"+
                Hint+
                "\nPoints"+
                Points+
                "\nFileName"+
                FileName;
    }

    public List<Integer> getPic_id() {
        return Pic_id;
    }

    public void setPic_id(List<Integer> pic_id) {
        Pic_id = pic_id;
    }

    public List<String> getAnswer() {
        return Answer;
    }

    public void setAnswer(List<String> answer) {
        Answer = answer;
    }

    public List<String> getHint() {
        return Hint;
    }

    public void setHint(List<String> hint) {
        Hint = hint;
    }

    public List<String> getUrl() {
        return Url;
    }

    public void setUrl(List<String> url) {
        Url = url;
    }

    public List<Integer> getPoints() {
        return Points;
    }

    public void setPoints(List<Integer> points) {
        Points = points;
    }

    public List<String> getFileName() {
        return FileName;
    }

    public void setFileName(List<String> fileName) {
        FileName = fileName;
    }
}
