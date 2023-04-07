package com.example.gamificationapp.repository.db;

import com.example.gamificationapp.domain.Exercise;
import com.example.gamificationapp.domain.Quest;
import com.example.gamificationapp.domain.User;
import com.example.gamificationapp.repository.interfaces.IQuestRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestDBRepo extends AbstractDBRepository<Integer, Quest> implements IQuestRepository {
    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM quests";

        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected List<Quest> getEntities(ResultSet resultSet) throws SQLException {
        List<Quest> quests = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id_quest");
            int id_exercise = resultSet.getInt("id_exercise");
            String description = resultSet.getString("description");
            String author = resultSet.getString("author");
            int units = resultSet.getInt("measurement_units");
            int tokens = resultSet.getInt("tokens");

            ExerciseDBRepo repo = new ExerciseDBRepo();
            Exercise exercise = repo.findOne(id_exercise);

            Quest quest = new Quest(exercise,description,author,units,tokens);
            quest.setId(id);

            quests.add(quest);
        }

        return quests;
    }

    @Override
    protected PreparedStatement getFindOneStatement(Connection connection) throws SQLException {
        return null;
    }

    @Override
    protected Quest getEntity(PreparedStatement statement, Integer integer) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getSaveStatement(Connection connection) throws SQLException {
        String query = "INSERT INTO quests(id_exercise,description,author,measurement_units,tokens) VALUES(?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected void saveEntity(PreparedStatement statement, Quest entity) throws SQLException {
        statement.setInt(1,entity.getExercise().getId());
        statement.setString(2,entity.getDescription());
        statement.setString(3,entity.getAuthor());
        statement.setInt(4,entity.getMeasurementUnits());
        statement.setInt(5,entity.getTokens());
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection) throws SQLException {
        return null;
    }

    @Override
    protected void updateEntity(PreparedStatement statement, Quest entity) throws SQLException {

    }
}
