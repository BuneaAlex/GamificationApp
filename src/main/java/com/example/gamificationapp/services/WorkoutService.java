package com.example.gamificationapp.services;

import com.example.gamificationapp.domain.*;
import com.example.gamificationapp.repository.db.*;
import com.example.gamificationapp.repository.interfaces.*;
import com.example.gamificationapp.utils.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A service containing all the repositories related to the main goal of the app
 */
public class WorkoutService {

    private final ICompletedQuestsRepository completedQuestsRepository;
    private final IExerciseRepository exerciseRepository;
    private final IQuestRepository questRepository;
    private final IUserRepository userRepository;

    private final IBadgeCollectionRepository badgeCollectionRepository;
    static WorkoutService instance = new WorkoutService(new CompletedQuestsDBRepo(),new ExerciseDBRepo(),new QuestDBRepo(),new UserDBRepo(),new BadgeCollectionDBRepository());

    /**
     * Constructor
     * @param completedQuestsRepository repository that handles the user and quests relation
     * @param exerciseRepository repository for exercises
     * @param questRepository repository for quests
     * @param userRepository repository for users
     * @param badgeCollectionRepository repository that handles the users and badges relation
     */
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

    /**
     * Filter exercises by category
     * @param category the category that the exercises are filtered by
     * @return list of exercises that are part of the category selected
     */
    public List<Exercise> filterExercisesByCategory(Category category)
    {
        return exerciseRepository.exercisesFilterByCategory(category);
    }

    /**
     * Get user by its credentials
     * @param username user's username
     * @param password user's password
     * @return the user if username and password are correct, else null
     */
    public User findUserForLogin(String username, String password)
    {
        return userRepository.getUserByUsernameAndPassword(username,password);
    }

    /**
     * Add quest
     * @param quest the quest added
     */
    public void addQuest(Quest quest)
    {
        questRepository.save(quest);
    }

    /**
     * Get exercise based on category, type, measurement
     * @param category exercise category
     * @param type exercise type
     * @param measurement exercise measurement
     * @return exercise if existent, else null
     */
    public Exercise getExercise(Category category,Type type,Measurement measurement)
    {
       return exerciseRepository.findExerciseByValues(category,type,measurement);
    }

    /**
     * Get the users with the most tokens leaderboard
     * @return list of users
     */
    public List<UserLeaderboardDTO> getLeaderboard()
    {
        return completedQuestsRepository.getLeaderboard();
    }

    /**
     * Get quests that the user hasn't completed yet
     * @param id_user user's id
     * @return list of quests to complete by user
     */
    public List<Quest> getQuestToCompleteByUser(String id_user)
    {
        return completedQuestsRepository.getQuestNotCompletedByUser(id_user);
    }

    /**
     * Get quests that the user completed
     * @param id_user user's id
     * @return list of quests completed by user
     */
    public List<ProfileDTO> getQuestsCompletedByUser(String id_user)
    {
        return completedQuestsRepository.getQuestsCompletedByUser(id_user);
    }

    /**
     * Get badges that the user has
     * @param id_user user's id
     * @return list of badges completed by user
     */
    public List<Badge> getUsersBadges(String id_user)
    {
        return badgeCollectionRepository.findUserBadges(id_user);
    }

    /**
     * Complete quest by user
     * @param user user that completed the quest
     * @param quest the quest that has been completed
     * @param date the date when the quest was completed
     */
    public void completeQuest(User user, Quest quest, LocalDateTime date)
    {
        CompletedQuests cq = new CompletedQuests(date);
        cq.setId(new Pair<>(user.getId(), quest.getId()));
        completedQuestsRepository.save(cq);
    }

    /**
     * Add badge
     * @param id_user user's id
     * @param id_badge badge's id
     */
    public void addBadge(String id_user,String id_badge)
    {
        BadgeCollection bc = new BadgeCollection();
        bc.setId(new Pair<>(id_user,id_badge));
        badgeCollectionRepository.save(bc);
    }

    /**
     * Get category badges that the user doesn't have and return the list of badges that he/she obtained by completing the quest
     * @param id_user user's id
     * @param quest quest that has been completed
     * @return list of category badges that the user obtained by completing the quest
     */
    public List<CategoryBadge> obtainCategoryBadge(String id_user,Quest quest)
    {
        List<CategoryBadge> badges = badgeCollectionRepository.findUncompletedCategoryBadges(id_user);
        List<CategoryBadge> badgesObtained = new ArrayList<>();
        for(CategoryBadge cb : badges){
            if(Objects.equals(cb.getExercise().getId(), quest.getExercise().getId()))
            {
                if(quest.getMeasurementUnits() >= cb.getUnits()) {
                    badgesObtained.add(cb);
                    addBadge(id_user, cb.getId());
                }
            }
        }
        return badgesObtained;
    }

    /**
     * Get quest number badges that the user doesn't have and return the list of badges that he/she obtained by completing the quest
     * @param id_user user's id
     * @return list of quest number badges that the user obtained by completing the quest
     */
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

    /**
     * Get tokens earned by user
     * @param id_user user's id
     * @return the tokens that the user has
     */
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
