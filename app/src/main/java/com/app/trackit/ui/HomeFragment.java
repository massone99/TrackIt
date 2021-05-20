package com.app.trackit.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.R;
import com.app.trackit.ui.components.AddFab;
import com.app.trackit.ui.recycler_view.adapter.WorkoutAdapter;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    protected AddFab fab;
    protected RecyclerView recyclerView;
    protected WorkoutAdapter workoutAdapter;
    protected RecyclerView.LayoutManager layoutManager;


    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        rootView.setTag(TAG);

        this.fab = new AddFab(
                rootView.findViewById(R.id.fab_add),
                rootView.findViewById(R.id.fab_add_exercise),
                rootView.findViewById(R.id.fab_add_workout),
                rootView,
                this
        );

        recyclerView = rootView.findViewById(R.id.home_recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        workoutAdapter = new WorkoutAdapter();
        recyclerView.setAdapter(workoutAdapter);

        return rootView;
    }
}