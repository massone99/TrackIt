package com.app.trackit.ui.recycler_view.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.app.trackit.model.Workout;
import com.app.trackit.ui.recycler_view.viewholder.WorkoutListViewHolder;

public class WorkoutListAdapter extends ListAdapter<Workout, WorkoutListViewHolder> {

    private final Activity activity;

    public WorkoutListAdapter(@NonNull DiffUtil.ItemCallback<Workout> diffCallback,
                              Activity activity) {
        super(diffCallback);
        this.activity = activity;
    }

    @NonNull
    @Override
    public WorkoutListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return WorkoutListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutListViewHolder holder, int position) {
        Workout current = getItem(position);
        holder.bind(current.getWorkoutId());
    }

    public static class WorkoutDiff extends DiffUtil.ItemCallback<Workout> {

        @Override
        public boolean areItemsTheSame(@NonNull Workout oldItem, @NonNull Workout newItem) {
            return oldItem.getWorkoutId() == newItem.getWorkoutId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Workout oldItem, @NonNull Workout newItem) {
            return oldItem.getDate().equals(newItem.getDate());
        }
    }

}
