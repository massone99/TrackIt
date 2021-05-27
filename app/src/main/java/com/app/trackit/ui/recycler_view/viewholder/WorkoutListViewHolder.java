package com.app.trackit.ui.recycler_view.viewholder;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.MainActivity;
import com.app.trackit.R;
import com.app.trackit.ui.AddWorkoutFragment;
import com.google.android.material.textview.MaterialTextView;

public class WorkoutListViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "WorkoutListViewHolder";

    private int workoutId;
    private boolean edit;
    private final MaterialTextView workoutTextView;

    public WorkoutListViewHolder(@NonNull View itemView) {
        super(itemView);
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        workoutTextView = itemView.findViewById(R.id.home_item_text_view);
        workoutTextView.setOnClickListener(v -> {
            edit = true;
            Bundle bundle = new Bundle();
            bundle.putBoolean("edit", true);
            bundle.putInt("workoutId", workoutId);
            AddWorkoutFragment fragment = new AddWorkoutFragment();
            fragment.setArguments(bundle);
            activity
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    )
                    .replace(R.id.fragment_container_view, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    public static WorkoutListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_home, parent, false);
        return new WorkoutListViewHolder(view);
    }

    public void bind(int workoutId) {
        try {
            String title = edit ?
                    "Allenamento del " + MainActivity.repo.getWorkoutFromId(workoutId).getDate() + " [IN CORSO]":
                    "Allenamento del " + MainActivity.repo.getWorkoutFromId(workoutId).getDate();

            workoutTextView.setText(title);
            this.workoutId = workoutId;
        } catch (NullPointerException ignored) { }
    }

    public MaterialTextView getWorkoutTextView() {
        return workoutTextView;
    }

    public int getWorkoutId() { return workoutId; }
}
