package com.example.gamificationapp.repository.db;


import com.example.gamificationapp.domain.Entity;
import com.example.gamificationapp.repository.interfaces.IRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Repository for basic operations for data stored in a database
 * @param <ID> - type E must have an attribute of type ID
 * @param <E>  -  type of entities saved in repository
 */

public abstract class AbstractDBRepository<ID,E extends Entity<ID>> implements IRepository<ID,E> {

    protected final JDBCUtils jdbcUtils = new JDBCUtils();

    public AbstractDBRepository() {

    }

    /**
     * Statement used to select all rows in the table
     * @param connection - database connection
     * @return statement that contains a query to do this operation
     * @throws SQLException if operation violates the consistency of the database
     */
    protected abstract PreparedStatement getFindAllStatement(Connection connection) throws SQLException;

    /**
     * Gets all the entities in the table
     * @return a list of all elements in the table
     * @throws SQLException if operation violates the consistency of the database
     */
    protected abstract List<E> getEntities(ResultSet resultSet) throws SQLException;

    /**
     * Statement used to find one entry based on id
     * @param connection - database connection
     * @return statement that contains a query to do this operation
     * @throws SQLException if operation violates the consistency of the database
     */
    protected abstract PreparedStatement getFindOneStatement(Connection connection) throws SQLException;

    /**
     * Find one entry based on id
     * @param statement - statement that contains a query to do this operation
     * @param id - id of the entity seeked
     * @return the entity if existent, else null
     * @throws SQLException if operation violates the consistency of the database
     */
    protected abstract E getEntity(PreparedStatement statement,ID id) throws SQLException;

    /**
     * Statement used to insert an entity in the table
     * @param connection - database connection
     * @return statement that contains a query to do this operation
     * @throws SQLException if operation violates the consistency of the database
     */
    protected abstract PreparedStatement getSaveStatement(Connection connection) throws SQLException;

    /**
     * Saving an entity to the database
     * @param statement - statement that contains a query to do this operation
     * @param entity - entity that is introduced in the table
     * @throws SQLException if operation violates the consistency of the database
     */
    protected abstract void saveEntity(PreparedStatement statement,E entity) throws SQLException;


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
}
