package com.app.trackit.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "muscles")
public class Muscle {

    @PrimaryKey
    @ColumnInfo(name = "muscleName")
    @NonNull
    public String muscleName;
    @Ignore
    private List<Exercise> exercises = new LinkedList<>();

    // FIXME:
    // This method should be deleted since Muscles shouldn't be created
    // but should prepopulate the Database
    public Muscle(String muscleName) {
        this.muscleName = muscleName;
    }

    /**
     * @return the name of this Muscle
     */
    @NonNull
    public String getMuscleName() {
        return muscleName;
    }

    /**
     * Gets all exercises that use this muscle
     * @return the List of exercises
     */
    public List<Exercise> getExercises() { return exercises; }

    /**
     * Add exercise to the list of exercises that work this Muscle
     * @param exercise is the exercise to add
     */
    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
    }

}
