package com.app.trackit.ui.recycler_view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.app.trackit.R;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.textview.MaterialTextView;

public class ChartViewHolder extends RecyclerView.ViewHolder {

    private WorkoutViewModel model;

    private final MaterialTextView exerciseTextView;
    private final LineChart lineChart;
    private final MaterialTextView tvContent;

    public ChartViewHolder(@NonNull View itemView) {
        super(itemView);

        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        model = new ViewModelProvider(activity).get(WorkoutViewModel.class);

        exerciseTextView = itemView.findViewById(R.id.row_item_progress_exercise);
        lineChart = itemView.findViewById(R.id.line_chart);
        tvContent = (MaterialTextView) itemView.findViewById(R.id.tvContent);
    }

    public void bind(String exerciseName) {
        exerciseTextView.setText(exerciseName);
    }

    public LineChart getLineChart() {
        return lineChart;
    }

    public MaterialTextView getTvContent() {
        return tvContent;
    }

    public static ChartViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_progress, parent, false);
        return new ChartViewHolder(view);
    }


}
