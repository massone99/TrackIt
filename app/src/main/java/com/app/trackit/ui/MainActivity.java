package com.app.trackit.ui;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.app.trackit.R;
import com.app.trackit.model.utility.ReminderReceiver;
import com.app.trackit.model.utility.Utilities;
import com.app.trackit.model.viewmodel.PhotosViewModel;
import com.app.trackit.model.viewmodel.WorkoutListViewModel;
import com.app.trackit.model.db.TrackItRepository;
import com.app.trackit.ui.components.AddFab;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    public static TrackItRepository repo;
    public final static String notificationChannelId = "TrackIt";


    private static final int MINUTE = 1000*60;
    private static final int HOUR = MINUTE*60;
    private static final int DAY = HOUR*24;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To disable the Night mode. It will be developed in the future releases
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        repo = new TrackItRepository(getApplication());

        createNotificationChannel();
        Intent intent = new Intent(MainActivity.this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                MainActivity.this,
                0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        // A reminder to do a photo every day
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + (1000*60),
                (AlarmManager.INTERVAL_FIFTEEN_MINUTES/15),
                pendingIntent);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
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
                return Utilities.loadFragment(fragment, getSupportFragmentManager());
            }
        });

        if (savedInstanceState == null) {
            WorkoutListViewModel model = new ViewModelProvider(this).get(WorkoutListViewModel.class);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            WorkoutsFragment workoutsFragment = new WorkoutsFragment();
            transaction.replace(R.id.fragment_container_view, workoutsFragment);
            transaction.commit();
        }
    }

    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "TrackIt";
            String description = "The TrackIt Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("TrackIt", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}