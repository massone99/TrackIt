package com.app.trackit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity(tableName = "workouts")
public class Workout {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "date")
    String date;

    @Ignore
    Map<Exercise, List<Set>> workoutExercises;

    public Workout(String date) {
        this.workoutExercises = new HashMap<>();
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void addExercise(Exercise exercise) {
        this.workoutExercises.put(exercise, new LinkedList<>());
    }

    public Map<Exercise, List<Set>> getWorkoutExercises() {
        return workoutExercises;
    }
}
