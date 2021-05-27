package com.app.trackit.model.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.app.trackit.model.Exercise;
import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Set;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertExercise(Exercise exercise);

    @Insert
    public void insertPerformedExercise(PerformedExercise performedExercise);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertSet(Set set);

    @Delete
    public void deleteExercise(Exercise exercise);

    @Delete
    public void deletePerformedExercise(PerformedExercise performedExercise);

    @Delete
    public void deleteSet(Set set);

    @Update
    public void updateExercise(Exercise exercise);

    @Update
    public void updatePerformedExercise(PerformedExercise performedExercise);

    @Update
    public void updateSet(Set set);

    @Query("select * from sets where setId = :id")
    public ListenableFuture<Set> getSetFromId(int id);

    // TODO:
    // Dovrei aggiungere metodi per ottenere gli esercizi fatti in
    // un determinato workout. Gli esercizi fatti in un workout sono
    // identificati dall'id del workout in quanto chiave foreign nell'esercizio

    @Query("SELECT * from performed_exercises where parentWorkoutId = :id")
    public LiveData<List<PerformedExercise>> getObservableExercisesFromWorkout(int id);

    @Query("SELECT * from performed_exercises where parentWorkoutId = :id")
    public ListenableFuture<List<PerformedExercise>> getExercisesFromWorkout(int id);

    @Query("SELECT * from sets where parentPerformedExerciseId = :id")
    public LiveData<List<Set>> getObservableSetsFromExercise(int id);

    @Query("SELECT * from sets where parentPerformedExerciseId = :id")
    public ListenableFuture<List<Set>> getSetsFromExercise(int id);

    @Query("DELETE from sets where parentPerformedExerciseId = :id")
    public void deleteSetsFromExercise(int id);

    @Query("DELETE from performed_exercises where parentWorkoutId = :id")
    public void deleteAllExercisesFromWorkout(int id);

    @Query("SELECT * from exercises where name = :name")
    public ListenableFuture<Exercise> getExercise(String name);

    @Query("SELECT * from performed_exercises where performedExerciseId = :id")
    public ListenableFuture<PerformedExercise> getPerformedExercise(int id);

    @Query("SELECT * from exercises")
    public LiveData<List<Exercise>> getAllExercises();


}
