package com.example.gamificationapp.services;

import com.example.gamificationapp.domain.*;
import com.example.gamificationapp.repository.db.*;
import com.example.gamificationapp.repository.interfaces.*;
import com.example.gamificationapp.utils.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WorkoutService {

    private final ICompletedQuestsRepository completedQuestsRepository;
    private final IExerciseRepository exerciseRepository;
    private final IQuestRepository questRepository;
    private final IUserRepository userRepository;

    private final IBadgeCollectionRepository badgeCollectionRepository;

    static WorkoutService instance = new WorkoutService(new CompletedQuestsDBRepo(),new ExerciseDBRepo(),new QuestDBRepo(),new UserDBRepo(),new BadgeCollectionDBRepository());

    public WorkoutService(ICompletedQuestsRepository completedQuestsRepository, IExerciseRepository exerciseRepository, IQuestRepository questRepository, IUserRepository userRepository, IBadgeCollectionRepository badgeCollectionRepository) {
        this.completedQuestsRepository = completedQuestsRepository;
        this.exerciseRepository = exerciseRepository;
        this.questRepository = questRepository;
        this.userRepository = userRepository;
        this.badgeCollectionRepository = badgeCollectionRepository;
    }

    public static WorkoutService getInstance() {
        return instance;
    }

    public List<Exercise> filterExercisesByCategory(Category category)
    {
        return exerciseRepository.exercisesFilterByCategory(category);
    }

    public User findUserForLogin(String username, String password)
    {
        return userRepository.getUserByUsernameAndPassword(username,password);
    }

    public void addQuest(Quest quest)
    {
        questRepository.save(quest);
    }

    public Exercise getExercise(Category category,Type type,Measurement measurement)
    {
       return exerciseRepository.findExerciseByValues(category,type,measurement);
    }

    public List<UserLeaderboardDTO> getLeaderboard()
    {
        return completedQuestsRepository.getLeaderboard();
    }

    public List<Quest> getQuestToCompleteByUser(String id_user)
    {
        return completedQuestsRepository.getQuestNotCompletedByUser(id_user);
    }

    public List<ProfileDTO> getQuestsCompletedByUser(String id_user)
    {
        return completedQuestsRepository.getQuestsCompletedByUser(id_user);
    }

    public List<Badge> getUsersBadges(String id_user)
    {
        return badgeCollectionRepository.findUserBadges(id_user);
    }

    public void completeQuest(User user, Quest quest, LocalDateTime date)
    {
        CompletedQuests cq = new CompletedQuests(date);
        cq.setId(new Pair<>(user.getId(), quest.getId()));
        completedQuestsRepository.save(cq);
    }

    public void addBadge(String id_user,String id_badge)
    {
        BadgeCollection bc = new BadgeCollection();
        bc.setId(new Pair<>(id_user,id_badge));
        badgeCollectionRepository.save(bc);
    }

    public List<CategoryBadge> obtainCategoryBadge(String id_user,Quest quest)
    {
        List<CategoryBadge> badges = badgeCollectionRepository.findUncompletedCategoryBadges(id_user);
        List<CategoryBadge> badgesObtained = new ArrayList<>();
        for(CategoryBadge cb : badges){
            if(cb.getExercise() == quest.getExercise())
            {
                if(quest.getMeasurementUnits() >= cb.getUnits()) {
                    badgesObtained.add(cb);
                    addBadge(id_user, cb.getId());
                }
            }
        }
        return badgesObtained;
    }

    public List<QuestNumberBadge> obtainQuestNumberBadge(String id_user)
    {
        List<QuestNumberBadge> badges = badgeCollectionRepository.findUncompletedQuestNumberBadges(id_user);
        List<QuestNumberBadge> badgesObtained = new ArrayList<>();
        int questsCompleted = getQuestsCompletedByUser(id_user).size();
        for(QuestNumberBadge cb : badges){

            if(questsCompleted >= cb.getCount())
            {
                badgesObtained.add(cb);
                addBadge(id_user, cb.getId());
            }

        }
        return badgesObtained;
    }

    public int getUsersTokens(String id_user)
    {
        List<Quest> quests = getQuestToCompleteByUser(id_user);
        int sum = 0;
        for(Quest q : quests)
        {
            sum+= q.getTokens();
        }
        return sum;
    }



}
