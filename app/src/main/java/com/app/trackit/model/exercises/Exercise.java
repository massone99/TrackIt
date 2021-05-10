package com.app.trackit.model.exercises;

import java.util.List;

public abstract class Exercise {

    private String name;
    private List<String> muscles;

    /**
     * A class modelling an Exercise
     * @param name
     */
    public Exercise(String name, List<String> muscles) {
        this.name = name;
        this.muscles = muscles;
    }

}
