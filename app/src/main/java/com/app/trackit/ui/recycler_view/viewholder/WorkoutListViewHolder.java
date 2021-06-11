package com.app.trackit.ui.recycler_view.viewholder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.model.Workout;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.app.trackit.R;
import com.app.trackit.ui.AddWorkoutFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class WorkoutListViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "WorkoutListViewHolder";

    private int workoutId;
    private boolean edit;
    private final MaterialTextView titleTextView;
    private final MaterialTextView dateTextView;

    private WorkoutViewModel model;

    public WorkoutListViewHolder(@NonNull View itemView) {
        super(itemView);
        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        model = new ViewModelProvider(activity).get(WorkoutViewModel.class);
        titleTextView = itemView.findViewById(R.id.home_item_title);
        dateTextView = itemView.findViewById(R.id.home_item_data);
        MaterialCardView cardView = itemView.findViewById(R.id.home_item_card_view);
        cardView.setOnClickListener(v -> {
            model.editWorkout();
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
            Workout workout = model.getWorkoutFromId(workoutId);
            titleTextView.setText(workout.getWorkoutName());
            dateTextView.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(workout.getDate()));
            this.workoutId = workoutId;
        } catch (NullPointerException ignored) { }
    }

    public MaterialTextView getDateTextView() {
        return dateTextView;
    }

    public int getWorkoutId() { return workoutId; }
}
