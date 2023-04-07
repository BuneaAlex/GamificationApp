package com.example.gamificationapp.repository.db;

import com.example.gamificationapp.domain.Badge;
import com.example.gamificationapp.domain.CategoryBadge;
import com.example.gamificationapp.domain.Exercise;
import com.example.gamificationapp.domain.QuestNumberBadge;
import com.example.gamificationapp.repository.interfaces.IBadgeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class BadgeDBRepo implements IBadgeRepository {

    protected final JDBCUtils jdbcUtils = new JDBCUtils();

    @Override
    public CategoryBadge findOneCategoryBadge(String id) {
        String query = "select b.id_badge,id_exercise,units,image from category_badges inner join badges b on category_badges.id_badge = b.id_badge where id_badge=?";
        CategoryBadge badge = null;

        try(Connection connection = jdbcUtils.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                int id_exercise = resultSet.getInt("id_exercise");
                int units = resultSet.getInt("units");
                String image = resultSet.getString("image");
                ExerciseDBRepo exerciseDBRepo = new ExerciseDBRepo();
                Exercise exercise = exerciseDBRepo.findOne(id_exercise);

                badge = new CategoryBadge(image,exercise,units);
                badge.setId(id);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return badge;
    }

    @Override
    public QuestNumberBadge findOneQuestNumberBadge(String id) {
        String query = "select b.id_badge,image,count from quest_number_badges q inner join badges b on q.id_badge = b.id_badge where id_badge=?";
        QuestNumberBadge badge = null;

        try(Connection connection = jdbcUtils.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                String image = resultSet.getString("image");
                int count = resultSet.getInt("count");

                badge = new QuestNumberBadge(image,count);
                badge.setId(id);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return badge;
    }

    @Override
    public List<Badge> getUsersBadges(String id_user) {
        return null;
    }
}
