package com.app.trackit.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.R;
import com.app.trackit.model.Photo;
import com.app.trackit.model.viewmodel.PhotosViewModel;
import com.app.trackit.ui.recycler_view.adapter.PhotoAdapter;

import java.io.File;
import java.io.IOException;
import java.net.SocketImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PhotosFragment extends Fragment implements LifecycleOwner {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private final String TAG = "PhotosFragment";
    private File directory;

    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;

    private Button deleteButton;

    private PhotosViewModel photosViewModel;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    private File photoFile;
    private String timestamp;

    public PhotosFragment() {
        super(R.layout.fragment_photos);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photosViewModel = new ViewModelProvider(this).get(PhotosViewModel.class);
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            photosViewModel.addPhoto(
                                    new Photo(new SimpleDateFormat("ddMMyyyy", Locale.ITALY).parse(timestamp),
                                    Uri.fromFile(photoFile)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
        photosViewModel.getObservablePhotos().observe(this, photoList -> {
            photoAdapter.submitList(photoList);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.grid_view);
        photoAdapter = new PhotoAdapter(new PhotoAdapter.PhotoDiff());

        recyclerView.setAdapter(photoAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        Button takePhoto = view.findViewById(R.id.take_photo);
        takePhoto.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(getContext(),
                        "com.app.trackit.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                activityResultLauncher.launch(takePictureIntent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        String time = new SimpleDateFormat("ddMMyyyy").format(new Date());
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, time + ".jpg");
        if (image.exists() && (photosViewModel.getPhotoFromUri(Uri.fromFile(image)) == null)) {
            image.delete();
        }
        recyclerView.setAdapter(photoAdapter);
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        // CORRECT CODE LINE
        timestamp = new SimpleDateFormat("ddMMyyyy", Locale.ITALY).format(new Date());

        // FOR TESTING ONLY
        // timestamp = "13062021";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, timestamp + ".jpg");
        if (image.exists()) {
            Log.d(TAG, "File already exists!");
            photosViewModel.deletePhoto(photosViewModel.getPhotoFromUri(Uri.fromFile(image)));
            image.delete();
            Log.d(TAG, "Old file deleted!");
            Log.d(TAG, String.valueOf(image.exists()));
        }
        if (image.createNewFile()) {
            return image;
        } else {
            return null;
        }
    }
}
