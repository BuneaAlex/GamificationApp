package com.example.gamificationapp.domain;

import com.example.gamificationapp.utils.Pair;

import java.time.LocalDateTime;

/**
    ID = [id_user,id_quest]
 */
public class CompletedQuests extends Entity<Pair<String,Integer>>{

    private LocalDateTime dateCompleted;

    public CompletedQuests(LocalDateTime dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public LocalDateTime getDateCompleted() {
        return dateCompleted;
    }

    @Override
    public String toString() {
        return "CompletedQuests{" +
                "id user=" + getId().first +
                "id quest=" + getId().second +
                "dateCompleted=" + dateCompleted +
                '}';
    }
}
