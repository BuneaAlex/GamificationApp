package com.example.gamificationapp.domain;

import java.util.Objects;

/**
 * A serializable entity which has the ID of type Integer
 */
public class Quest extends Entity<Integer>{

    private Exercise exercise;
    private String description;
    private String author;
    private int measurementUnits;
    private int tokens;

    /**
     * Constructor
     * @param exercise - exercise supposed to do to complete quest
     * @param description - quest description
     * @param author - the author of the quest
     * @param measurementUnits - measurement for difficulty of the exercise
     * @param tokens - reward after completing quest
     */
    public Quest(Exercise exercise, String description, String author, int measurementUnits, int tokens) {
        this.exercise = exercise;
        this.description = description;
        this.author = author;
        this.measurementUnits = measurementUnits;
        this.tokens = tokens;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public int getMeasurementUnits() {
        return measurementUnits;
    }

    public int getTokens() {
        return tokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quest quest = (Quest) o;
        return measurementUnits == quest.measurementUnits && tokens == quest.tokens && Objects.equals(exercise, quest.exercise) && Objects.equals(description, quest.description) && Objects.equals(author, quest.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exercise, description, author, measurementUnits, tokens);
    }

    @Override
    public String toString() {
        return "Quest{" +
                "exercise=" + exercise +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", measurementUnits=" + measurementUnits +
                ", tokens=" + tokens +
                '}';
    }
}
