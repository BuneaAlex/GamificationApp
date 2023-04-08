package com.example.gamificationapp.repository.db;

import com.example.gamificationapp.domain.*;
import com.example.gamificationapp.repository.interfaces.IExerciseRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDBRepo extends AbstractDBRepository<Integer, Exercise> implements IExerciseRepository {
    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM exercises";

        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected List<Exercise> getEntities(ResultSet resultSet) throws SQLException {
        List<Exercise> exercises = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id_exercise");
            Category ex_category = Category.valueOf(resultSet.getString("ex_category"));
            Type ex_type = Type.valueOf(resultSet.getString("ex_type"));
            Measurement measurement = Measurement.valueOf(resultSet.getString("measurement"));

            Exercise exercise = new Exercise(ex_category,ex_type,measurement);
            exercise.setId(id);

            exercises.add(exercise);
        }

        return exercises;
    }

    @Override
    protected PreparedStatement getFindOneStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM exercises WHERE id_exercise=?";

        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected Exercise getEntity(PreparedStatement statement, Integer id) throws SQLException {
        Exercise exercise = null;
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            Category ex_category = Category.valueOf(resultSet.getString("ex_category"));
            Type ex_type = Type.valueOf(resultSet.getString("ex_type"));
            Measurement measurement = Measurement.valueOf(resultSet.getString("measurement"));

            exercise = new Exercise(ex_category,ex_type,measurement);
            exercise.setId(id);
        }
        resultSet.close();

        return exercise;
    }

    @Override
    protected PreparedStatement getSaveStatement(Connection connection) throws SQLException {
        return null;
    }

    @Override
    protected void saveEntity(PreparedStatement statement, Exercise entity) throws SQLException {

    }

    @Override
    public List<Exercise> exercisesFilterByCategory(Category category) {
        String query = "select * from exercises where ex_category = ?";
        List<Exercise> exercises = new ArrayList<>();

        try(Connection connection = jdbcUtils.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,category.toString());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                int id = resultSet.getInt("id_exercise");
                Category ex_category = Category.valueOf(resultSet.getString("ex_category"));
                Type ex_type = Type.valueOf(resultSet.getString("ex_type"));
                Measurement measurement = Measurement.valueOf(resultSet.getString("measurement"));

                Exercise exercise = new Exercise(ex_category,ex_type,measurement);
                exercise.setId(id);

                exercises.add(exercise);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return exercises;
    }

    @Override
    public Exercise findExerciseByValues(Category category, Type type, Measurement measurement) {
        String query = "select * from exercises where ex_category = ? and ex_type = ? and measurement = ?";
        Exercise exercise = null;
        try(Connection connection = jdbcUtils.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,category.toString());
            statement.setString(2,type.toString());
            statement.setString(3,measurement.toString());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                int id = resultSet.getInt("id_exercise");
                Category ex_category = Category.valueOf(resultSet.getString("ex_category"));
                Type ex_type = Type.valueOf(resultSet.getString("ex_type"));
                Measurement ex_measurement = Measurement.valueOf(resultSet.getString("measurement"));

                exercise = new Exercise(ex_category,ex_type,ex_measurement);
                exercise.setId(id);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return exercise;
    }
}
