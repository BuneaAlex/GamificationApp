package com.example.gamificationapp.repository.interfaces;

import com.example.gamificationapp.domain.User;

public interface IUserRepository extends IRepository<String, User>{

    User getUserByUsernameAndPassword(String username,String password);

}
