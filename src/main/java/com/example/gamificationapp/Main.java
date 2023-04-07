package com.example.gamificationapp;


import com.example.gamificationapp.domain.Category;
import com.example.gamificationapp.domain.Quest;
import com.example.gamificationapp.repository.db.ExerciseDBRepo;
import com.example.gamificationapp.repository.db.QuestDBRepo;
import com.example.gamificationapp.repository.db.UserDBRepo;
import com.example.gamificationapp.repository.interfaces.IExerciseRepository;
import com.example.gamificationapp.repository.interfaces.IQuestRepository;
import com.example.gamificationapp.repository.interfaces.IUserRepository;

public class Main {
    public static void main(String[] args) throws Exception {
        //IUserRepository repo = new UserDBRepo();
        //IExerciseRepository repo = new ExerciseDBRepo();
        IQuestRepository repo = new QuestDBRepo();
        //repo.save(new Quest(2,"desc2","user1",30,10));
        repo.findAll().forEach(System.out::println);
    }
}