package com.app.trackit.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.trackit.model.Muscle;
import com.app.trackit.model.db.dao.ExerciseDao;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.db.relations.ExerciseMuscleCrossRef;
import com.app.trackit.model.db.relations.MuscleExerciseCrossRef;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        Exercise.class,
        Muscle.class,
        MuscleExerciseCrossRef.class,
        ExerciseMuscleCrossRef.class},
        version = 1)
public abstract class TrackItDatabase extends RoomDatabase {

    public abstract ExerciseDao exerciseDao();

    private static volatile TrackItDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TrackItDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TrackItDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    TrackItDatabase.class,
                                    "trackit_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}