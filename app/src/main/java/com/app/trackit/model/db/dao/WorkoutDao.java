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

import java.util.Date;
import java.util.List;

@Dao
public interface WorkoutDao {

    @Insert
    void insert(Workout workout);

    @Delete
    void delete(Workout workout);

    @Update
    void updateWorkout(Workout workout);

    @Query("UPDATE workouts SET confirmed = :confirm WHERE workoutId = :id")
    void confirmWorkout(int id, boolean confirm);

    @Query("UPDATE workouts SET confirmed = 0 WHERE workoutId = :id")
    void editWorkout(int id);

    @Query("SELECT * FROM workouts ORDER BY date DESC")
    LiveData<List<Workout>> getObservableWorkouts();

    @Query("SELECT * FROM workouts ORDER BY date DESC")
    ListenableFuture<List<Workout>> getWorkouts();

    @Query("SELECT * FROM workouts WHERE workoutId = :id")
    ListenableFuture<Workout> getWorkout(int id);

    @Transaction
    @Query("select * from workouts where confirmed = 0")
    ListenableFuture<Workout> getCurrentWorkout();

    @Query("UPDATE performed_exercises SET date = :date WHERE parentWorkoutId = :parentWorkoutId")
    void updateExercisesDate(int parentWorkoutId, Date date);

}
