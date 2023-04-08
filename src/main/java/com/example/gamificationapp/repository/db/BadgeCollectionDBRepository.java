package com.example.gamificationapp.repository.db;

import com.example.gamificationapp.domain.*;
import com.example.gamificationapp.repository.interfaces.IBadgeCollectionRepository;
import com.example.gamificationapp.utils.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BadgeCollectionDBRepository extends AbstractDBRepository<Pair<String,String>, BadgeCollection> implements IBadgeCollectionRepository {

    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        return null;
    }

    @Override
    protected List<BadgeCollection> getEntities(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getFindOneStatement(Connection connection) throws SQLException {
        return null;
    }

    @Override
    protected BadgeCollection getEntity(PreparedStatement statement, Pair<String, String> stringStringPair) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getSaveStatement(Connection connection) throws SQLException {
        String query = "INSERT INTO badge_collection(id_user,id_badge) VALUES(?,?)";

        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected void saveEntity(PreparedStatement statement, BadgeCollection entity) throws SQLException {
        statement.setString(1,entity.getId().first);
        statement.setString(2,entity.getId().second);
    }


    @Override
    public List<Badge> findUserBadges(String id_user) {
        String query = "select id_user,image,b1.id_badge from badge_collection b1 inner join badges b2 on b1.id_badge = b2.id_badge where id_user = ?";
        List<Badge> badges = new ArrayList<>();

        try(Connection connection = jdbcUtils.getConnection() ;
            PreparedStatement statement = connection.prepareStatement(query);)
        {
            statement.setString(1,id_user);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                String id_badge = resultSet.getString("id_badge");
                String img = resultSet.getString("image");

                Badge badge = new Badge(img);
                badge.setId(id_badge);

                badges.add(badge);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return badges;
    }

    @Override
    public List<CategoryBadge> findUncompletedCategoryBadges(String id_user) {
        String query = "select cb.id_badge,cb.id_exercise,cb.units from category_badges cb inner join badges b on b.id_badge = cb.id_badge inner join\n" +
                "(select id_badge from badges\n" +
                "except\n" +
                "select b1.id_badge from badge_collection b1 inner join badges b2 on b1.id_badge = b2.id_badge where id_user = ?) A\n" +
                "on A.id_badge = cb.id_badge";
        List<CategoryBadge> badges = new ArrayList<>();

        try(Connection connection = jdbcUtils.getConnection() ;
            PreparedStatement statement = connection.prepareStatement(query);)
        {
            statement.setString(1,id_user);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                String id_badge = resultSet.getString("id_badge");
                int id_exercise = resultSet.getInt("id_exercise");
                int units = resultSet.getInt("units");

                ExerciseDBRepo repo = new ExerciseDBRepo();
                Exercise exercise = repo.findOne(id_exercise);

                CategoryBadge badge = new CategoryBadge("",exercise,units);
                badge.setId(id_badge);

                badges.add(badge);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return badges;
    }

    @Override
    public List<QuestNumberBadge> findUncompletedQuestNumberBadges(String id_user) {
        String query = "select cb.id_badge,cb.count from quest_number_badges cb inner join badges b on b.id_badge = cb.id_badge inner join\n" +
                "(select id_badge from badges\n" +
                "except\n" +
                "select b1.id_badge from badge_collection b1 inner join badges b2 on b1.id_badge = b2.id_badge where id_user = ?) A\n" +
                "on A.id_badge = cb.id_badge";
        List<QuestNumberBadge> badges = new ArrayList<>();

        try(Connection connection = jdbcUtils.getConnection() ;
            PreparedStatement statement = connection.prepareStatement(query);)
        {
            statement.setString(1,id_user);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                String id_badge = resultSet.getString("id_badge");
                int count = resultSet.getInt("count");

                QuestNumberBadge badge = new QuestNumberBadge("",count);
                badge.setId(id_badge);

                badges.add(badge);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return badges;
    }


}
