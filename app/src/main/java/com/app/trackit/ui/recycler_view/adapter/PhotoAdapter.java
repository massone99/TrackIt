package com.app.trackit.ui.recycler_view.adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.app.trackit.model.Photo;
import com.app.trackit.model.viewmodel.PhotosViewModel;
import com.app.trackit.ui.recycler_view.viewholder.PhotoViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PhotoAdapter extends ListAdapter<Photo, PhotoViewHolder> {

    private static final String TAG = "PhotoAdapter";

    private AppCompatActivity activity;
    private PhotosViewModel photosViewModel;

    public PhotoAdapter(@NonNull DiffUtil.ItemCallback<Photo> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (AppCompatActivity) parent.getContext();
        photosViewModel = new ViewModelProvider(activity).get(PhotosViewModel.class);

        return PhotoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo currentPhoto = getItem(position);
        holder.bind(new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY)
                .format(currentPhoto.getTimeStamp()));
        Log.d(TAG, currentPhoto.getUri().toString());
        Glide.with(activity)
                .load(currentPhoto.getUri())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(holder.getImageView());
    }

    public static class PhotoDiff extends DiffUtil.ItemCallback<Photo> {

        @Override
        public boolean areItemsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.getTimeStamp().getTime() ==  newItem.getTimeStamp().getTime();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.getTimeStamp().equals(newItem.getTimeStamp());
        }
    }
}
