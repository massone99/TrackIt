package com.app.trackit.ui.components;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.trackit.R;
import com.app.trackit.ui.AddExerciseFragment;
import com.app.trackit.ui.AddWorkoutFragment;
import com.app.trackit.ui.MainActivity;
import com.app.trackit.ui.animation.FabAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddFab {

    private boolean clicked;
    protected FloatingActionButton mainFab;
    protected FloatingActionButton exerciseFab;
    protected FloatingActionButton workoutFab;

    public AddFab(FloatingActionButton mainFab,
                  FloatingActionButton exerciseFab,
                  FloatingActionButton workoutFab,
                  View view,
                  Fragment fragment) {
        this.mainFab = mainFab;
        this.exerciseFab = exerciseFab;
        this.workoutFab = workoutFab;
        FabAnimation.init(view.findViewById(R.id.fab_add_exercise));
        FabAnimation.init(view.findViewById(R.id.fab_add_workout));
        initListeners(fragment);
    }

    public FloatingActionButton getMainFab() {
        return mainFab;
    }

    public FloatingActionButton getExerciseFab() {
        return exerciseFab;
    }

    public FloatingActionButton getWorkoutFab() {
        return workoutFab;
    }

    private void initListeners(Fragment fragment) {
        FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
        );
        mainFab.setOnClickListener(v -> onClick());
        exerciseFab.setOnClickListener(v -> {
            onClick();
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.replace(R.id.fragment_container_view, AddExerciseFragment.class, null);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        workoutFab.setOnClickListener(v -> {
            onClick();
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.replace(R.id.fragment_container_view, AddWorkoutFragment.class, null);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    public void onClick() {
        clicked = FabAnimation.rotateFab(this.mainFab, !clicked);
        if (clicked) {
            FabAnimation.showIn(this.exerciseFab);
            FabAnimation.showIn(this.workoutFab);
        } else {
            FabAnimation.showOut(this.exerciseFab);
            FabAnimation.showOut(this.workoutFab);
        }
    }

    public void hide() {
        mainFab.hide();
    }

    public void show() {
        mainFab.show();
    }

}
