package com.example.gamificationapp.repository.interfaces;

import com.example.gamificationapp.domain.Badge;
import com.example.gamificationapp.domain.BadgeCollection;
import com.example.gamificationapp.domain.CategoryBadge;
import com.example.gamificationapp.domain.QuestNumberBadge;
import com.example.gamificationapp.utils.Pair;

import java.util.List;

public interface IBadgeCollectionRepository extends IRepository<Pair<String,String>, BadgeCollection>{

    List<Badge> findUserBadges(String id_user);
    List<CategoryBadge> findUncompletedCategoryBadges(String id_user);
    List<QuestNumberBadge> findUncompletedQuestNumberBadges(String id_user);
}
