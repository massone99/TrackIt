package com.app.trackit.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.trackit.R;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.LinkedList;
import java.util.List;

public class AddExerciseFragment extends Fragment {

    private static final String TAG = "AddExerciseFragment";

    private TextInputEditText exerciseNameEditText;
    private RadioGroup exerciseTypeRadio;
    private ChipGroup musclesChipGroup;
    private RadioGroup exerciseMovementRadio;
    private View rootView;

    private WorkoutViewModel model;

    public AddExerciseFragment() {
        super(R.layout.fragment_add_exercise);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_add_exercise, container, false);
        this.rootView.setTag(TAG);

        MaterialTextView textView = this.rootView.findViewById(R.id.new_exercise_title);
        this.exerciseNameEditText = this.rootView.findViewById(R.id.exercise_name_edit_text);
        this.exerciseTypeRadio = this.rootView.findViewById(R.id.type_of_exercise);
        this.musclesChipGroup = this.rootView.findViewById(R.id.muscles_chip_group);
        this.exerciseMovementRadio = this.rootView.findViewById(R.id.type_of_movement);
        Button addExerciseButton = this.rootView.findViewById(R.id.add_exercise_button);

        boolean edit;
        try {
            edit = getArguments().getBoolean("edit");
        } catch (NullPointerException e) {
            edit = false;
        }

        if (edit) {
            textView.setText("Modifica Esercizio");

            Exercise exercise = model.getExerciseFromId(getArguments().getInt("exerciseId"));
            int typeId;
            int movementId;
            exerciseNameEditText.setText(exercise.getName());
            if (exercise.getType().equals("Ripetizioni")) {
                typeId = R.id.concentric_radio;
            } else {
                typeId = R.id.isometric_radio;
            }
            exerciseTypeRadio.check(typeId);
            if (exercise.getMovement().equals("Spinta")) {
                movementId = R.id.pushing_radio;
            } else {
                movementId = R.id.pulling_radio;
            }
            exerciseMovementRadio.check(movementId);
        }

        addExerciseButton.setOnClickListener(v -> {
            if (checkForm()) {
                Exercise oldExercise = model.getExerciseFromName(exerciseNameEditText.getText().toString());
                if (oldExercise != null) {
                    Log.d(TAG, "exercise update");
                    model.updateExercise(oldExercise);
                } else {
                    RadioButton checkedType = this.rootView.findViewById(exerciseTypeRadio.getCheckedRadioButtonId());
                    RadioButton checkedMovement = this.rootView.findViewById(exerciseMovementRadio.getCheckedRadioButtonId());

                    int muscleChipId = musclesChipGroup.getCheckedChipId();
                    Chip muscle = musclesChipGroup.findViewById(muscleChipId);

                    Exercise exercise = new Exercise(
                            exerciseNameEditText.getText().toString(),
                            checkedType.getText().toString(),
                            checkedMovement.getText().toString(), muscle.getText().toString());

                    MainActivity.repo.insertExercise(exercise);
                }
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return this.rootView;
    }

    public boolean checkForm() {
        if (exerciseNameEditText.getText().toString().isEmpty() ||
            musclesChipGroup.getCheckedChipId() == View.NO_ID) {
            Toast toast = Toast.makeText(getContext(),
                    "Riempi tutti i campi!",
                    Toast.LENGTH_LONG);

            toast.show();
            return false;
        }
        return true;
    }
}
