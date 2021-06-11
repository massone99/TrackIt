package com.app.trackit.ui.recycler_view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.app.trackit.R;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.viewmodel.StatsViewModel;
import com.app.trackit.ui.recycler_view.viewholder.ChartViewHolder;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.common.collect.FluentIterable;

import java.net.SocketImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartAdapter extends ListAdapter<Exercise, ChartViewHolder> {

    private final String TAG = "ChartAdapter";

    private AppCompatActivity activity;
    private Context context;
    private StatsViewModel statsViewModel;


    public ChartAdapter(@NonNull DiffUtil.ItemCallback<Exercise> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        activity = (AppCompatActivity) parent.getContext();
        context = parent.getContext();
        statsViewModel = new ViewModelProvider(activity).get(StatsViewModel.class);
        return ChartViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        ArrayList<Entry> repsValues = new ArrayList<>();
        ArrayList<Entry> weightValues = new ArrayList<>();

        Exercise current = getItem(position);

        // The Key is a Date converted in milliseconds (Long)
        Map<Long, Integer> repsOverDays = statsViewModel.getExerciseRepsStats(current.getExerciseId());
        Map<Long, Float> weightOverDays = statsViewModel.getExerciseWeightStats(current.getExerciseId());

        for(Long date: repsOverDays.keySet()) {
            int val = repsOverDays.get(date);
            repsValues.add(new Entry(date, val));
            Log.d(TAG, new SimpleDateFormat("dd/MM", Locale.ITALY).format(new Date((long) date * 1000)));
        }

        for(Long date: weightOverDays.keySet()) {
            float val = weightOverDays.get(date);
            weightValues.add(new Entry(date, val));
        }

        ArrayList<ILineDataSet> dataSet = new ArrayList<>();
        String label;
        if (current.getType().equals("Tempo")) {
            label = "Tempo";
        } else {
            label = "Ripetizioni";
        }
        LineDataSet repsDataSet = new LineDataSet(repsValues, label);
        LineDataSet weightDataSet = new LineDataSet(weightValues, "Peso");
        dataSet.add(repsDataSet);
        dataSet.add(weightDataSet);


        repsDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        repsDataSet.setDrawCircles(false);
        repsDataSet.setLineWidth(3.5f);
        repsDataSet.setColor(activity.getResources().getColor(R.color.red_500));

        weightDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        weightDataSet.setDrawCircles(false);
        weightDataSet.setLineWidth(3.5f);
        weightDataSet.setColor(activity.getResources().getColor(R.color.blue_500));

        LineData data = new LineData(dataSet);
        data.setValueTextSize(13f);

        XAxis xAxis = holder.getLineChart().getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(false);
//        holder.getLineChart().getAxisRight().setDrawGridLines(false);
        holder.getLineChart().getAxisRight().setDrawLabels(false);
//        holder.getLineChart().getAxisLeft().setDrawGridLines(false);
        holder.getLineChart().getAxisLeft().setDrawLabels(false);
//        xAxis.setEnabled(false);

        LineChart chart = holder.getLineChart();
//        chart.setBackgroundColor(R.color.whitesmoke);
        chart.setGridBackgroundColor(R.color.whitesmoke);
        chart.setBorderColor(R.color.black);
        chart.setBorderWidth(2);
        chart.setDescription(null);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String date = new SimpleDateFormat("dd/MM").format(new Date((long) e.getX() * 1000));
                int reps = (int) e.getY();

                holder.getTvContent().setText("Data: "  + date);


//                holder.getTvContent().setText("Ripetizioni: " + String.valueOf(reps) + ", Data: "  + date);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chart.setHighlightPerTapEnabled(true);
        chart.setData(data);

        holder.bind(current.getName());
    }

    public static class ExerciseDiff extends DiffUtil.ItemCallback<Exercise> {

        /*FIXME:
        *  potrebbe causare errori, tienilo d'occhio
        * */

        @Override
        public boolean areItemsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
