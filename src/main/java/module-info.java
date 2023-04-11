module com.example.gamificationapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.junit.jupiter.api;

    opens com.example.gamificationapp to javafx.fxml;
    opens com.example.gamificationapp.controller to javafx.fxml;
    exports com.example.gamificationapp;
    exports com.example.gamificationapp.controller;
    exports com.example.gamificationapp.domain;
    exports com.example.gamificationapp.services;
    exports com.example.gamificationapp.repository.interfaces;
}