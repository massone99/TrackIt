package com.app.trackit.model.db;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.app.trackit.model.Muscle;
import com.app.trackit.model.Exercise;

import java.util.List;

public class MuscleWithExercises {

    @Embedded
    public Muscle muscle;
    @Relation(
            parentColumn = "muscleName",
            entityColumn = "name",
            associateBy = @Junction(MuscleExerciseCrossRef.class)
    )
    public List<Exercise> exercises;

}
