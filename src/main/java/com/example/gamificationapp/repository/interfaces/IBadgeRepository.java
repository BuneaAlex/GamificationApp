package com.example.gamificationapp.repository.interfaces;

import com.example.gamificationapp.domain.Badge;
import com.example.gamificationapp.domain.CategoryBadge;
import com.example.gamificationapp.domain.QuestNumberBadge;

import java.util.List;

public interface IBadgeRepository {

    CategoryBadge findOneCategoryBadge(String id);
    QuestNumberBadge findOneQuestNumberBadge(String id);

    List<Badge> getUsersBadges(String id_user);
}
