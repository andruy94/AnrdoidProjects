package com.example.macbookair.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by macbookair on 25.05.16.
 */
public class Esse extends RealmObject{
    @PrimaryKey
    @SerializedName("referat_id")//Анатация для Сериализации
    @Expose
    private String referatId;
    @SerializedName("page_count")
    @Expose
    private String pageCount;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("language_id")
    @Expose
    private String languageId;
    @SerializedName("discipline_id")
    @Expose
    private String disciplineId;
    @SerializedName("referat_type_id")
    @Expose
    private String referatTypeId;

    private String searchInput;



    public Esse (String referatId, String pageCount, String title, String languageId, String disciplineId, String referatTypeId) {
        this.referatId = referatId;
        this.pageCount = pageCount;
        this.title = title;
        this.languageId = languageId;
        this.disciplineId = disciplineId;
        this.referatTypeId = referatTypeId;
    }

    public Esse(){

    }
    public String getReferatId() {
            return referatId;
        }

    public void setReferatId(String referatId) {
            this.referatId = referatId;
        }

    public String getPageCount() {
            return pageCount;
        }

    public void setPageCount(String pageCount) {
            this.pageCount = pageCount;
        }

    public String getTitle() {
            return title;
        }

    public void setTitle(String title) {
            this.title = title;
        }

    public String getLanguageId() {
            return languageId;
        }

    public void setLanguageId(String languageId) {
            this.languageId = languageId;
        }

    public String getDisciplineId() {
            return disciplineId;
        }

    public void setDisciplineId(String disciplineId) {
            this.disciplineId = disciplineId;
        }

    public String getReferatTypeId() {
            return referatTypeId;
        }

    public void setReferatTypeId(String referatTypeId) {
            this.referatTypeId = referatTypeId;
        }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }
}

