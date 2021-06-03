package com.app.trackit.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.R;
import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Workout;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.app.trackit.ui.recycler_view.adapter.WorkoutAdapter;
import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddWorkoutFragment extends Fragment implements LifecycleOwner {

    private static final String TAG = "AddWorkoutFragment";
    protected RecyclerView exercisesRecyclerView;
    protected WorkoutAdapter exercisesAdapter;
    private WorkoutViewModel model;

    private EditText titleEditText;

    private MaterialTextView confirmButton;
    private Workout workout;

    private boolean addExercise;

    public AddWorkoutFragment() {
        super(R.layout.fragment_add_workout);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addExercise = false;
        // When you access this fragment a Workout object should be created.
        // By the way, the memorization on the database should happen only
        // after the "Confirm Workout" button is pressed
        model = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
//        Here we convert the current date in millis to the dd-mm-yyyy format
//        and then convert it to a String so it can be stored in the DB
        boolean edit;
        try {
            edit = this.getArguments().getBoolean("edit");
        } catch (NullPointerException e) {
            edit = false;
        }
        if (!edit) {
            Workout current;
            try {
                current = MainActivity.repo.getCurrentWorkout();
                if (current.getConfirmed()) {
                    workout = new Workout();
                    MainActivity.repo.insertWorkout(workout);
                } else {
                    workout = current;
                }
            } catch (NullPointerException e) {
                workout = new Workout();
                MainActivity.repo.insertWorkout(workout);
            }
        } else {
            int id = getArguments().getInt("workoutId");
            workout = MainActivity.repo.getWorkoutFromId(id);
            MainActivity.repo.editWorkout(id);
            Bundle bundle = new Bundle();
            bundle.putBoolean("edit", false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_workout, container, false);
        rootView.setTag(TAG);

        model.getObservableExercises(MainActivity.repo.getCurrentWorkout().getWorkoutId()).observe(getViewLifecycleOwner(), performedExercises -> {
            exercisesAdapter.submitList(performedExercises);
            exercisesAdapter.notifyDataSetChanged();
        });

        exercisesRecyclerView = rootView.findViewById(R.id.recycler_view_workout_exercises);

        exercisesAdapter = new WorkoutAdapter(
                new WorkoutAdapter.ExerciseDiff(),
                model,
                this,
                getActivity());
        exercisesRecyclerView.setAdapter(exercisesAdapter);
        exercisesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        titleEditText = rootView.findViewById(R.id.new_workout_title);
        titleEditText.setText(new SimpleDateFormat("dd/MM/yyyy").format(workout.getDate()));

        confirmButton = rootView.findViewById(R.id.confirm_workout_button);
        MaterialTextView discardButton = rootView.findViewById(R.id.discard_workout_button);

        TextView addExerciseToWorkout = rootView.findViewById(R.id.add_exercise_to_workout);
        addExerciseToWorkout.setOnClickListener(v -> {
            addExercise = true;
            PickExerciseFragment fragment = new PickExerciseFragment(
                    this,
                    exercisesAdapter,
                    workout.getWorkoutId()
            );
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    )
                    .replace(R.id.fragment_container_view, fragment, "PickExerciseFragment")
                    .addToBackStack(null)
                    .commit();
            model.submitSetChanges();
        });
        confirmButton.setOnClickListener(v -> {

            saveWorkout();
        });

        discardButton.setOnClickListener(v -> {
            MainActivity.repo.deleteIncompleteWorkout(MainActivity.repo.getCurrentWorkout());
            getActivity().getSupportFragmentManager().popBackStack();
        });

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                saveWorkout();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<PerformedExercise> exercises = model.getObservableExercises(workout.getWorkoutId()).getValue();
        if (exercises != null) {
            exercisesAdapter.submitList(exercises);
        }

        // Checks if the RecyclerView is empty
        boolean val = false;
        Workout currentWorkout = MainActivity.repo.getCurrentWorkout();
        if (currentWorkout != null) {
            val = model.getExercises(
                    MainActivity.repo.getCurrentWorkout().getWorkoutId()
            ).size() == 0;
        }
        if (val) {
            confirmButton.setVisibility(View.GONE);
        } else {
            confirmButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!addExercise) {
            model.editWorkout();
        }
        /*Workout currentWorkout = MainActivity.repo.getCurrentWorkout();
        if (currentWorkout != null) {
            saveWorkout();
        }*/
    }

    private void saveWorkout() {
        Workout currentWorkout = MainActivity.repo.getCurrentWorkout();
        if (currentWorkout != null) {
            model.submitSetChanges();
            String dateString = titleEditText.getText().toString();
            if (dateString.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
                Log.d(TAG, dateString);
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                currentWorkout.setDate(date);

                Log.d(TAG, currentWorkout.getDate().toString());

                model.updateWorkout(currentWorkout);
                model.updateExercisesDate(currentWorkout.getWorkoutId(), currentWorkout.getDate());
            }

            MainActivity.repo.convalidateWorkout(currentWorkout);
            workout.setConfirmed(true);
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
