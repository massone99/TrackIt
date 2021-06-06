 package com.app.trackit.model.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.trackit.R;
import com.app.trackit.model.Photo;
import com.app.trackit.model.db.TrackItRepository;
import com.app.trackit.ui.MainActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PhotosViewModel extends AndroidViewModel {

    private final TrackItRepository repository;

    private MutableLiveData<Bitmap> bitmap = new MutableLiveData<>();

    /**
     * A ViewModel to manage the photos taken by the user
     * @param application
     */
    public PhotosViewModel(Application application) {
        super(application);
        repository = MainActivity.repo;
        initBitmap();
    }

    public void addPhoto(Photo photo) {
        repository.addPhoto(photo);
    }

    public Photo getLastPhoto() {
        return repository.getLastPhoto();
    }

    public LiveData<List<Photo>> getAllByDate() {
        return repository.getAllPhotosByDate();
    }

    public void deletePhoto(Photo photo) {
        repository.deletePhoto(photo);
    }

    private void initBitmap() {
        Drawable drawable = ResourcesCompat.getDrawable(getApplication().getResources(),
                R.drawable.ic_launcher_foreground, getApplication().getTheme());
        Bitmap bitmap = drawableToBitmap(drawable);

        this.bitmap.setValue(bitmap);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap.setValue(bitmap);
    }

    public LiveData<Bitmap> getBitmap() {
        return bitmap;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}
