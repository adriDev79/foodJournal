package com.example.appfood.bo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_element")
public class MealElement {

    /**
     * Identifiant de l'element du repas {@code int}
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * date de l'élément du repas {@code String}
     */
    private String day;

    /**
     * Nom de l'élément du menu {@code String}
     */
    private String element_name;

    /**
     * Identifiant du repas {@code int}
     */
    private int id_meal;

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

    public int getIdMeal() {
        return id_meal;
    }

    public void setIdMeal(int id_meal) {
        this.id_meal = id_meal;
    }

    public String getElement_name() {
        return element_name;
    }

    public void setElement_name(String element_name) {
        this.element_name = element_name;
    }

    public int getId_meal() {
        return id_meal;
    }

    public void setId_meal(int id_meal) {
        this.id_meal = id_meal;
    }
}
