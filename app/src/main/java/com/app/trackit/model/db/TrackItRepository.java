package com.app.trackit.model.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.app.trackit.model.Exercise;
import com.app.trackit.model.db.dao.ExerciseDao;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

public class TrackItRepository {

    public ExerciseDao exerciseDao;
    private ListenableFuture<List<Exercise>> exercises;

    public TrackItRepository(Application application) {
        TrackItDatabase db = TrackItDatabase.getDatabase(application);
        exerciseDao = db.exerciseDao();
        exercises = exerciseDao.loadAllExercises();
    }

    public ListenableFuture<List<Exercise>> getExercises() {
        return exercises;
    }

    public ListenableFuture<Integer> getExercisesCount() {
        return exerciseDao.getExercisesCount();
    }

    public void insert(Exercise exercise) {
        TrackItDatabase.databaseWriteExecutor.execute(() -> {
            exerciseDao.insert(exercise);
        });
    }

}
