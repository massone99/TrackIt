package com.app.trackit.model.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.app.trackit.model.db.TrackItRepository;
import com.app.trackit.ui.MainActivity;

public class PhotosViewModel extends AndroidViewModel {

    private final TrackItRepository repository;

    /**
     * A ViewModel to manage the photos taken by the user
     * @param application
     */
    public PhotosViewModel(@NonNull Application application) {
        super(application);
        repository = MainActivity.repo;
    }

}
