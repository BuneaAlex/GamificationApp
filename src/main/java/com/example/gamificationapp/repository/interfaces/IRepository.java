package com.example.gamificationapp.repository.interfaces;

import com.example.gamificationapp.domain.Entity;

/**
 * CREATE, READ, UPDATE operations repository interface
 *
 * @param <ID> - type E must have an attribute of type ID
 * @param <E>  -  type of entities saved in repository
 */

public interface IRepository<ID, E extends Entity<ID>> {

    /**
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return the entity with the specified id
     * or null - if there is no entity with the given id
     */
    E findOne(ID id);

    /**
     * @return all entities
     */
    Iterable<E> findAll();

    /**
     * @param entity entity must be not null.
     * saves given the entity
     */
    void save(E entity);

    /**
     * @param entity entity must not be null
     * updates the element in the repo with the same ID, giving it entity's values for its attributes
     */
    void update(E entity);
}
