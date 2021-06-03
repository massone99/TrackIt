package com.app.trackit.ui.recycler_view.viewholder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.app.trackit.ui.MainActivity;
import com.app.trackit.R;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.Set;

public class SetListViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "SetListViewHolder";
    // The various elements of the layout
    private final TextView numberTextView;
    private final TextView idTextView;
    private final EditText weightEditText;
    private final EditText timeEdiText;
    private final EditText repsEditText;

    // The Set represented
    private Set currentSet;

    private final WorkoutViewModel model;

    public SetListViewHolder(@NonNull View itemView, WorkoutViewModel model) {
        super(itemView);

        numberTextView = itemView.findViewById(R.id.set_number_text_view);
        idTextView = itemView.findViewById(R.id.set_id);
        weightEditText = itemView.findViewById(R.id.set_kg_text_view);
        timeEdiText = itemView.findViewById(R.id.set_time_text_view);
        repsEditText = itemView.findViewById(R.id.set_reps_text_view);

        this.model = model;

        initTextWatchers();

        ImageButton removeSet = itemView.findViewById(R.id.remove_set);
        removeSet.setOnClickListener(v -> {
            MainActivity.repo.deleteSet(
                    MainActivity.repo.getSetFromId(Integer.parseInt(idTextView.getText().toString()))
            );
        });
    }

    public static SetListViewHolder create(ViewGroup parent, WorkoutViewModel model) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_exercise_set_reps, parent, false);
        return new SetListViewHolder(view, model);
    }

    public void bind(Set set) {
        String number = set.getNumber() + ".";

        this.currentSet = set;

        numberTextView.setText(number);
        idTextView.setText(String.valueOf(set.getId()));
        weightEditText.setText(String.valueOf(set.getWeight()));
        timeEdiText.setText(String.valueOf(set.getReps()));
        repsEditText.setText(String.valueOf(set.getReps()));
    }

    public void showRightLayout() {
        Exercise exercise = null;
        try {
            exercise = MainActivity.repo.getExercise(
                    MainActivity.repo.getPerformedExercise(
                            currentSet.getParentPerformedExerciseId()
                    ).getName()
            );
        } catch (NullPointerException ignored) {
        }
        if (exercise.getType().equals("Ripetizioni")) {
            itemView.findViewById(R.id.set_time_label).setVisibility(View.GONE);
            itemView.findViewById(R.id.set_time_text_view).setVisibility(View.GONE);

            EditText reps = itemView.findViewById(R.id.set_reps_text_view);
            ImageButton delete = itemView.findViewById(R.id.remove_set);

            // To make the layout display correctly, it is needed to change
            // the constraints dynamically
            ConstraintLayout constraintLayout = itemView.findViewById(R.id.set_constraint_layout);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);

            constraintSet.connect(delete.getId(), ConstraintSet.START, reps.getId(), ConstraintSet.END, 16);
            constraintSet.applyTo(constraintLayout);

        } else {
            itemView.findViewById(R.id.set_reps_label).setVisibility(View.GONE);
            itemView.findViewById(R.id.set_reps_text_view).setVisibility(View.GONE);
        }
    }

    private void initTextWatchers() {
        weightEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.addPendingSetChanges(updateWeight());
            }
        });
        repsEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.addPendingSetChanges(updateReps());
            }
        });
        timeEdiText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.addPendingSetChanges(updateTime());
            }
        });
    }

    private Set updateWeight() {
        Set set = MainActivity.repo.getSetFromId(Integer.parseInt(idTextView.getText().toString()));
        try {
            set.setWeight(Integer.parseInt(weightEditText.getText().toString()));
        } catch (NumberFormatException ignored) {}
        return set;
    }

    private Set updateReps() {
        Set set = MainActivity.repo.getSetFromId(Integer.parseInt(idTextView.getText().toString()));
        try {
            set.setReps(Integer.parseInt(repsEditText.getText().toString()));
        } catch (NumberFormatException ignored) {}
        return set;
    }

    private Set updateTime() {
        Set set = MainActivity.repo.getSetFromId(Integer.parseInt(idTextView.getText().toString()));
        try {
            set.setReps(Integer.parseInt(timeEdiText.getText().toString()));
        } catch (NumberFormatException ignored) {}
        return set;
    }
}
