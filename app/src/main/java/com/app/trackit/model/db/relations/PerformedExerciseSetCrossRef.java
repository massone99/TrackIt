package com.app.trackit.model.db.relations;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"name", "number"})
public class PerformedExerciseSetCrossRef {

    @NonNull
    public String name;
    @NonNull
    public int number;

}
