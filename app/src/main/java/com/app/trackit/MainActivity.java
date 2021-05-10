package com.app.trackit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.app.trackit.ui.AddExerciseFragment;
import com.app.trackit.ui.HomeFragment;

public class MainActivity extends AppCompatActivity {

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Questo perchè in caso di capovolgimento device per esempio
        // il Fragment viene generato automaticamente
        // getSupportActionBar().hide();
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            AddExerciseFragment addExerciseFragment = new AddExerciseFragment();
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.fragment_container_view, homeFragment);
            transaction.commit();
        }
    }
}