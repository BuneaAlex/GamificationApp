package com.example.gamificationapp.domain;

import java.util.Objects;

public class CategoryBadge extends Badge{

    private Exercise exercise;
    private int units;

    public CategoryBadge(String image, Exercise exercise, int units) {
        super(image);
        this.exercise = exercise;
        this.units = units;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public int getUnits() {
        return units;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryBadge that = (CategoryBadge) o;
        return units == that.units && Objects.equals(exercise, that.exercise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exercise, units);
    }

    @Override
    public String toString() {
        return "CategoryBadge{" +
                "exercise=" + exercise +
                ", units=" + units +
                '}';
    }
}
