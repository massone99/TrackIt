package com.app.trackit.model.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.app.trackit.model.db.relations.ExerciseWithMuscles;
import com.app.trackit.model.Exercise;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Exercise exercise);

    @Delete
    public void delete(Exercise exercise);

    @Update
    public void update(Exercise exercise);

    @Query("SELECT * from exercises")
    public ListenableFuture<List<Exercise>> loadAllExercises();

    @Query("SELECT COUNT(name) FROM exercises")
    public ListenableFuture<Integer> getExercisesCount();
}
