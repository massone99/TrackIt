package com.app.trackit.model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.app.trackit.model.Muscle;
import com.app.trackit.model.db.dao.ExerciseDao;
import com.app.trackit.model.Exercise;

@Database(entities = {
        Exercise.class,
        Muscle.class,
        MuscleExerciseCrossRef.class,
        ExerciseMuscleCrossRef.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExerciseDao exerciseDao();

}