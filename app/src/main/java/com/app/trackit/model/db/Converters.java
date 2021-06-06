package com.app.trackit.model.db;

import android.net.Uri;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromUriToString(Uri uri) {
        return uri == null ? null : uri.toString();
    }

    @TypeConverter
    public static Uri fromStringToUri(String string) {
        return string == null ? null : Uri.parse(string);
    }


}

