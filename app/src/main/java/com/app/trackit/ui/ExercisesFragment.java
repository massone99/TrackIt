package com.app.trackit.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.R;
import com.app.trackit.model.utility.Utilities;
import com.app.trackit.model.viewmodel.ExercisesViewModel;
import com.app.trackit.ui.recycler_view.adapter.ExerciseListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExercisesFragment extends Fragment implements LifecycleOwner{

    private static final String TAG = "ExercisesFragment";

    protected RecyclerView recyclerView;
    protected ExerciseListAdapter exerciseListAdapter;

    private ExercisesViewModel model;

    public ExercisesFragment() {
        super(R.layout.fragment_exercise_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(ExercisesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.fragment_exercise_list,
                container,
                false);
        rootView.setTag(TAG);

        model.getAllExercises().observe(getViewLifecycleOwner(), exerciseList -> {
            if (exerciseList.size() == 0) {
                rootView.findViewById(R.id.exercise_helper).setVisibility(View.VISIBLE);
            }
            exerciseListAdapter.submitList(exerciseList);
            exerciseListAdapter.notifyDataSetChanged();
        });

        recyclerView = rootView.findViewById(R.id.exercises_recycler_view);
        recyclerView.setHasFixedSize(true);
        exerciseListAdapter = new ExerciseListAdapter(new ExerciseListAdapter.ExerciseDiff());
        recyclerView.setAdapter(exerciseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        exerciseListAdapter.submitList(model.getAllExercises().getValue());
    }
}
