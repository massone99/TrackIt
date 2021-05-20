package com.app.trackit.model.db;

import android.app.Application;
import android.os.strictmode.CredentialProtectedWhileLockedViolation;

import androidx.lifecycle.LiveData;

import com.app.trackit.model.Exercise;
import com.app.trackit.model.Set;
import com.app.trackit.model.Workout;
import com.app.trackit.model.db.dao.ExerciseDao;
import com.app.trackit.model.db.dao.WorkoutDao;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class TrackItRepository {

    private ExerciseDao exerciseDao;
    private WorkoutDao workoutDao;
    private LiveData<List<Exercise>> exercises;
    private LiveData<List<Workout>> workouts;

    public TrackItRepository(Application application) {
        TrackItDatabase db = TrackItDatabase.getDatabase(application);
        exerciseDao = db.exerciseDao();
        workoutDao = db.workoutDao();
        exercises = exerciseDao.getAll();
        workouts = workoutDao.getAll();
    }

    public LiveData<List<Exercise>> loadAllExercises() {
        return exercises;
    }

    public LiveData<List<Workout>> loadAllWorkouts() {
        return workouts;
    }

    public Map<Exercise, List<Set>> getWorkoutExercises(String date) {
        try {
            return workoutDao.getWorkout(date).get().getWorkoutExercises();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ListenableFuture<Exercise> getExercise(String name) {
        return exerciseDao.getExercise(name);
    }

    public ListenableFuture<Workout> getWorkout(String date) {
        return workoutDao.getWorkout(date);
    }

    public void insertWorkout(Workout workout) {
        // The "best" way to insert a workout is to create
        // it and add all the exercises. Then after the workout
        // is built, proceed to insert it in the DB.
        // ([Builder] pattern in future maybe)
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.insert(workout);
        });
    }

    public void insertExercise(Exercise exercise) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.insert(exercise);
        });
    }

    public void deleteWorkout(Workout workout) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            workoutDao.delete(workout);
        });
    }

    public void deleteExercise(Exercise exercise) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.delete(exercise);
        });
    }
}
