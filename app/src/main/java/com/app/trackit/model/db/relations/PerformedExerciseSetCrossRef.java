package com.app.trackit.model.db.relations;

import androidx.room.Entity;
import androidx.room.Fts4;

@Entity(primaryKeys = {"performedExerciseId", "setId"})
public class PerformedExerciseSetCrossRef{
    public int performedExerciseId;
    public int setId;
}
