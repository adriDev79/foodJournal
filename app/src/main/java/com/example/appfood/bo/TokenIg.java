package com.example.appfood.bo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "token_ig")
public class TokenIg {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String token;
    private String date_creation;
    private String date_modification;
    private String date_expiration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDateCreation() {
        return date_creation;
    }

    public void setDateCreation(String date_creation) {
        this.date_creation = date_creation;
    }

    public String getDateExpiration() {
        return date_expiration;
    }

    public void setDateExpiration(String date_expiration) {
        this.date_expiration = date_expiration;
    }

    public String getDateModification() {
        return date_modification;
    }

    public void setDateModification(String date_modification) {
        this.date_modification = date_modification;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public String getDate_modification() {
        return date_modification;
    }

    public void setDate_modification(String date_modification) {
        this.date_modification = date_modification;
    }

    public String getDate_expiration() {
        return date_expiration;
    }

    public void setDate_expiration(String date_expiration) {
        this.date_expiration = date_expiration;
    }
}
