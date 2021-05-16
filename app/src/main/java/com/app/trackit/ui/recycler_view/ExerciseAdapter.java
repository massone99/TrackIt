package com.app.trackit.ui.recycler_view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.MainActivity;
import com.app.trackit.R;
import com.app.trackit.model.Exercise;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private final String TAG = "ExerciseAdapter";
    private List<Exercise> exerciseList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final MaterialTextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (MaterialTextView) view.findViewById(R.id.exercise_item_text_view);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public ExerciseAdapter() {
        loadExercises();
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_exercise, parent, false);
        ExerciseAdapter.ViewHolder holder = new ExerciseAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(this.exerciseList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return this.exerciseList.size();
    }

    private void loadExercises() {
        try {
            this.exerciseList = MainActivity.repo.exerciseDao.loadAllExercises().get();
        } catch (Exception e) {
            Log.d(TAG, "ConcurrentException");
        }
    }

}
