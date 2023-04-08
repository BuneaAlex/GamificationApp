package com.example.gamificationapp.domain;

import com.example.gamificationapp.utils.Pair;

import java.time.LocalDateTime;

/**
 * A serializable entity which has the ID of type Pair<String,String>
 * where the first represents the user id and the second represents the quest's id
 */

public class CompletedQuests extends Entity<Pair<String,Integer>>{

    private LocalDateTime dateCompleted;

    /**
     * Constructor
     * @param dateCompleted - the date when the quest is completed
     */
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
