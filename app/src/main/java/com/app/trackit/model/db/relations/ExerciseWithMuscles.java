package com.app.trackit.model.db.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.app.trackit.model.Muscle;
import com.app.trackit.model.Exercise;

import java.util.List;

public class ExerciseWithMuscles {

    @Embedded
    public Exercise exercise;
    @Relation(
            parentColumn = "name",
            entityColumn = "muscleName",
            associateBy = @Junction(ExerciseMuscleCrossRef.class)
    )
    public List<Muscle> muscles;

}
