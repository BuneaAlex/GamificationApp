package com.example.gamificationapp.repository.db;


import com.example.gamificationapp.domain.Entity;
import com.example.gamificationapp.repository.interfaces.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractDBRepository<ID,E extends Entity<ID>> implements IRepository<ID,E> {

    protected final JDBCUtils jdbcUtils = new JDBCUtils();

    public AbstractDBRepository() {

    }

    protected abstract PreparedStatement getFindAllStatement(Connection connection) throws SQLException;
    protected abstract List<E> getEntities(ResultSet resultSet) throws SQLException;
    protected abstract PreparedStatement getFindOneStatement(Connection connection) throws SQLException;
    protected abstract E getEntity(PreparedStatement statement,ID id) throws SQLException;
    protected abstract PreparedStatement getSaveStatement(Connection connection) throws SQLException;
    protected abstract void saveEntity(PreparedStatement statement,E entity) throws SQLException;

    protected abstract PreparedStatement getUpdateStatement(Connection connection) throws SQLException;
    protected abstract void updateEntity(PreparedStatement statement,E entity) throws SQLException;

    @Override
    public E findOne(ID id) {

        E entity = null;

        try(PreparedStatement statement = getFindOneStatement(jdbcUtils.getConnection()))
        {
            entity = getEntity(statement,id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return entity;
    }



    @Override
    public Iterable<E> findAll() {

        List<E> entities = new ArrayList<>();

        try(PreparedStatement statement = getFindAllStatement(jdbcUtils.getConnection());
            ResultSet resultSet = statement.executeQuery())
        {
            entities = getEntities(resultSet);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return entities;
    }


    @Override
    public void save(E entity) {

        try(PreparedStatement statement = getSaveStatement(jdbcUtils.getConnection()))
        {
            saveEntity(statement,entity);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(E entity) {

        try(PreparedStatement statement = getUpdateStatement(jdbcUtils.getConnection()))
        {
            updateEntity(statement,entity);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }
}
