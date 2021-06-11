package com.app.trackit.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercises")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int exerciseId;
    @NonNull
    private String name;
    private String type;
    private String movement;
    private String muscle;
    private boolean favourite;

    public Exercise(@NonNull String name,
                    String type,
                    String movement,
                    String muscle){
        this.name = name;
        this.movement = movement;
        this.type = type;
        this.muscle = muscle;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public Exercise setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Exercise setType(String type) {
        this.type = type;
        return this;
    }

    public String getMovement() {
        return movement;
    }

    public Exercise setMovement(String movement) {
        this.movement = movement;
        return this;
    }

    public String getMuscle() {
        return muscle;
    }

    public Exercise setMuscle(String muscle) {
        this.muscle = muscle;
        return this;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (favourite != exercise.favourite) return false;
        if (!name.equals(exercise.name)) return false;
        if (type != null ? !type.equals(exercise.type) : exercise.type != null) return false;
        if (movement != null ? !movement.equals(exercise.movement) : exercise.movement != null)
            return false;
        return muscle != null ? muscle.equals(exercise.muscle) : exercise.muscle == null;
    }
}
