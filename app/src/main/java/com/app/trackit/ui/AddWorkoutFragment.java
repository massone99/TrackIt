package com.app.trackit.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.MainActivity;
import com.app.trackit.R;
import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Workout;
import com.app.trackit.model.viewmodel.WorkoutViewModel;
import com.app.trackit.ui.recycler_view.adapter.WorkoutAdapter;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class AddWorkoutFragment extends Fragment implements LifecycleOwner {

    private static final String TAG = "AddWorkoutFragment";
    protected RecyclerView exercisesRecyclerView;
    protected WorkoutAdapter exercisesAdapter;
    private WorkoutViewModel model;

    private MaterialTextView confirmButton;
    private Workout workout;

    public AddWorkoutFragment() {
        super(R.layout.fragment_add_workout);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // When you access this fragment a Workout object should be created.
        // By the way, the memorization on the database should happen only
        // after the "Confirm Workout" button is pressed
        model = new ViewModelProvider(requireActivity()).get(WorkoutViewModel.class);
//        Here we convert the current date in millis to the dd-mm-yyyy format
//        and then convert it to a String so it can be stored in the DB
        boolean edit = false;
        try {
            edit = this.getArguments().getBoolean("edit");
        } catch (NullPointerException e) {
            edit = false;
        }
        if (!edit) {
            Workout current = null;
            try {
                current = MainActivity.repo.getCurrentWorkout();
                if (current.getConfirmed() == 1) {
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
        });

        exercisesRecyclerView = rootView.findViewById(R.id.recycler_view_workout_exercises);

        exercisesAdapter = new WorkoutAdapter(
                new WorkoutAdapter.ExerciseDiff(),
                model,
                this,
                getActivity());
        exercisesRecyclerView.setAdapter(exercisesAdapter);
        exercisesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                MainActivity.repo.confirmWorkout(workout);
                getActivity().getSupportFragmentManager().popBackStack();

                workout.setConfirmed(1);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);


        TextView title = rootView.findViewById(R.id.new_workout_title);
        title.setText("Allenamento del " + workout.getDate());

        confirmButton = rootView.findViewById(R.id.confirm_workout_button);
        MaterialTextView discardButton = rootView.findViewById(R.id.discard_workout_button);

        TextView addExerciseToWorkout = rootView.findViewById(R.id.add_exercise_to_workout);
        addExerciseToWorkout.setOnClickListener(v -> {
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
        });
        confirmButton.setOnClickListener(v -> {
            /*
            Memorizzare nel Db i SET (con i valori settati) e il WORKOUT
            */
            MainActivity.repo.confirmWorkout(workout);
            getActivity().getSupportFragmentManager().popBackStack();

            workout.setConfirmed(1);
//            clearBackStack();
        });
        discardButton.setOnClickListener(v -> {
            MainActivity.repo.deleteIncompleteWorkout(MainActivity.repo.getCurrentWorkout());
            getActivity().getSupportFragmentManager().popBackStack();

//            clearBackStack();
        });

        return rootView;
    }

    @Override
    public void onResume() {
        // FIXME: esercizi spariscono se non seleziono nuovo
        //  esercizio nella schermata di scelta

        super.onResume();
        List<PerformedExercise> exercises = model.getObservableExercises(workout.getWorkoutId()).getValue();
        if (exercises != null) {
            exercisesAdapter.submitList(exercises);
        }

        // Checks if the RecyclerView is empty
        boolean val = model.getExercises(
                MainActivity.repo.getCurrentWorkout().getWorkoutId()
        ).size() == 0;

        if (val) {
            confirmButton.setVisibility(View.GONE);
        } else {
            confirmButton.setVisibility(View.VISIBLE);
        }
    }

    private void clearBackStack() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
