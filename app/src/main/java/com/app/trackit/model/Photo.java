package com.app.trackit.model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "photos")
public class Photo {

    @PrimaryKey
    @ColumnInfo(name = "uri")
    @NonNull
    private Uri uri;
    @NonNull
    @ColumnInfo(name = "timestamp")
    private Date timeStamp;

    public Photo(Date timeStamp, Uri uri) {
        this.timeStamp = timeStamp;
        this.uri = uri;
    }

    @NonNull
    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(@NonNull Date timeStamp) {
        this.timeStamp = timeStamp;
    }


    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }


}
