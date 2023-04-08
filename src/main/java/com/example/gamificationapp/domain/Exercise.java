package com.example.gamificationapp.domain;

import java.util.Objects;

/**
 * A serializable entity which has the ID of type Integer
 */
public class Exercise extends Entity<Integer>{

    private Category category;
    private Type type;
    private Measurement measurement;

    /**
     * Constructor
     * @param category - category of the exercise
     * @param type - type of exercise
     * @param measurement - how is the performance measured
     */

    public Exercise(Category category, Type type, Measurement measurement) {
        this.category = category;
        this.type = type;
        this.measurement = measurement;
    }

    public Category getCategory() {
        return category;
    }

    public Type getType() {
        return type;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return category == exercise.category && type == exercise.type && measurement == exercise.measurement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, type, measurement);
    }

    @Override
    public String toString() {
        return "category: " + category +
                ", type: " + type +
                ", measurement: " + measurement;
    }
}
