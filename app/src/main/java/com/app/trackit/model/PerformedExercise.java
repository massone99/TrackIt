package com.app.trackit.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.app.trackit.ui.MainActivity;

@Entity(tableName = "performed_exercises")
public class PerformedExercise {

    @PrimaryKey(autoGenerate = true)
    private int performedExerciseId;
    private String name;
    private int parentWorkoutId;

    public PerformedExercise(String name, int parentWorkoutId) {
        this.name = name;
        this.parentWorkoutId = MainActivity.repo.getCurrentWorkout().getWorkoutId();
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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

}
