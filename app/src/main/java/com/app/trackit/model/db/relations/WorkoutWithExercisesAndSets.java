package com.app.trackit.model.db.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.app.trackit.model.PerformedExercise;
import com.app.trackit.model.Workout;

import java.util.List;

public class WorkoutWithExercisesAndSets {


    @Embedded
    public Workout workout;
    @Relation(
            entity = PerformedExercise.class,
            parentColumn = "id",
            entityColumn = "workoutId"
    )
    public List<PerformedExerciseWithSets> performedExercises;

}
