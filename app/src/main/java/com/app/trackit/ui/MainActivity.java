package com.app.trackit.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.app.trackit.R;
import com.app.trackit.model.viewmodel.PhotosViewModel;
import com.app.trackit.model.viewmodel.WorkoutListViewModel;
import com.app.trackit.model.db.TrackItRepository;
import com.app.trackit.ui.components.AddFab;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    public static TrackItRepository repo;

    private PhotosViewModel photosViewModel;

    private AddFab fab;
    private BottomNavigationView navigationView;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new TrackItRepository(getApplication());
        photosViewModel = new ViewModelProvider(this).get(PhotosViewModel.class);
        if (savedInstanceState == null) {
            WorkoutListViewModel model = new ViewModelProvider(this).get(WorkoutListViewModel.class);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            WorkoutsFragment workoutsFragment = new WorkoutsFragment();
            transaction.replace(R.id.fragment_container_view, workoutsFragment);
            transaction.commit();

            navigationView = findViewById(R.id.bottom_navigation);
            navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;

                    getSupportFragmentManager().popBackStack();
                    switch (item.getItemId()) {
                        case R.id.workouts_page:
                            fragment = new WorkoutsFragment();
                            break;
                        case R.id.exercises_page:
                            fragment = new ExercisesFragment();
                            break;
                        case R.id.progress_page:
                            fragment = new ProgressFragment();
                            break;
                        case R.id.photos_page:
                            fragment = new PhotosFragment();
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}