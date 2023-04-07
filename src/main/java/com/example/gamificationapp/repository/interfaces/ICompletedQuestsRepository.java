package com.example.gamificationapp.repository.interfaces;

import com.example.gamificationapp.domain.*;
import com.example.gamificationapp.utils.Pair;

import java.util.List;

public interface ICompletedQuestsRepository extends IRepository<Pair<String,Integer>, CompletedQuests>{

    List<ProfileDTO> getQuestsCompletedByUser(String id_user);
    List<UserLeaderboardDTO> getLeaderboard();

    List<Quest> getQuestNotCompletedByUser(String id_user);
}
