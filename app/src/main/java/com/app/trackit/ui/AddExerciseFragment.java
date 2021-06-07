package com.app.trackit.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.trackit.R;
import com.app.trackit.model.Exercise;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class AddExerciseFragment extends Fragment {

    private static final String TAG = "AddExerciseFragment";

    private TextInputEditText exerciseNameEditText;
    private RadioGroup exerciseTypeRadio;
    private ChipGroup musclesChipGroup;
    private RadioGroup exerciseMovementRadio;
    private View rootView;

    private WorkoutViewModel model;

    private Exercise pendingExercise;

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
            model.deleteExercise(exercise);
            int typeId;
            int movementId;
            exerciseNameEditText.setText(exercise.getName());
            if (exercise.getType().equals("Ripetizioni")) {
                typeId = R.id.concentric_radio;
            } else {
                typeId = R.id.isometric_radio;
            }

            Log.d(TAG, exercise.getMuscle());
            switch (exercise.getMuscle()) {
                case "Collo":
                    musclesChipGroup.check(R.id.neck);
                    break;
                case "Spalle":
                    musclesChipGroup.check(R.id.shoulders);
                    break;
                case "Braccia":
                    musclesChipGroup.check(R.id.arms);
                    break;
                case "Avambraccia":
                    musclesChipGroup.check(R.id.forearms);
                    break;
                case "Dorso":
                    musclesChipGroup.check(R.id.back);
                    break;
                case "Petto":
                    musclesChipGroup.check(R.id.chest);
                    break;
                case "Addome":
                    musclesChipGroup.check(R.id.waist);
                    break;
                case "Fianchi":
                    musclesChipGroup.check(R.id.hips);
                    break;
                case "Cosce":
                    musclesChipGroup.check(R.id.thighs);
                    break;
                case "Polpacci":
                    musclesChipGroup.check(R.id.calves);
                    break;
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

                /*Exercise oldExercise = model.getExerciseFromName(exerciseNameEditText.getText().toString());
                if (oldExercise != null) {
                    model.deleteExercise(oldExercise);
                }*/
               saveExercise();
            }
        });

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (checkForm()){
                    saveExercise();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return this.rootView;
    }

    private boolean checkForm() {
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

    private void saveExercise() {
        RadioButton checkedType = this.rootView.findViewById(exerciseTypeRadio.getCheckedRadioButtonId());
        RadioButton checkedMovement = this.rootView.findViewById(exerciseMovementRadio.getCheckedRadioButtonId());
        int muscleChipId = musclesChipGroup.getCheckedChipId();
        Chip muscle = musclesChipGroup.findViewById(muscleChipId);

        pendingExercise = new Exercise(
                exerciseNameEditText.getText().toString(),
                checkedType.getText().toString(),
                checkedMovement.getText().toString(), muscle.getText().toString());
        model.insertExercise(pendingExercise);
        MainActivity.hideKeyboard(requireActivity());
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
