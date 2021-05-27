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

    @Query("UPDATE workouts SET confirmed = 1 WHERE workoutId = :id")
    public void confirmWorkout(int id);

    @Query("UPDATE workouts SET confirmed = 0 WHERE workoutId = :id")
    public void editWorkout(int id);

    @Query("SELECT * FROM workouts")
    LiveData<List<Workout>> getAll();

    @Query("SELECT * FROM workouts WHERE workoutId = :id")
    ListenableFuture<Workout> getWorkout(int id);

    @Transaction
    @Query("SELECT * FROM workouts")
    public List<WorkoutWithExercisesAndSets> getWorkoutsWithExercisesAndSets();

    @Transaction
    @Query("select * from workouts where confirmed = 0")
    public ListenableFuture<Workout> getCurrentWorkout();

}
