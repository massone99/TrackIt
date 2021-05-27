package com.app.trackit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity(tableName = "workouts")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private int workoutId;

    @ColumnInfo(name = "date")
    private String date;
    // 0 if the workout is not confirmed, 1 otherwise
    private int confirmed;

    public Workout() {
        this.date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        this.confirmed = 0;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getConfirmed() {
        return confirmed;
    }

    /**
     * Sets the confirmed flag for this workout
     * @param confirmed is 1 if the Workout is confirmed, 0 otherwise
     */
    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }
}
