package com.app.trackit.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.app.trackit.model.Muscle;

import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "exercises")
public class Exercise {

    @PrimaryKey
    @NonNull
    private String name;
    private String type;
    private String movement;
    @Ignore
    // The muscles worked by this exercise
    private List<Muscle> muscles = new LinkedList<>();

    public static enum TYPE{
        RIPETIZIONI,
        TEMPO
    }

    public Exercise(@NonNull String name,
                    String type,
                    String movement){
        this.name = name;
        this.movement = movement;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMovement() {
        return movement;
    }

    /**
     * Gets all muscles used by this exercise
     * @return the List of muscles
     */
    public List<Muscle> getMuscles() {
        return muscles;
    }

    /**
     * Sets the muscles used by this exercise
     * @param muscles are the muscles involved in the exercise
     */
    public void setMuscles(List<Muscle> muscles) {
        this.muscles = muscles;
    }

}
