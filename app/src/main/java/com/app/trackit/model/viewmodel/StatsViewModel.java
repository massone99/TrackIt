package com.app.trackit.model.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.db.TrackItRepository;
import com.app.trackit.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.SimpleFormatter;

public class StatsViewModel extends AndroidViewModel {

    private static final String TAG = "StatsViewModel";

    private final TrackItRepository repository;

    public StatsViewModel(@NonNull Application application) {
        super(application);
        repository = MainActivity.repo;
    }

    /**
     * Returns the progress of exerciseName's reps over time
     * @param exerciseId is the exercise to analyze
     * @return a Map with Date associated with the best reps of that day
     *
     */
    public Map<Long, Integer> getExerciseRepsStats(int exerciseId) {
        List<PerformedExercise> performances = repository.getPerformancesForExercise(exerciseId);

        // Exercise with date and best reps of that training day
        SortedMap<Long, Integer> result = new TreeMap<>();

        // For every performance, a query to get the best set of that workout will be executed
        for (PerformedExercise p : performances) {
            int reps = 0;
            try {
                reps = repository.getBestSetForReps(p.getPerformedExerciseId()).getReps();
            } catch (NullPointerException ignored) { }

            result.put(
                    (p.getDate().getTime())/1000,
                    reps
            );
        }

        return result;
    }

    /**
     * Returns the progress of exerciseName's weight over time
     * @param exerciseId is the exercise to analyze
     * @return a Map with Date associated with the best reps of that day
     *
     */
    public Map<Long, Float> getExerciseWeightStats(int exerciseId) {
        List<PerformedExercise> performances = repository.getPerformancesForExercise(exerciseId);

        // Exercise with date and best reps of that training day
        SortedMap<Long, Float> result = new TreeMap<>();

        // For every performance, a query to get the best set of that workout will be executed
        for (PerformedExercise p : performances) {
            float weight = 0;
            try {
                weight = repository.getBestSetForWeight(p.getPerformedExerciseId()).getWeight();
            } catch (NullPointerException ignored) { }

            result.put(
                    (p.getDate().getTime())/1000,
                    weight
            );
        }

        return result;
    }


}
