package com.example.gamificationapp.repository.interfaces;

import com.example.gamificationapp.domain.Category;
import com.example.gamificationapp.domain.Exercise;
import com.example.gamificationapp.domain.Measurement;
import com.example.gamificationapp.domain.Type;

import java.util.List;

public interface IExerciseRepository extends IRepository<Integer, Exercise> {

    List<Exercise> exercisesFilterByCategory(Category category);

    Exercise findExerciseByValues(Category category, Type type, Measurement measurement);
}
