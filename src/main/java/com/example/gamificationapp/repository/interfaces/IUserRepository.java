package com.example.gamificationapp.repository.interfaces;

import com.example.gamificationapp.domain.User;

public interface IUserRepository extends IRepository<String, User>{

    /**
     * Find user by username and password for authentication
     * @param username - user' username
     * @param password - user's password
     * @return user of type User if the username and password are correct,
     * else null
     */
    User getUserByUsernameAndPassword(String username,String password);

}
