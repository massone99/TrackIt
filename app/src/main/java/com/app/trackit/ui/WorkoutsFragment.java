package com.app.trackit.ui;

import android.os.Bundle;
import android.util.Log;
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
import com.app.trackit.model.Workout;
import com.app.trackit.model.utility.Utilities;
import com.app.trackit.model.viewmodel.WorkoutListViewModel;
import com.app.trackit.ui.components.AddFab;
import com.app.trackit.ui.recycler_view.adapter.WorkoutListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collections;
import java.util.List;

public class WorkoutsFragment extends Fragment implements LifecycleOwner {

    private static final String TAG = "HomeFragment";

    protected AddFab fab;
    protected RecyclerView recyclerView;
    protected WorkoutListAdapter workoutListAdapter;
    protected RecyclerView.LayoutManager linearLayoutManager;

    private WorkoutListViewModel model;

    public WorkoutsFragment() {
        super(R.layout.fragment_workouts);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(WorkoutListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_workouts, container, false);
        rootView.setTag(TAG);

        List<Workout> list = model.getWorkouts();

        if (list.size() > 0) {
            rootView.findViewById(R.id.workout_helper).setVisibility(View.GONE);
        }

        model.getObservableWorkouts().observe(getViewLifecycleOwner(), workouts -> {
            workoutListAdapter.submitList(workouts);
            workoutListAdapter.notifyDataSetChanged();
        });

        // FIXME: put this button inside the other fragments too, for a better UX
        this.fab = new AddFab(
                rootView.findViewById(R.id.fab_add),
                rootView.findViewById(R.id.fab_add_exercise),
                rootView.findViewById(R.id.fab_add_workout),
                rootView,
                this
        );

        recyclerView = rootView.findViewById(R.id.home_recycler_view);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        workoutListAdapter = new WorkoutListAdapter(new WorkoutListAdapter.WorkoutDiff(), getActivity());
        recyclerView.setAdapter(workoutListAdapter);

        return rootView;
    }
}