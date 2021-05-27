package com.app.trackit.ui.recycler_view.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;

import com.app.trackit.MainActivity;
import com.app.trackit.R;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Set;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.app.trackit.ui.recycler_view.viewholder.WorkoutViewHolder;

import java.util.List;

public class WorkoutAdapter extends ListAdapter<PerformedExercise, WorkoutViewHolder> {

    private final String TAG = "WorkoutAdapter";

    private WorkoutViewModel model;

    private Fragment fragment;
    private Activity activity;

    public WorkoutAdapter(@NonNull DiffUtil.ItemCallback<PerformedExercise> diffCallback,
                          WorkoutViewModel model,
                          Fragment fragment,
                          Activity activity) {
        super(diffCallback);
        this.model = model;
        this.fragment = fragment;
        this.activity = activity;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return WorkoutViewHolder.create(parent, model);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        PerformedExercise current = getItem(position);
        holder.bind(current.getName(), current.getPerformedExerciseId());

        // Managing the Sets RecyclerView
        final SetListAdapter adapter = new SetListAdapter(new SetListAdapter.SetDiff(), activity);

        model.getSetsFromExercise(current.getPerformedExerciseId()).observe(fragment, adapter::submitList);
        Log.d(TAG, model.getSetsFromExercise(current.getPerformedExerciseId()).toString());

        holder.getRecyclerView().setLayoutManager(new LinearLayoutManager(activity));
        holder.getRecyclerView().setAdapter(adapter);
    }

    public static class ExerciseDiff extends DiffUtil.ItemCallback<PerformedExercise> {

        @Override
        public boolean areItemsTheSame(@NonNull PerformedExercise oldItem, @NonNull PerformedExercise newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PerformedExercise oldItem, @NonNull PerformedExercise newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
