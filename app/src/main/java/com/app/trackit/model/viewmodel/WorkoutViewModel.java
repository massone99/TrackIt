package com.app.trackit.model.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.trackit.MainActivity;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.Set;
import com.app.trackit.model.Workout;
import com.app.trackit.model.db.TrackItRepository;

import java.util.List;
import java.util.Map;

public class WorkoutViewModel extends AndroidViewModel {

    private TrackItRepository repository;
    // Before confirming the workout the data needs to be stored as a Java object.
    // The workout will be stored in the DB only after the confirm button is pressed.
    private Workout workout;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        repository = MainActivity.repo;
    }

    public Map<Exercise, List<Set>> getWorkoutExercises(String date){
        return repository.getWorkoutExercises(date);
    }

    public TrackItRepository getRepository() {
        return repository;
    }
}
