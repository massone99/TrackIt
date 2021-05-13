package com.app.trackit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workouts")
public class Workout {

    @PrimaryKey @ColumnInfo(name = "workout_id")
    public int id;

    @ColumnInfo(name ="date")
    String date;

}
