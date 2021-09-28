package com.example.appfood.bo;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Day {
    private LocalDate date;
    private LinkedHashMap<String, LinkedList<String>> meals;
    private LinkedHashMap<String, LinkedList<String>> daytime_running;
    private  LinkedHashMap<String, String> day_options;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LinkedHashMap<String, LinkedList<String>> getMeals() {
        return meals;
    }

    public void setMeals(LinkedHashMap<String, LinkedList<String>> meals) {
        this.meals = meals;
    }

    public LinkedHashMap<String, LinkedList<String>> getDaytimeRunning() {
        return daytime_running;
    }

    public void setDaytimeRunning(LinkedHashMap<String, LinkedList<String>> daytime_running) {
        this.daytime_running = daytime_running;
    }

    public LinkedHashMap<String, String> getDayOptions() {
        return day_options;
    }

    public void setDayOptions(LinkedHashMap<String, String> day_options) {
        this.day_options = day_options;
    }
}
