package com.example.appfood.bo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "day_options")
public class DayOptions {

    /**
     * Date du jour {@code String}
     */
    @PrimaryKey
    @NonNull
    private String day;

    /**
     * Si alcool bu {@code boolean}
     */
    private boolean is_drink_alcohol;

    /**
     * Si sport {@code boolean}
     */
    private boolean is_sport;

    @NotNull
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean getIsDrinkAlcohol() {
        return is_drink_alcohol;
    }

    public boolean isIs_drink_alcohol() {
        return is_drink_alcohol;
    }

    public boolean isIs_sport() {
        return is_sport;
    }

    public void setIs_drink_alcohol(boolean is_drink_alcohol) {
        this.is_drink_alcohol = is_drink_alcohol;
    }

    public void setIs_sport(boolean is_sport) {
        this.is_sport = is_sport;
    }

    public void setIsDrinkAlcohol(boolean is_drink_alcohol) {
        this.is_drink_alcohol = is_drink_alcohol;
    }

    public boolean isSport() {
        return is_sport;
    }

    public void setIsSport(boolean is_sport) {
        this.is_sport = is_sport;
    }
}
