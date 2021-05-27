package com.app.trackit.ui.recycler_view.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.app.trackit.MainActivity;
import com.app.trackit.model.Exercise;
import com.app.trackit.ui.recycler_view.viewholder.PickExerciseViewHolder;

public class PickExerciseAdapter extends ListAdapter<Exercise, PickExerciseViewHolder> {

    private final String TAG = "PickExerciseAdapter";

    private int workoutId;

    private FragmentActivity fragmentActivity;
    private WorkoutAdapter workoutAdapter;

    public PickExerciseAdapter(@NonNull DiffUtil.ItemCallback<Exercise> diffCallback,
                               FragmentActivity fragmentActivity,
                               WorkoutAdapter workoutAdapter,
                               int workoutId) {
        super(diffCallback);
        this.workoutId = MainActivity.repo.getCurrentWorkout().getWorkoutId();
        this.fragmentActivity = fragmentActivity;
        this.workoutAdapter = workoutAdapter;
    }

    @NonNull
    @Override
    public PickExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return PickExerciseViewHolder.create(parent, fragmentActivity, workoutId);
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
