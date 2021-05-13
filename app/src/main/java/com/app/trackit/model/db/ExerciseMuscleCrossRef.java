package com.app.trackit.model.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "exercise_muscles",primaryKeys = {"name", "muscleName"})
public class ExerciseMuscleCrossRef {

    @NonNull
    public String name;
    @NonNull
    public String muscleName;

}
