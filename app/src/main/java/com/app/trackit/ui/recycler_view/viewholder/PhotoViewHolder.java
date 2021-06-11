package com.app.trackit.ui.recycler_view.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.os.FileUriExposedException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trackit.R;
import com.app.trackit.model.Photo;
import com.app.trackit.model.viewmodel.PhotosViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "PhotoViewHolder";

    private PhotosViewModel photosViewModel;

    private Photo photo;
    private final ImageView imageView;
    private final TextView textView;
    private final ImageButton deleteButton;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);

        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
        this.textView = (TextView) itemView.findViewById(R.id.grid_image_title);
        this.imageView = (ImageView) itemView.findViewById(R.id.grid_image);
        this.deleteButton = (ImageButton) itemView.findViewById(R.id.delete_photo);
        MaterialCardView card = (MaterialCardView) itemView.findViewById(R.id.grid_card);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            card.setOnClickListener(v -> {
                try {
                    Log.d(TAG, photo.getUri().toString());
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File("/storage/emulated/0/Pictures/"
                            + new SimpleDateFormat("ddMMyyyy").format(photo.getTimeStamp())
                            + ".jpg")), "image/*");
                    Intent chooser = Intent.createChooser(intent, "Scegli un app per aprire la foto");
                    activity.startActivity(chooser);
                } catch (FileUriExposedException ignored) { }
            });
        }
    }

    public void bind(String imageTitle, Photo photo) {
        textView.setText(imageTitle);
        this.photo = photo;
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

    public ImageButton getDeleteButton() {
        return deleteButton;
    }
}
