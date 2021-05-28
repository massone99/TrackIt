package com.app.trackit.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.trackit.ui.MainActivity;
import com.app.trackit.model.Workout;
import com.app.trackit.model.db.TrackItRepository;

import java.util.List;

public class WorkoutListViewModel extends AndroidViewModel {

    private TrackItRepository repository;

    private final LiveData<List<Workout>> workouts;

    public WorkoutListViewModel(Application application) {
        super(application);
        repository = MainActivity.repo;
        workouts = repository.loadAllWorkouts();
    }

    public LiveData<List<Workout>> getObservableWorkouts() {
        return workouts;
    }
}
