package com.app.trackit.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercises")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int exerciseId;
    @NonNull
    private final String name;
    private final String type;
    private final String movement;
    private final String muscle;
    private boolean favourite;

    public Exercise(@NonNull String name,
                    String type,
                    String movement, String muscle){
        this.name = name;
        this.movement = movement;
        this.type = type;
        this.muscle = muscle;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMovement() {
        return movement;
    }

    public String getMuscle() {
        return muscle;
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

        if (exerciseId != exercise.exerciseId) return false;
        if (favourite != exercise.favourite) return false;
        if (!name.equals(exercise.name)) return false;
        if (type != null ? !type.equals(exercise.type) : exercise.type != null) return false;
        if (movement != null ? !movement.equals(exercise.movement) : exercise.movement != null)
            return false;
        return muscle != null ? muscle.equals(exercise.muscle) : exercise.muscle == null;
    }
}
