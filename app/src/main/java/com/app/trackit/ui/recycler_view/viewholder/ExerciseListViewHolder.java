package com.app.trackit.ui.recycler_view.viewholder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.R;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.app.trackit.ui.AddExerciseFragment;
import com.app.trackit.ui.MainActivity;
import com.app.trackit.ui.recycler_view.adapter.ExerciseListAdapter;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.card.MaterialCardView;

public class ExerciseListViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "ExerciseListViewHolder";

    private final TextView nameTextView;
    private final TextView typeTextView;
    private final TextView movementTextView;

    private final MaterialFavoriteButton favoriteButton;

    private Exercise currentExercise;
    private WorkoutViewModel model;

    private final ExerciseListAdapter adapter;

    public ExerciseListViewHolder(@NonNull View itemView, ExerciseListAdapter adapter) {
        super(itemView);

        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        model = new ViewModelProvider(activity).get(WorkoutViewModel.class);

        this.adapter = adapter;

        nameTextView = itemView.findViewById(R.id.exercise_item_text_view);
        typeTextView = itemView.findViewById(R.id.exercise_item_type_text_view);
        movementTextView = itemView.findViewById(R.id.exercise_item_movement_text_view);


        ImageButton deleteButton = itemView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            Exercise exercise;
            exercise = MainActivity
                    .repo
                    .getExercise(nameTextView.getText().toString());
            MainActivity
                    .repo
                    .deleteExercise(exercise);
            adapter.notifyDataSetChanged();
        });

        Log.d(TAG, nameTextView.getText().toString());

        favoriteButton = itemView.findViewById(R.id.add_to_favorites);
        favoriteButton.setOnFavoriteChangeListener((buttonView, favorite) -> {
            model.setExerciseAsFavorite(currentExercise.getExerciseId(), favorite);
        });

        MaterialCardView cardView = itemView.findViewById(R.id.exercise_item_card_view);
        cardView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("edit", true);
            bundle.putInt("exerciseId", MainActivity.repo.getExercise(nameTextView.getText().toString()).getExerciseId());
            AddExerciseFragment fragment = new AddExerciseFragment();
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

    public static ExerciseListViewHolder create(ViewGroup parent, ExerciseListAdapter adapter) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_exercise, parent, false);
        return new ExerciseListViewHolder(view, adapter);
    }

    public void bind(String name,
                     String type,
                     String movement) {
        nameTextView.setText(name);
        typeTextView.setText(type);
        movementTextView.setText(movement);

        currentExercise = model.getExerciseFromName(nameTextView.getText().toString());
        try {
            favoriteButton.setFavorite(model.isExerciseFavorite(currentExercise.getExerciseId()));
        } catch (NullPointerException ignored) {}
    }


    public MaterialFavoriteButton getFavoriteButton() {
        return favoriteButton;
    }


}