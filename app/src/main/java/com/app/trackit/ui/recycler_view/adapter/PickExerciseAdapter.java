package com.app.trackit.ui.recycler_view.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.model.Exercise;
import com.app.trackit.ui.recycler_view.viewholder.ExerciseViewHolder;
import com.app.trackit.ui.recycler_view.viewholder.PickExerciseViewHolder;

public class PickExerciseAdapter extends ListAdapter<Exercise, PickExerciseViewHolder> {

    private final String TAG = "PickExerciseAdapter";
    // There should be a ViewModel made only to manage this Fragment content

    public PickExerciseAdapter(@NonNull DiffUtil.ItemCallback<Exercise> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PickExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PickExerciseViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PickExerciseViewHolder holder, int position) {
        Exercise current = getItem(position);
        holder.bind(current.getName(), current.getType(), current.getMovement());
    }

    public static class ExerciseDiff extends DiffUtil.ItemCallback<Exercise> {

        @Override
        public boolean areItemsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
