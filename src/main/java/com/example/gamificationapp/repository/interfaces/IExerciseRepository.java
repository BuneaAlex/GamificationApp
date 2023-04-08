package com.example.gamificationapp.repository.interfaces;

import com.example.gamificationapp.domain.Category;
import com.example.gamificationapp.domain.Exercise;
import com.example.gamificationapp.domain.Measurement;
import com.example.gamificationapp.domain.Type;

import java.util.List;

public interface IExerciseRepository extends IRepository<Integer, Exercise> {

    /**
     * Filter exercises based on their category
     * @param category - category
     * @return List of exercises that fit the category
     */
    List<Exercise> exercisesFilterByCategory(Category category);

    /**
     * Find exercise based on some parameters
     * @param category - category
     * @param type - type
     * @param measurement - measurement
     * @return exercise of type Exercise if existent, else null
     */
    Exercise findExerciseByValues(Category category, Type type, Measurement measurement);
}
