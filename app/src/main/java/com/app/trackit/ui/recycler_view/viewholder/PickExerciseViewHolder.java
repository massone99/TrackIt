package com.app.trackit.ui.recycler_view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.model.Workout;
import com.app.trackit.ui.MainActivity;
import com.app.trackit.R;
import com.app.trackit.model.PerformedExercise;
import com.google.android.material.card.MaterialCardView;

public class PickExerciseViewHolder extends RecyclerView.ViewHolder {

    private final String TAG = "PickExerciseViewHolder";

    private final MaterialCardView cardView;
    private final TextView idTextView;
    private final TextView nameTextView;
    private final TextView typeTextView;
    private final TextView movementTextView;

    public PickExerciseViewHolder(@NonNull View itemView, FragmentActivity fragmentActivity, int id) {
        super(itemView);

        cardView = itemView.findViewById(R.id.pick_exercise_item_card_view);
        idTextView = itemView.findViewById(R.id.pick_exercise_item_id);
        nameTextView = itemView.findViewById(R.id.pick_exercise_item_text_view);
        typeTextView = itemView.findViewById(R.id.pick_exercise_item_type_text_view);
        movementTextView = itemView.findViewById(R.id.pick_exercise_item_movement_text_view);

        cardView.setOnClickListener(v -> {
            PerformedExercise exercise;
            Workout workout = MainActivity.repo.getCurrentWorkout();
            exercise = new PerformedExercise(
                    Integer.parseInt(idTextView.getText().toString()),
                    nameTextView.getText().toString(),
                    workout.getWorkoutId(),
                    workout.getDate());

            MainActivity.repo.insertPerformedExercise(exercise);
            fragmentActivity.getSupportFragmentManager().popBackStack();
        });
    }

    public static PickExerciseViewHolder create(
            ViewGroup parent,
            FragmentActivity fragmentActivity,
            int id
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_pick_exercise, parent, false);
        return new PickExerciseViewHolder(view, fragmentActivity, id);
    }

    public void bind(int exercisedId,
                     String name,
                     String type,
                     String movement) {
        idTextView.setText(String.valueOf(exercisedId));
        nameTextView.setText(name);
        typeTextView.setText(type);
        movementTextView.setText(movement);

    }

}
