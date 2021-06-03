package com.app.trackit.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.trackit.ui.MainActivity;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.db.TrackItRepository;

import java.util.List;

public class ExercisesViewModel extends AndroidViewModel {

    private final TrackItRepository repository;

    private final LiveData<List<Exercise>> exercises;

    public ExercisesViewModel(Application application) {
        super(application);
        repository = MainActivity.repo;
        exercises = repository.loadAllExercises();
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return exercises;
    }

    public boolean isFavorite(Exercise exercise) {
        return repository.isExerciseFavorite(exercise.getExerciseId());
    }

    // FIXME:
    // A population method for the main exercises could be added here
}
