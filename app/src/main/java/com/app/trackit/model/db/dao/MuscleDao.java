package com.app.trackit.model.db.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.app.trackit.model.Muscle;
import com.app.trackit.model.db.MuscleWithExercises;

import java.util.List;

public interface MuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Muscle muscle);

    @Delete
    public abstract void delete(Muscle muscle);

    @Update
    public abstract void update(Muscle muscle);

    @Query("SELECT * from muscles")
    public abstract List<Muscle> loadAllExercises();

    @Transaction
    @Query("SELECT * FROM muscles")
    public abstract List<MuscleWithExercises> loadMuscleWithExercises();

}
