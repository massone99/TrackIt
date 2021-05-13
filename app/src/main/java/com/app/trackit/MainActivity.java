package com.app.trackit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.os.Bundle;

import com.app.trackit.model.db.AppDatabase;
import com.app.trackit.ui.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private static AppDatabase db;

    public static AppDatabase getDb() {
        return db;
    }

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        // Questo perchè in caso di capovolgimento device per esempio
        // il Fragment viene generato automaticamente
        // getSupportActionBar().hide();
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // AddExerciseFragment addExerciseFragment = new AddExerciseFragment();
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.fragment_container_view, homeFragment);
            transaction.commit();
        }
    }
}