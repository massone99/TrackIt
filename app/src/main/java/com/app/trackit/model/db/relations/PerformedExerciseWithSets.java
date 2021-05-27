package com.app.trackit.model.db.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Set;

import java.util.List;

public class PerformedExerciseWithSets {

    @Embedded
    public PerformedExercise exercise;
    @Relation(
            parentColumn = "performedExerciseId",
            entityColumn = "setId",
            associateBy = @Junction(PerformedExerciseSetCrossRef.class)
    )
    public List<Set> sets;

}
