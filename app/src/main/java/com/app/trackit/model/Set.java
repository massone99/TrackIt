package com.app.trackit.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sets")
public class Set {

    @PrimaryKey(autoGenerate = true)
    public int setId;
    private int number;
    private int parentPerformedExerciseId;
    private int relativeExerciseId;
    private int weight;
    private int reps;

    /**
     * A class representing a Set of reps
     *
     * @param number is the number of the set.
     *               Used to identify the order of the sets in
     *               a workout
     */
    public Set(int number, int parentPerformedExerciseId, int relativeExerciseId) {
        this.number = number;
        this.parentPerformedExerciseId = parentPerformedExerciseId;
        this.relativeExerciseId = relativeExerciseId;
    }

    public void setParentPerformedExerciseId(int parentPerformedExerciseId) {
        this.parentPerformedExerciseId = parentPerformedExerciseId;
    }

    public int getRelativeExerciseId() {
        return relativeExerciseId;
    }

    public void setRelativeExerciseId(int relativeExerciseId) {
        this.relativeExerciseId = relativeExerciseId;
    }

    public int getId() {
        return setId;
    }

    public void setId(int id) {
       this.setId = id;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getReps() {
        return reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getParentPerformedExerciseId() {
        return parentPerformedExerciseId;
    }

    /*@Override
    public boolean equals(Object o) {
        Set set = (Set) o;

        if (weight != set.weight) return false;
        return reps == set.reps;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Set set = (Set) o;

        if (setId != set.setId) return false;
        if (number != set.number) return false;
        if (parentPerformedExerciseId != set.parentPerformedExerciseId) return false;
        if (relativeExerciseId != set.relativeExerciseId) return false;
        if (weight != set.weight) return false;
        return reps == set.reps;
    }
}