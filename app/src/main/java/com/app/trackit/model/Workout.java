package com.app.trackit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "workouts")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private int workoutId;
    private Date date;
    private boolean confirmed;

    public Workout() {
//        this.date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        this.date = new Date();
        this.confirmed = false;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    /**
     * Sets the confirmed flag for this workout
     * @param confirmed is 1 if the Workout is confirmed, 0 otherwise
     */
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
