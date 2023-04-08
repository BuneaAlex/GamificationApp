package com.example.gamificationapp.repository.interfaces;

import com.example.gamificationapp.domain.Badge;
import com.example.gamificationapp.domain.BadgeCollection;
import com.example.gamificationapp.domain.CategoryBadge;
import com.example.gamificationapp.domain.QuestNumberBadge;
import com.example.gamificationapp.utils.Pair;

import java.util.List;

public interface IBadgeCollectionRepository extends IRepository<Pair<String,String>, BadgeCollection>{

    /**
     * Finds all of user's badges
     * @param id_user - user id
     * @return List of badges possessed by user
     */
    List<Badge> findUserBadges(String id_user);

    /**
     * Finds all category badges that the user doesn't have
     * @param id_user - user id
     * @return List of category badges
     */
    List<CategoryBadge> findUncompletedCategoryBadges(String id_user);

    /**
     * Finds all quest number badges that the user doesn't have
     * @param id_user - user id
     * @return List of quest number badges
     */
    List<QuestNumberBadge> findUncompletedQuestNumberBadges(String id_user);
}
