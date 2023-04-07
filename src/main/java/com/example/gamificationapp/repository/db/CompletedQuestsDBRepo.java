package com.example.gamificationapp.repository.db;

import com.example.gamificationapp.domain.*;
import com.example.gamificationapp.repository.interfaces.ICompletedQuestsRepository;
import com.example.gamificationapp.utils.Pair;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompletedQuestsDBRepo extends AbstractDBRepository<Pair<String,Integer>, CompletedQuests> implements ICompletedQuestsRepository {
    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        return null;
    }

    @Override
    protected List<CompletedQuests> getEntities(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getFindOneStatement(Connection connection) throws SQLException {
        return null;
    }

    @Override
    protected CompletedQuests getEntity(PreparedStatement statement, Pair<String, Integer> integerIntegerPair) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getSaveStatement(Connection connection) throws SQLException {
        String query = "INSERT INTO completed_quests(id_user,id_quest,date_of_completion) VALUES(?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected void saveEntity(PreparedStatement statement, CompletedQuests entity) throws SQLException {
        statement.setString(1,entity.getId().first);
        statement.setInt(2,entity.getId().second);
        statement.setTimestamp(3, Timestamp.valueOf(entity.getDateCompleted()));
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection) throws SQLException {
        return null;
    }

    @Override
    protected void updateEntity(PreparedStatement statement, CompletedQuests entity) throws SQLException {

    }

    @Override
    public List<ProfileDTO> getQuestsCompletedByUser(String id_user) {
        String query = "select * from quests inner join completed_quests cq on quests.id_quest = cq.id_quest where id_user=?";
        List<ProfileDTO> questList = new ArrayList<>();
        try(Connection connection = jdbcUtils.getConnection() ;
            PreparedStatement statement = connection.prepareStatement(query);)
        {
            statement.setString(1,id_user);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                int id_quest = resultSet.getInt(1);
                int id_exercise = resultSet.getInt("id_exercise");
                String description = resultSet.getString("description");
                String author = resultSet.getString("author");
                int units = resultSet.getInt("measurement_units");
                int tokens = resultSet.getInt("tokens");
                LocalDateTime date = resultSet.getTimestamp("date_of_completion").toLocalDateTime();

                ExerciseDBRepo repo = new ExerciseDBRepo();
                Exercise exercise = repo.findOne(id_exercise);

                Quest quest = new Quest(exercise,description,author,units,tokens);
                quest.setId(id_quest);

                ProfileDTO profileDTO = new ProfileDTO(quest,date);

                questList.add(profileDTO);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return questList;
    }

    @Override
    public List<UserLeaderboardDTO> getLeaderboard() {
        String query = "select ROW_NUMBER() OVER (ORDER BY sum(tokens) desc) as rank,id_user,sum(tokens) as tokens from completed_quests inner join quests q on q.id_quest = completed_quests.id_quest group by id_user order by sum(tokens) desc";
        List<UserLeaderboardDTO> leaderboardList = new ArrayList<>();

        try(Connection connection = jdbcUtils.getConnection() ;
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery())
        {
            while(resultSet.next())
            {
                int rank = resultSet.getInt("rank");
                String id_user = resultSet.getString("id_user");
                int tokens = resultSet.getInt("tokens");

                UserDBRepo repo = new UserDBRepo();
                User user = repo.findOne(id_user);
                UserLeaderboardDTO leaderboardDTO = new UserLeaderboardDTO(user,rank,tokens);
                leaderboardList.add(leaderboardDTO);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return leaderboardList;
    }

    @Override
    public List<Quest> getQuestNotCompletedByUser(String id_user) {
        String query = "select quests.id_quest,id_exercise,description,author,measurement_units,tokens from quests inner join\n" +
                "(select id_quest from quests where not author = ? \n" +
                "except\n" +
                "select cq.id_quest from quests inner join completed_quests cq on quests.id_quest = cq.id_quest where id_user=?) A\n" +
                "on quests.id_quest = A.id_quest\n";
        List<Quest> questList = new ArrayList<>();
        try(Connection connection = jdbcUtils.getConnection() ;
            PreparedStatement statement = connection.prepareStatement(query);)
        {
            statement.setString(1,id_user);
            statement.setString(2,id_user);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                int id_quest = resultSet.getInt("id_quest");
                int id_exercise = resultSet.getInt("id_exercise");
                String description = resultSet.getString("description");
                String author = resultSet.getString("author");
                int units = resultSet.getInt("measurement_units");
                int tokens = resultSet.getInt("tokens");

                ExerciseDBRepo repo = new ExerciseDBRepo();
                Exercise exercise = repo.findOne(id_exercise);

                Quest quest = new Quest(exercise,description,author,units,tokens);
                quest.setId(id_quest);

                questList.add(quest);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return questList;
    }
}
