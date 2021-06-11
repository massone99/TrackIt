package com.app.trackit.model.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.SortedList;

import com.app.trackit.ui.MainActivity;
import com.app.trackit.model.Workout;
import com.app.trackit.model.db.TrackItRepository;

import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class WorkoutListViewModel extends AndroidViewModel {

    private final TrackItRepository repository;

    private final LiveData<List<Workout>> workouts;

    public WorkoutListViewModel(Application application) {
        super(application);
        repository = MainActivity.repo;
        workouts = repository.loadAllWorkouts();
    }

    public LiveData<List<Workout>> getObservableWorkouts() {
        return workouts;
    }

    public List<Workout> getWorkouts() {
        return repository.getAllWorkouts();
    }
}
