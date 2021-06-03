package com.app.trackit.ui.recycler_view.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;

import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.app.trackit.ui.recycler_view.viewholder.WorkoutViewHolder;

public class WorkoutAdapter extends ListAdapter<PerformedExercise, WorkoutViewHolder> {

    private final String TAG = "WorkoutAdapter";

    private final WorkoutViewModel model;

    private final Fragment fragment;
    private final Activity activity;

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
        final SetListAdapter adapter = new SetListAdapter(new SetListAdapter.SetDiff(), model);

        model.getSetsFromExercise(current.getPerformedExerciseId()).observe(fragment, adapter::submitList);
        Log.d(TAG, model.getSetsFromExercise(current.getPerformedExerciseId()).toString());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setItemPrefetchEnabled(false);
        holder.getRecyclerView().setLayoutManager(linearLayoutManager);
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
