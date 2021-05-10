package com.app.trackit.model;

import com.app.trackit.model.exercises.Exercise;

import java.util.Map;

public class Workout {

    private int day;
    private int month;
    private int year;
    private Map<Exercise, Map<String, String>> info;

    /**
     * A class modelling a workout. A workout is identified by a timestamp
     * @param day
     * @param month
     * @param year
     */
    public Workout(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * A method to add new exercises to the current workout
     * @param ex is the exercise that should be addes
     * @return true if the insert goes right,
     *         false otherwise
     */
    public boolean addExercise(Exercise ex) {
        return true;
    }

    /**
     * A method to add information about a single exercise
     * @param key is the type of info to add
     * @param val is the value of that info
     * @return true if the insert goes right,
     *         false otherwise
     */
    public boolean addInfo(String key, String val) {
        return true;
    }

}
