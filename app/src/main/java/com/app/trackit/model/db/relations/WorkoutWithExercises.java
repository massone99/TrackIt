package com.app.trackit.model.db.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.app.trackit.model.Workout;
import com.app.trackit.model.Exercise;

import java.util.List;

public class WorkoutWithExercises {

    @Embedded public Workout workout;
    @Relation(
        parentColumn = "id",
        entityColumn = "name",
        associateBy = @Junction(WorkoutExerciseCrossRef.class)
    )
    public List<Exercise> exercises;

}
