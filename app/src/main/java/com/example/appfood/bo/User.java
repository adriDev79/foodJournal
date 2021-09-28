package com.example.appfood.bo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "utilisateur")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private boolean admin;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
