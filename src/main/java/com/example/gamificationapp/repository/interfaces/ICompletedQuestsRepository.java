package com.example.gamificationapp.repository.interfaces;

import com.example.gamificationapp.domain.*;
import com.example.gamificationapp.utils.Pair;

import java.util.List;

public interface ICompletedQuestsRepository extends IRepository<Pair<String,Integer>, CompletedQuests>{

    /**
     * Gets all the quests completed by a user
     * @param id_user - id of the user
     * @return - List of all quests completed by user
     */
    List<ProfileDTO> getQuestsCompletedByUser(String id_user);

    /**
     * Gets all the users that have tokens and sorts them desc by number of tokens
     * @return List of users with the most tokens
     */
    List<UserLeaderboardDTO> getLeaderboard();

    /**
     * Gets all the quests that aren't completed by a user
     * @param id_user - id of the user
     * @return - List of all quests not completed by user
     */

    List<Quest> getQuestNotCompletedByUser(String id_user);
}
