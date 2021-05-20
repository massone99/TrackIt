package com.app.trackit.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sets")
public class Set {

    @PrimaryKey
    @NonNull
    private int number;
    private int weight;
    private int reps;

    /**
     * A class representing a Set of reps
     *
     * @param number is the number of the set.
     *               Used to identify the order of the sets in
     *               a workout
     */
    public Set(int number) {
        this.number = number;
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


}
