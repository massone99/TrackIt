package com.app.trackit.model.db;

import androidx.room.Entity;

@Entity(tableName = "workout_exercises",primaryKeys = {"workoutId", "exerciseName"})
public class WorkoutExerciseCrossRef {

    public int workoutId;
    public String exerciseName;

}