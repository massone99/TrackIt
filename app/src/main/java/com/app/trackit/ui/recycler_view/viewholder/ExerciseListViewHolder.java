package com.app.trackit.ui.recycler_view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.MainActivity;
import com.app.trackit.R;
import com.app.trackit.model.Exercise;
import com.google.android.material.card.MaterialCardView;

public class ExerciseListViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameTextView;
    private final TextView typeTextView;
    private final TextView movementTextView;

    public ExerciseListViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.exercise_item_text_view);
        typeTextView = itemView.findViewById(R.id.exercise_item_type_text_view);
        movementTextView = itemView.findViewById(R.id.exercise_item_movement_text_view);
        ImageButton deleteButton = itemView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            Exercise exercise = null;
            exercise = MainActivity
                    .repo
                    .getExercise(nameTextView.getText().toString());
            MainActivity
                    .repo
                    .deleteExercise(exercise);
        });
    }

    public static ExerciseListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_exercise, parent, false);
        return new ExerciseListViewHolder(view);
    }

    public void bind(String name,
                     String type,
                     String movement) {
        nameTextView.setText(name);
        typeTextView.setText(type);
        movementTextView.setText(movement);
    }

}