package com.app.trackit.model.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.app.trackit.model.Workout;
import com.app.trackit.model.db.relations.WorkoutWithExercisesAndSets;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Insert
    public void insert(Workout workout);

    @Delete
    public void delete(Workout workout);

    @Update
    public void updateWorkout(Workout workout);

    @Query("SELECT * FROM workouts")
    LiveData<List<Workout>> getAll();

    @Query("SELECT * FROM workouts WHERE date = :date")
    ListenableFuture<Workout> getWorkout(String date);

    @Transaction
    @Query("SELECT * FROM workouts")
    public List<WorkoutWithExercisesAndSets> getWorkoutsWithExercisesAndSets();

}
