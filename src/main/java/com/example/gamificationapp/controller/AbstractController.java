package com.example.gamificationapp.controller;


import com.example.gamificationapp.HelloApplication;
import com.example.gamificationapp.domain.User;
import com.example.gamificationapp.services.WorkoutService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A class that contains common functions and attributes for all controllers
 */

public abstract class AbstractController {

    protected WorkoutService service = WorkoutService.getInstance();

    /**
     * Open Login Window
     * @param actionEvent the action that causes the window to open
     * @throws IOException if an error of type IO occurs
     */
    protected void openLoginWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
    }

    /**
     * Open Main Window
     * @param actionEvent the action that causes the window to open
     * @throws IOException if an error of type IO occurs
     */
    protected void openMainWindow(ActionEvent actionEvent, User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new MainController(user));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setTitle(user.getId());
        stage.setScene(scene);
    }

    /**
     * Open Profile Window
     * @param actionEvent the action that causes the window to open
     * @throws IOException if an error of type IO occurs
     */
    protected void openProfileWindow(ActionEvent actionEvent, User user, User exUser) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("profile-view.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new ProfileController(user,exUser));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setTitle(user.getId());
        stage.setScene(scene);
    }

    /**
     * Close current window
     * @param actionEvent the action that causes the window to open
     */
    protected void closeWindow(ActionEvent actionEvent)
    {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}
