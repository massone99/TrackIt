package com.app.trackit.ui.recycler_view.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.app.trackit.R;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.viewmodel.ExercisesViewModel;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.app.trackit.ui.recycler_view.viewholder.ExerciseListViewHolder;
import com.google.android.material.card.MaterialCardView;

public class ExerciseListAdapter extends ListAdapter<Exercise, ExerciseListViewHolder> {

    private final String TAG = "ExerciseAdapter";
    // There should be a ViewModel made only to manage this Fragment content
    private ExercisesViewModel exercisesViewModel;

    public ExerciseListAdapter(@NonNull DiffUtil.ItemCallback<Exercise> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ExerciseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) parent.getContext();
        exercisesViewModel = new ViewModelProvider(appCompatActivity).get(ExercisesViewModel.class);

        return ExerciseListViewHolder.create(parent, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListViewHolder holder, int position) {
        Exercise current = getItem(position);
        holder.bind(current.getName(), current.getType(), current.getMovement());
        try {
            holder.getFavoriteButton().setFavorite(exercisesViewModel.isFavorite(getItem(position)));
        } catch (NullPointerException ignored) {
        }
    }

    public static class ExerciseDiff extends DiffUtil.ItemCallback<Exercise> {

        @Override
        public boolean areItemsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.getExerciseId() == newItem.getExerciseId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Exercise oldItem, @NonNull Exercise newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
