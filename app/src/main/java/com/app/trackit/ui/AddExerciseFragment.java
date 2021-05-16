package com.app.trackit.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.trackit.MainActivity;
import com.app.trackit.R;
import com.app.trackit.model.Muscle;
import com.app.trackit.model.Exercise;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.LinkedList;
import java.util.List;

public class AddExerciseFragment extends Fragment {

    private static final String TAG = "AddExerciseFragment";

    private TextInputEditText exerciseNameEditText;
    private RadioGroup exerciseTypeRadio;
    private ChipGroup musclesChipGroup;
    private RadioGroup exerciseMovementRadio;
    private Button addExerciseButton;
    private View rootView;

    public AddExerciseFragment() {
        super(R.layout.fragment_add_exercise);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // In this method the initialization of the dataset should be completed
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_add_exercise, container, false);
        this.rootView.setTag(TAG);

        this.exerciseNameEditText = this.rootView.findViewById(R.id.exercise_name_edit_text);
        this.exerciseTypeRadio = this.rootView.findViewById(R.id.type_of_exercise);
        this.musclesChipGroup = this.rootView.findViewById(R.id.muscles_chip_group);
        this.exerciseMovementRadio = this.rootView.findViewById(R.id.type_of_movement);
        this.addExerciseButton = this.rootView.findViewById(R.id.add_exercise_button);

        this.addExerciseButton.setOnClickListener(v -> {
            RadioButton checkedType = this.rootView.findViewById(exerciseTypeRadio.getCheckedRadioButtonId());
            RadioButton checkedMovement = this.rootView.findViewById(exerciseMovementRadio.getCheckedRadioButtonId());
            List<Integer> ids = musclesChipGroup.getCheckedChipIds();
            List<Muscle> muscles = new LinkedList<>();
            for (Integer id:ids){
                Chip muscle = musclesChipGroup.findViewById(id);
                muscles.add(new Muscle(muscle.getText().toString()));
            }
            Exercise exercise = new Exercise(
                    exerciseNameEditText.getText().toString(),
                    checkedType.getText().toString(),
                    checkedMovement.getText().toString());
            exercise.setMuscles(muscles);

            MainActivity.repo.insert(exercise);

            for (Muscle muscle: muscles){
                muscle.addExercise(exercise);
            }
            getActivity().getSupportFragmentManager().popBackStack();
        });


        return this.rootView;
    }
}
