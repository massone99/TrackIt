package com.app.trackit.model.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.app.trackit.model.Exercise;
import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Set;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ExerciseDao {

    // Main Operations

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExercise(Exercise exercise);

    @Insert
    void insertPerformedExercise(PerformedExercise performedExercise);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSet(Set set);

    @Delete
    void deleteExercise(Exercise exercise);

    @Delete
    void deletePerformedExercise(PerformedExercise performedExercise);

    @Delete
    void deleteSet(Set set);

    @Update
    void updateExercise(Exercise exercise);

    @Query("UPDATE exercises SET favourite = :favorite WHERE exerciseId = :exerciseId")
    void setExerciseAsFavorite(int exerciseId, boolean favorite);

    @Query("SELECT favourite FROM exercises WHERE exerciseId = :exerciseId")
    ListenableFuture<Boolean> isFavorite(int exerciseId);

    @Query("SELECT * FROM exercises WHERE favourite = 1")
    LiveData<List<Exercise>> getFavoritesExercises();

    @Update
    void updatePerformedExercise(PerformedExercise performedExercise);

    @Update
    void updateSet(Set set);

    // Return value queries

    @Query("select * from sets where setId = :id")
    ListenableFuture<Set> getSetFromId(int id);

    @Query("SELECT * from performed_exercises where parentWorkoutId = :id")
    LiveData<List<PerformedExercise>> getObservableExercisesFromWorkout(int id);

    @Query("SELECT * from performed_exercises where parentWorkoutId = :id")
    ListenableFuture<List<PerformedExercise>> getExercisesFromWorkout(int id);

    @Query("SELECT * from sets where parentPerformedExerciseId = :id")
    LiveData<List<Set>> getObservableSetsFromExercise(int id);

    @Query("SELECT * from sets where parentPerformedExerciseId = :performedExerciseId " +
            "and  reps = (select max(reps) from sets where parentPerformedExerciseId = :performedExerciseId)")
    ListenableFuture<Set> getBestSetForReps(int performedExerciseId);

    @Query("SELECT * from sets where parentPerformedExerciseId = :performedExerciseId " +
            "and  weight = (select max(weight) from sets where parentPerformedExerciseId = :performedExerciseId)")
    ListenableFuture<Set> getBestSetForWeight(int performedExerciseId);

    @Query("select max(reps) from sets where relativeExerciseId = :exerciseId")
    ListenableFuture<Integer> getBestReps(int exerciseId);

    @Query("SELECT * from sets where parentPerformedExerciseId = :id")
    ListenableFuture<List<Set>> getSetsFromExercise(int id);

    @Query("DELETE from sets where parentPerformedExerciseId = :id")
    void deleteSetsFromExercise(int id);

    @Query("DELETE from performed_exercises where parentWorkoutId = :id")
    void deleteAllExercisesFromWorkout(int id);

    @Query("SELECT * from exercises where name = :name")
    ListenableFuture<Exercise> getExerciseFromName(String name);

    @Query("SELECT * from exercises where exerciseId = :exerciseId")
    ListenableFuture<Exercise> getExerciseFromId(int exerciseId);

    @Query("SELECT * from performed_exercises where performedExerciseId = :id")
    ListenableFuture<PerformedExercise> getPerformedExercise(int id);

    @Query("select * from performed_exercises where parentExerciseId = :id")
    ListenableFuture<List<PerformedExercise>> getPerformancesForExercise(int id);

    @Query("SELECT * from exercises")
    LiveData<List<Exercise>> getAllExercises();


}
