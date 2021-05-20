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
import com.app.trackit.ui.recycler_view.viewholder.ExerciseViewHolder;
import com.google.android.material.card.MaterialCardView;

public class PickExerciseViewHolder extends RecyclerView.ViewHolder {

    private final MaterialCardView cardView;
    private final TextView nameTextView;
    private final TextView typeTextView;
    private final TextView movementTextView;

    public PickExerciseViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.pick_exercise_item_card_view);
        nameTextView = itemView.findViewById(R.id.pick_exercise_item_text_view);
        typeTextView = itemView.findViewById(R.id.pick_exercise_item_type_text_view);
        movementTextView = itemView.findViewById(R.id.pick_exercise_item_movement_text_view);

        cardView.setOnClickListener(v -> {
            Exercise exercise = null;
            try {
                exercise = MainActivity
                        .repo
                        .getExercise(nameTextView.getText().toString())
                        .get();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    public void bind(String name,
                     String type,
                     String movement) {
        nameTextView.setText(name);
        typeTextView.setText(type);
        movementTextView.setText(movement);
    }

    public static PickExerciseViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_pick_exercise, parent, false);
        return new PickExerciseViewHolder(view);
    }

}
