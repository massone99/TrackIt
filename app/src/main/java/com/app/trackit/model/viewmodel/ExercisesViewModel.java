package com.app.trackit.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.trackit.MainActivity;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.db.TrackItRepository;

import java.util.List;

public class ExercisesViewModel extends AndroidViewModel {

    private TrackItRepository repository;

    private final LiveData<List<Exercise>> exercises;

    public ExercisesViewModel(Application application) {
        super(application);
        repository = MainActivity.repo;
        exercises = repository.loadAllExercises();
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return exercises;
    }

    // FIXME:
    // A population method for the main exercises could be added here
}
