package com.app.trackit.model.db.relations;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "muscle_exercises",primaryKeys = {"muscleName", "name"})
public class MuscleExerciseCrossRef {

    @NonNull
    public String muscleName;
    @NonNull
    public String name;
}