package com.app.trackit.ui.recycler_view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.R;
import com.app.trackit.model.viewmodel.PhotosViewModel;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private PhotosViewModel photosViewModel;

    private final ImageView imageView;
    private final TextView textView;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);

        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        this.textView = (TextView) itemView.findViewById(R.id.grid_image_title);
        this.imageView = (ImageView) itemView.findViewById(R.id.grid_image);
    }

    public void bind(String imageTitle) {
        textView.setText(imageTitle);
    }

    public TextView getTextView() {
        return textView;
    }

    public static PhotoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        return new PhotoViewHolder(view);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
