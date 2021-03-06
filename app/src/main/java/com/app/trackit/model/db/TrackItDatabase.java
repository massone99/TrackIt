package com.app.trackit.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Photo;
import com.app.trackit.model.Set;
import com.app.trackit.model.Workout;
import com.app.trackit.model.db.dao.ExerciseDao;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.db.dao.PhotoDao;
import com.app.trackit.model.db.dao.WorkoutDao;
import com.app.trackit.model.db.relations.PerformedExerciseSetCrossRef;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        Exercise.class,
        Workout.class,
        Set.class,
        PerformedExercise.class,
        PerformedExerciseSetCrossRef.class,
        Photo.class},
        version = 1)
@TypeConverters({Converters.class})
public abstract class TrackItDatabase extends RoomDatabase {

    public abstract ExerciseDao exerciseDao();
    public abstract WorkoutDao workoutDao();
    public abstract PhotoDao photoDao();

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