package com.app.trackit.model.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.trackit.MainActivity;
import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Set;
import com.app.trackit.model.db.TrackItRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WorkoutViewModel extends AndroidViewModel {

    private int workoutId;

    private final TrackItRepository repository;
    private LiveData<List<PerformedExercise>> exercises;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        repository = MainActivity.repo;
    }

    public LiveData<List<PerformedExercise>> getObservableExercises(int workoutId) {
        return repository.getObservableWorkoutExercises(workoutId);
    }

    public List<PerformedExercise> getExercises(int workoutId) {
        return repository.getWorkoutExercises(workoutId);
    }

    public LiveData<List<Set>> getSetsFromExercise(int exerciseId) {
        return repository.getObservableSetsFromExercise(exerciseId);
    }

    public int getNextSetValueForExercise(int performedExerciseId) {
        List<Set> sets = null;
        try {
            sets = repository.getSetsFromExercise(performedExerciseId);
            List<Integer> setNums = sets
                    .stream()
                    .map(set -> set.getNumber())
                    .collect(Collectors.toList());
            Collections.sort(setNums);
            return (setNums.get(setNums.size()-1) + 1);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            // In case there isn't any set for this performedExercise
            return 1;
        }
    }

}
