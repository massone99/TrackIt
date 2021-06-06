package com.app.trackit.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.app.trackit.R;
import com.app.trackit.model.Photo;
import com.app.trackit.model.viewmodel.PhotosViewModel;
import com.app.trackit.ui.recycler_view.adapter.GridAdapter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class PhotosFragment extends Fragment implements LifecycleOwner {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private final String TAG = "PhotosFragment";
    private File directory;

    private GridView gridView;
    private Button takePhoto;

    private PhotosViewModel photosViewModel;
    private ActivityResultLauncher<Intent> activityResultLauncher;


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
                        Uri imageUri = photosViewModel.getLastPhoto().getUri();
                        try {
                            photosViewModel.setBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri));
                        } catch (IOException e) {
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
        gridView = view.findViewById(R.id.grid_view);
        gridView.setAdapter(new GridAdapter(getActivity(), photosViewModel.getAllByDate()));

        /*photosViewModel.getBitmap().observe(getViewLifecycleOwner(), bitmap -> {
            imageView.setImageBitmap(bitmap);
        });*/


        //        gridView = rootView.findViewById(R.id.grid_view);
        takePhoto = view.findViewById(R.id.take_photo);
        takePhoto.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(getContext(),
                        "com.app.trackit.fileprovider",
                        photoFile);
                // Attenzione forse la new Date non coinciderà con il nome del file
                photosViewModel.addPhoto(new Photo(new Date(), photoUri));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                activityResultLauncher.launch(takePictureIntent);
            }
        });

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timestamp = new SimpleDateFormat("ddMMyyyy").format(new Date());
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir, timestamp + ".jpg");
        if (image.exists()) {
            Log.d(TAG, "File already exists!");
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
