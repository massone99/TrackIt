package com.app.trackit.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.app.trackit.model.utility.Utilities;
import com.app.trackit.model.viewmodel.PhotosViewModel;
import com.app.trackit.ui.recycler_view.adapter.PhotoAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private String currentPhotoPath;

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

                            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                            bitmap = rotateImage(bitmap, -90);
                            saveImageToPublicDir(bitmap);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
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

        photosViewModel.getObservablePhotos().observe(getViewLifecycleOwner(), photoList -> {
            if (photoList.size() == 0 ) {
                view.findViewById(R.id.photos_helper).setVisibility(View.VISIBLE);
            }
            photoAdapter.submitList(photoList);
        });

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
                Log.d(TAG, "photoFile != null");
                // WORKING CODE
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
        String time = new SimpleDateFormat("ddMMyyyy", Locale.ITALY).format(new Date());
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, time + ".jpg");
        if (image.exists() && (photosViewModel.getPhotoFromUri(Uri.fromFile(image)) == null)) {
            image.delete();
        }
        recyclerView.setAdapter(photoAdapter);
    }

    private void saveImageToPublicDir(Bitmap bitmap) {
        File file = new File(
                "/storage/emulated/0/Pictures/",
                new SimpleDateFormat("ddMMyyyy", Locale.ITALY).format(new Date())
                        + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        // CORRECT CODE LINE
        timestamp = new SimpleDateFormat("ddMMyyyy", Locale.ITALY).format(new Date());

        // FOR TESTING ONLY
//        timestamp = "30052021";
        // PREVIOUS CODE - WORKING
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
            currentPhotoPath = image.getAbsolutePath();
            return image;
        } else {
            return null;
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}
