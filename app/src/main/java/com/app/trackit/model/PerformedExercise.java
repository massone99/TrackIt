package com.app.trackit.model;

import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import com.app.trackit.ui.MainActivity;

import java.util.Date;

@Entity(tableName = "performed_exercises")
public class PerformedExercise {

    @PrimaryKey(autoGenerate = true)
    private int performedExerciseId;
    private String name;
    public int parentExerciseId;
    public final int parentWorkoutId;
    private Date date;

    public PerformedExercise(int parentExerciseId, String name,  int parentWorkoutId, Date date) {
        this.parentExerciseId = parentExerciseId;
        this.name = name;
        this.parentWorkoutId = parentWorkoutId;
        this.date = date;
    }

    public int getPerformedExerciseId() {
        return performedExerciseId;
    }

    public void setPerformedExerciseId(int performedExerciseId) {
        this.performedExerciseId = performedExerciseId;
    }

    public void setId(int id) {
        this.performedExerciseId = id;
    }

    public int getParentWorkoutId() {
        return parentWorkoutId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getParentExerciseId() {
        return parentExerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentExerciseId(int parentExerciseId) {
        this.parentExerciseId = parentExerciseId;
    }

}
