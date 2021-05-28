package com.app.trackit.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "sets", indices = {@Index("setId")})
public class Set {

    @PrimaryKey(autoGenerate = true)
    public int setId;
    private int number;
    public int parentPerformedExerciseId;
    private int weight;
    private int reps;

    /**
     * A class representing a Set of reps
     *
     * @param number is the number of the set.
     *               Used to identify the order of the sets in
     *               a workout
     */
    public Set(int number, int parentPerformedExerciseId) {
        this.number = number;
        this.parentPerformedExerciseId = parentPerformedExerciseId;
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

    @Override
    public boolean equals(Object o) {
        Set set = (Set) o;

        if (weight != set.weight) return false;
        return reps == set.reps;
    }
}
