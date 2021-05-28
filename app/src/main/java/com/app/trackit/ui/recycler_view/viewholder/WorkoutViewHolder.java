package com.app.trackit.ui.recycler_view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.ui.MainActivity;
import com.app.trackit.R;
import com.app.trackit.model.Set;
import com.app.trackit.model.viewmodel.WorkoutViewModel;

public class WorkoutViewHolder extends RecyclerView.ViewHolder{

    private static final String TAG = "WorkoutViewHolder";

    private final TextView nameTextView;
    private final RecyclerView recyclerView;

    private int exerciseId;

    public WorkoutViewHolder(@NonNull View itemView,
                             WorkoutViewModel model) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.workout_exercise_item_text_view);
        recyclerView = itemView.findViewById(R.id.exercise_sets_recycler_view);

        final TextView addSet = itemView.findViewById(R.id.workout_exercise_item_add_set);
        addSet.setOnClickListener(v -> {
            Set set = new Set(model.getNextSetValueForExercise(exerciseId), exerciseId);
            MainActivity.repo.insertSet(set);
        });

        final ImageButton removeButton = itemView.findViewById(R.id.remove_exercise);

        removeButton.setOnClickListener(v -> {
            MainActivity.repo.deletePerformedExercise(MainActivity.repo.getPerformedExercise(exerciseId));
        });

    }

    public void bind(String name, int exerciseId) {
        nameTextView.setText(name);
        this.exerciseId = exerciseId;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static WorkoutViewHolder create(ViewGroup parent,
                                           WorkoutViewModel model) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_workout_exercise, parent, false);

        return new WorkoutViewHolder(view, model);
    }
}
