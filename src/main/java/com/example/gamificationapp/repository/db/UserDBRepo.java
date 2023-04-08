package com.example.gamificationapp.repository.db;


import com.example.gamificationapp.domain.User;
import com.example.gamificationapp.repository.interfaces.IUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDBRepo extends AbstractDBRepository<String, User> implements IUserRepository {


    @Override
    protected PreparedStatement getFindOneStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM users WHERE id_user=?";

        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected User getEntity(PreparedStatement statement,String id) throws SQLException {

        User user = null;
        statement.setString(1,id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next())
        {
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            String password = resultSet.getString("password");

            user = new User(first_name,last_name, password);
            user.setId(id);
        }
        resultSet.close();

        return user;
    }
    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM users WHERE id_user=? and password = ?";
        User user = null;

        try(Connection connection = jdbcUtils.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");

                user = new User(first_name,last_name, password);
                user.setId(username);
            }
            resultSet.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return user;
    }
    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        String query = "SELECT * FROM users";

        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }
    @Override
    protected List<User> getEntities(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString("id_user");
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            String password = resultSet.getString("password");

            User user = new User(first_name,last_name, password);
            user.setId(id);

            users.add(user);
        }

        return users;
    }

    @Override
    protected PreparedStatement getSaveStatement(Connection connection) throws SQLException {

        String query = "INSERT INTO users(id_user,first_name,last_name,password) VALUES(?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);

        return statement;
    }

    @Override
    protected void saveEntity(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1,entity.getId());
        statement.setString(2,entity.getFirstName());
        statement.setString(3,entity.getLastName());
        statement.setString(4,entity.getPassword());

    }


}
