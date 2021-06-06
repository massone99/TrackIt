package com.app.trackit.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotosFragment.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                photosViewModel.setBitmap(imageBitmap);
            }
        }
    }*/

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

    public void hideFab() {
        this.fab.hide();
    }

    public void hideNav() {
        navigationView.setVisibility(View.GONE);
    }
}