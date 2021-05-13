package com.app.trackit.model.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.app.trackit.model.Workout;
import com.app.trackit.model.Exercise;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Insert
    void insert(Workout workout);

    @Delete
    void delete(Workout workout);

    @Update
    public void updateWorkout(Workout workout);

    @Query("SELECT * FROM workouts")
    List<Workout> getAll();

    @Query("SELECT * FROM workout_exercises WHERE workoutId = :workoutId")
    List<Exercise> getExercisesList(int workoutId);

}
