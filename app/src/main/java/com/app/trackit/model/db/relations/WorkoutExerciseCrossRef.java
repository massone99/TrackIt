package com.app.trackit.model.db.relations;

import androidx.room.Entity;

@Entity(tableName = "workout_exercises",primaryKeys = {"workoutId", "exerciseName"})
public class WorkoutExerciseCrossRef {

    public int workoutId;
    public String exerciseName;

}