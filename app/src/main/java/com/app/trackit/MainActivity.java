package com.app.trackit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MenuItem;

import com.app.trackit.model.db.TrackItRepository;
import com.app.trackit.ui.AddExerciseFragment;
import com.app.trackit.ui.ExercisesFragment;
import com.app.trackit.ui.HomeFragment;
import com.app.trackit.ui.animation.FabAnimation;
import com.app.trackit.ui.components.ExpandableFab;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static TrackItRepository repo;

    protected ExpandableFab fab;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new TrackItRepository(getApplication());
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.fragment_container_view, homeFragment);
            transaction.commit();

            this.fab = new ExpandableFab(
                    findViewById(R.id.fab_add),
                    findViewById(R.id.fab_add_exercise),
                    findViewById(R.id.fab_add_workout)
            );

            FabAnimation.init(findViewById(R.id.fab_add_exercise));
            FabAnimation.init(findViewById(R.id.fab_add_workout));

            this.fab.getMainFab().setOnClickListener(v -> this.fab.onClick());
            this.fab.getSecondaryFab().setOnClickListener(v -> {
                this.fab.onClick();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                );
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.fragment_container_view, AddExerciseFragment.class, null);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });

            BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
            navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;

                    switch (item.getItemId()) {
                        case R.id.workouts_page:
                            fragment = new HomeFragment();
                            break;

                        case R.id.exercises_page:
                            fragment = new ExercisesFragment();
                            break;

                        case R.id.progress_page:
                            break;

                        case R.id.photos_page:
                            break;
                    }

                    return loadFragment(fragment);
                }
            });
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}