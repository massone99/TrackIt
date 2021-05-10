package com.app.trackit.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.trackit.R;

public class AddExerciseFragment extends Fragment {

    private static final String TAG = "AddExerciseFragment";

    public AddExerciseFragment() {
        super(R.layout.add_exercise_fragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // In this method the initialization of the dataset should be completed
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_exercise_fragment, container, false);
        rootView.setTag(TAG);
/*
        mRecyclerView = rootView.findViewById(R.id.muscle_list_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter =new MuscleAdapter();
        mRecyclerView.setAdapter(mAdapter);*/

        return rootView;
    }
}
