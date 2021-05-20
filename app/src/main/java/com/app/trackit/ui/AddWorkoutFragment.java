package com.app.trackit.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.R;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.Workout;
import com.app.trackit.model.viewmodel.WorkoutViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddWorkoutFragment extends Fragment {

    private static final String TAG = "AddWorkoutFragment";

    private WorkoutViewModel model;

    private View rootView;
    protected RecyclerView recyclerView;
    private TextView addExerciseToWorkout;

    private Workout workout;

    public AddWorkoutFragment() {
        super(R.layout.fragment_add_workout);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // When you access this fragment a Workout object should be created.
        // By the way, the memorization on the database should happen only
        // after the "Confirm Workout" button is pressed
        this.model = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
        // Here we convert the current date in millis to the dd-mm-yyyy format
        // and then convert it to a String so it can be stored in the DB
        workout = new Workout(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        Log.d(TAG, workout.getDate());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_workout, container, false);
        rootView.setTag(TAG);

        recyclerView = rootView.findViewById(R.id.recycler_view_workout_exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        exerciseAdapter = new WorkoutExerciseAdapter(model);
//        recyclerView.setAdapter(exerciseAdapter);

        addExerciseToWorkout = rootView.findViewById(R.id.add_exercise_to_workout);
        addExerciseToWorkout.setOnClickListener(v -> {
            PickExerciseFragment fragment = new PickExerciseFragment(this);
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    )
                    .replace(R.id.fragment_container_view, fragment, "PickExerciseFragment")
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

    public void addExerciseToWorkout(Exercise exercise) {
//        model.getRepository().getWorkout()
    }
}
