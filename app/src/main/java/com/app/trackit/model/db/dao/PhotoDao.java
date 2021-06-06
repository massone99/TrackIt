package com.app.trackit.model.db.dao;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.app.trackit.model.Photo;
import com.google.common.util.concurrent.ListenableFuture;

import java.nio.file.Path;
import java.util.List;

@Dao
public interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhoto(Photo photo);

    @Delete
    void deletePhoto(Photo photo);

    @Update
    void updatePhoto(Photo photo);

    @Query("SELECT * FROM photos ORDER BY timestamp DESC")
    LiveData<List<Photo>> getAllByDate();


    @Query("SELECT * FROM photos WHERE timestamp = (select max(timestamp) from photos)")
    ListenableFuture<Photo> getLastPhoto();

    @Query("SELECT * FROM photos WHERE timestamp = (select max(timestamp) from photos)")
    ListenableFuture<List<Photo>> getPhotos();

    @Query("SELECT * FROM photos WHERE uri = :uri ")
    ListenableFuture<Photo> getPhotoFromUri(Uri uri);
}
