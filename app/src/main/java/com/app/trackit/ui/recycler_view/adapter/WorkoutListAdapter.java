package com.app.trackit.ui.recycler_view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.R;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.Workout;
import com.app.trackit.ui.recycler_view.viewholder.PickExerciseViewHolder;
import com.app.trackit.ui.recycler_view.viewholder.WorkoutListViewHolder;
import com.google.android.material.textview.MaterialTextView;

public class WorkoutListAdapter extends ListAdapter<Workout, WorkoutListViewHolder> {

    private Activity activity;

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
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Workout oldItem, @NonNull Workout newItem) {
            return oldItem.getWorkoutId() == newItem.getWorkoutId();
        }
    }

    /*public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (MaterialTextView) view.findViewById(R.id.home_item_text_view);
        }

        public TextView getTextView() {
            return textView;
        }
    }*/


}
