# GamificationApp

Tools used:
Javafx for the user interface
Gradle for dependecies
Postgresql for data persistence (there is also an image of a diagram of the database)

Gamification idea: Workout app where you can earn tokens and badges for completing quests from the other users

A quest contains:
an exercise from chosen from the following categories: lifting, cardio and sports
a measurement unit: kg, min, km and the value
tokens earned for completing it

The token system:
There is a function that calculates the tokens a quest is worth depending on the exercise and the value of the measurement unit 
(ex: a deadlift with 100kg generates 130 tokens)

A user cannot add a quest if he/she doesn't have more tokens than the quest is worth.

The badge system:
There are 2 types of badges: category badges and quest count badges
1) category badges are earned by doing an exercise with x amount of km/min/kg
2) quest count badges are earned by doing x amount of quests

The ranking system:
There is a leaderboard that displays the users with the most tokens and if you want more details you can check out their profile where you can see the quests that they
completed and the badges that they earned
