package com.example.gamificationapp.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class ProfileDTO {

    private Quest quest;
    private LocalDateTime dateOfCompletion;

    public ProfileDTO(Quest quest, LocalDateTime dateOfCompletion) {
        this.quest = quest;
        this.dateOfCompletion = dateOfCompletion;
    }

    public Quest getQuest() {
        return quest;
    }

    public Category getCategory(){
        return quest.getExercise().getCategory();
    }

    public LocalDateTime getDateOfCompletion() {
        return dateOfCompletion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileDTO that = (ProfileDTO) o;
        return Objects.equals(quest, that.quest) && Objects.equals(dateOfCompletion, that.dateOfCompletion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quest, dateOfCompletion);
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "quest=" + quest +
                ", dateOfCompletion=" + dateOfCompletion +
                '}';
    }
}
