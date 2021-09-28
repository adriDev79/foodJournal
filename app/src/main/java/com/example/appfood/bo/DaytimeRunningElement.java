package com.example.appfood.bo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity(tableName = "daytime_running_element")
public class DaytimeRunningElement {

    /**
     * Identifiant de l'élément {@code int}
     */
    @PrimaryKey (autoGenerate = true)
    private int id;

    /**
     * date de l'élément du repas {@code String}
     */
    private String day;

    /**
     * nom de l'élément de la journée {@code String}
     */
    private String element_name;

    public String getElement_name() {
        return element_name;
    }

    public void setElement_name(String element_name) {
        this.element_name = element_name;
    }

    public int getId_daytime_running() {
        return id_daytime_running;
    }

    public void setId_daytime_running(int id_daytime_running) {
        this.id_daytime_running = id_daytime_running;
    }

    /**
     * id de l'élément de la journée {@code int}
     */
    private int id_daytime_running;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getElementName() {
        return element_name;
    }

    public void setElementName(String element_name) {
        this.element_name = element_name;
    }

    public int getIdDaytimeRunning() {
        return id_daytime_running;
    }

    public void setIdDaytimeRunning(int id_daytime_running) {
        this.id_daytime_running = id_daytime_running;
    }
}
