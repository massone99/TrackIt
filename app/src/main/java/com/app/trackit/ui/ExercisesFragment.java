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
import com.app.trackit.ui.components.ExpandableFab;
import com.app.trackit.ui.recycler_view.ExerciseAdapter;

public class ExercisesFragment extends Fragment {

    private static final String TAG = "ExercisesFragment";

    protected RecyclerView recyclerView;
    protected ExerciseAdapter exerciseAdapter;

    protected ExpandableFab fab;

    protected RecyclerView.LayoutManager layoutManager;

    public ExercisesFragment() {
        super(R.layout.fragment_exercise_list);
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

        View rootView = inflater.inflate(
                R.layout.fragment_exercise_list,
                container,
                false);
        rootView.setTag(TAG);

        recyclerView = rootView.findViewById(R.id.exercises_recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        exerciseAdapter = new ExerciseAdapter();
        recyclerView.setAdapter(exerciseAdapter);

        return rootView;
    }


}
