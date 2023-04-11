package com.example.gamificationapp.test;

import com.example.gamificationapp.domain.*;
import com.example.gamificationapp.services.WorkoutService;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutServiceTest {

    WorkoutService service = WorkoutService.getInstance();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void filterExercisesByCategory() {

        List<Exercise> exercises = service.filterExercisesByCategory(Category.LIFTING);
        assertEquals(2, exercises.size());
    }

    @org.junit.jupiter.api.Test
    void findUserForLogin() {
        User user1 = service.findUserForLogin("user1","1234");
        User user2 = service.findUserForLogin("user2","12345");

        assertEquals("user1", user1.getId(), "incorrect authentication");
        assertNull(user2, "user can login although it shouldn't");
    }

    @org.junit.jupiter.api.Test
    void getExercise() {
        Exercise exercise = service.getExercise(Category.LIFTING, Type.DEADLIFT, Measurement.KG);
        Exercise exerciseWrong = service.getExercise(Category.LIFTING, Type.DEADLIFT, Measurement.KM);
        assertEquals(1, exercise.getId(), "exercise not found");
        assertNull(exerciseWrong,"exercise should be null");
    }

    @org.junit.jupiter.api.Test
    void getLeaderboard() {
        List<UserLeaderboardDTO> leaderboardDTOList = service.getLeaderboard();
        assertTrue(leaderboardDTOList.size()>0,"leaderboard should have users");
    }
}