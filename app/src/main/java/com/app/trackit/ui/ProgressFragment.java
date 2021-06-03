package com.app.trackit.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.app.trackit.model.viewmodel.StatsViewModel;
import com.app.trackit.model.viewmodel.WorkoutListViewModel;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.app.trackit.ui.recycler_view.adapter.ChartAdapter;
import com.app.trackit.ui.recycler_view.adapter.WorkoutListAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.common.collect.FluentIterable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgressFragment extends Fragment implements LifecycleOwner {

    private static final String TAG = "ProgressFragment";

    private StatsViewModel statsModel;
    private WorkoutViewModel workoutModel;

    private ChartAdapter chartAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager linearLayoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        statsModel = new ViewModelProvider(requireActivity()).get(StatsViewModel.class);
        workoutModel = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
        workoutModel.getObservableFavorites().observe(this, exerciseList -> {
            Log.d(TAG, exerciseList.toString());
            chartAdapter.submitList(exerciseList);
            chartAdapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_progress, container, false);

        recyclerView = rootView.findViewById(R.id.progress_recycler_view);

        linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        chartAdapter = new ChartAdapter(new ChartAdapter.ExerciseDiff());
        recyclerView.setAdapter(chartAdapter);

        return rootView;
    }
}
