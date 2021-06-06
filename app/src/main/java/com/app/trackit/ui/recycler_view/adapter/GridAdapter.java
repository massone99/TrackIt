package com.app.trackit.ui.recycler_view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.app.trackit.R;
import com.app.trackit.model.Photo;
import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private LiveData<List<Photo>> photoList;
    private Activity activity;

    public GridAdapter(Activity activity, LiveData<List<Photo>> photoList) {
        this.photoList = photoList;
        this.activity = activity;
        this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photoList.getValue().size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.getValue(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridHolder gridHolder = new GridHolder();
        View gridView;

        LayoutInflater inflater;
        ViewGroup root;


        gridView = layoutInflater.inflate(R.layout.grid_item, null);
        gridHolder.textView = (TextView) gridView.findViewById(R.id.grid_image_title);
        gridHolder.imageView = (ImageView) gridView.findViewById(R.id.grid_image);

        gridHolder.textView.setText(new SimpleDateFormat("dd/MM/yyyy").format(photoList.get(position).getTimeStamp()));

        Glide.with(activity).load(photoList.get(position).getUri()).into(gridHolder.imageView);
        gridView.setOnClickListener(v -> {
            Toast.makeText(activity, "You Clicked ", Toast.LENGTH_LONG).show();
        });

        return gridView;
    }

    public class GridHolder {
        TextView textView;
        ImageView imageView;
    }
}
