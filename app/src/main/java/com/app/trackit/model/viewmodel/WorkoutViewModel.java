package com.app.trackit.model.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.trackit.model.Exercise;
import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Set;
import com.app.trackit.model.Workout;
import com.app.trackit.model.db.TrackItRepository;
import com.app.trackit.ui.MainActivity;

import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class WorkoutViewModel extends AndroidViewModel {

    private static final String TAG = "WorkoutViewModel";

    private final TrackItRepository repository;
    private List<Set> setChanges;
    private int workoutId;
    private LiveData<List<PerformedExercise>> exercises;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        repository = MainActivity.repo;
        setChanges = new LinkedList<>();
    }

    public void updateExercisesDate(int parentWorkoutId, Date date) {
        repository.updateExercisesDate(parentWorkoutId, date);
    }

    public Workout getWorkoutFromId(int workoutId) {
        return repository.getWorkoutFromId(workoutId);
    }

    public void insertExercise(Exercise exercise) {
        repository.insertExercise(exercise);
    }

    public boolean isExerciseFavorite(int exerciseId) {
        return repository.isExerciseFavorite(exerciseId);
    }

    public void editWorkout() {
        Workout workout = repository.getCurrentWorkout();
        if (workout != null) {
            repository.setConfirmWorkout(workout.getWorkoutId(), true);
        }
    }

    public void convalidateWorkout(Workout workout) {
        repository.convalidateWorkout(workout);
    }

    public void addPendingSetChanges(Set set) {
        setChanges.add(set);
    }

    public void removePendingSetChanges(Set set) {
        try {
            for (Set s : setChanges) {
                if (s.getId() == set.getId()) {
                    setChanges.remove(s);
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "SetChanges: " + String.valueOf(setChanges.size()));
        for (Set s2 : setChanges) {
            Log.d(TAG, "Set ID: " + String.valueOf(s2.setId));
        }
    }

    public void submitSetChanges() {
        for (Set set : setChanges) {
            if (set.getReps() != 0 || set.getWeight() != 0) {
                repository.insertSet(set);
            }
        }
    }

    public void setExerciseAsFavorite(int exerciseId, boolean favorite) {
        repository.setExerciseAsFavorite(exerciseId, favorite);
    }

    public Exercise getExerciseFromName(String name) {
        return repository.getExercise(name);
    }

    public void deleteExercise(Exercise exercise) {
        repository.deleteExercise(exercise);
    }

    public void updateWorkout(Workout workout) {
        repository.updateWorkout(workout);
    }

    public Exercise getExerciseFromId(int exerciseId) {
        return repository.getExerciseFromId(exerciseId);
    }

    public int getExerciseIdFromPerformed(int performedExerciseId) {
        return repository.getPerformedExercise(performedExerciseId).getParentExerciseId();
    }

    public LiveData<List<PerformedExercise>> getObservableExercises(int workoutId) {
        return repository.getObservableWorkoutExercises(workoutId);
    }

    public LiveData<List<Exercise>> getObservableFavorites() {
        return repository.getFavoriteExercises();
    }

    public List<PerformedExercise> getExercises(int workoutId) {
        return repository.getWorkoutExercises(workoutId);
    }

    public LiveData<List<Set>> getSetsFromExercise(int exerciseId) {
        return repository.getObservableSetsFromExercise(exerciseId);
    }

    public int getNextSetValueForExercise(int performedExerciseId) {
        List<Set> sets;
        try {
            sets = repository.getSetsFromExercise(performedExerciseId);
            List<Integer> setNums = sets
                    .stream()
                    .map(Set::getNumber).sorted().collect(Collectors.toList());
            return (setNums.get(setNums.size() - 1) + 1);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            // In case there isn't any set for this performedExercise
            return 1;
        }
    }

    public void emptySetChanges() {
        setChanges = new LinkedList<>();
    }
}
