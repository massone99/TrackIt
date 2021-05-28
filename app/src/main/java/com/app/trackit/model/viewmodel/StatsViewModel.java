package com.app.trackit.model.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;

import com.app.trackit.model.Exercise;
import com.app.trackit.model.db.TrackItRepository;
import com.app.trackit.ui.MainActivity;

import java.util.Map;

public class StatsViewModel extends AndroidViewModel {

    private TrackItRepository repository;

    public StatsViewModel(@NonNull Application application) {
        super(application);
        repository = MainActivity.repo;
    }

    public Map<Exercise, Pair<String, Integer>> getExerciseStats(String exerciseName) {
         /*
         TODO:
          Prendere tutti i performed_exercises con name = exerciseName
          fare join con i workout in cui appaiono i performed
         */
    }


}
