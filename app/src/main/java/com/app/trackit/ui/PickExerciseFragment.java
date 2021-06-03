package com.app.trackit.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.R;
import com.app.trackit.model.viewmodel.ExercisesViewModel;
import com.app.trackit.ui.recycler_view.adapter.PickExerciseAdapter;
import com.app.trackit.ui.recycler_view.adapter.WorkoutAdapter;

public class PickExerciseFragment extends Fragment{

    private static final String TAG = "PickExerciseFragment";

    protected RecyclerView recyclerView;
    protected PickExerciseAdapter exerciseAdapter;
    protected WorkoutAdapter workoutAdapter;

    private final int id;

    protected AddWorkoutFragment workoutFragment;

    private ExercisesViewModel model;

    public PickExerciseFragment(AddWorkoutFragment workoutFragment,
                                WorkoutAdapter workoutAdapter,
                                int id) {
        super(R.layout.fragment_pick_exercise);
        this.workoutFragment = workoutFragment;
        this.id = id;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(ExercisesViewModel.class);
        model.getAllExercises().observe(this, exerciseList -> {
            exerciseAdapter.submitList(exerciseList);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_pick_exercise,
                container,
                false);
        rootView.setTag(TAG);

        recyclerView = rootView.findViewById(R.id.pick_exercises_recycler_view);
        exerciseAdapter = new PickExerciseAdapter(
                new PickExerciseAdapter.ExerciseDiff(),
                requireActivity(),
                workoutAdapter,
                id);
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }
}