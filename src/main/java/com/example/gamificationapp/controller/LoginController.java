package com.example.gamificationapp.controller;

import com.example.gamificationapp.domain.User;
import com.example.gamificationapp.utils.SimpleAlertBuilder;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends AbstractController{
    public TextField usernameTextField;
    public PasswordField passwordTextField;

    /**
     * Login the user into account if username and password are correct, else show alert message
     * @param actionEvent the action of the event
     * @throws IOException if an error of type IO occurs
     */
    public void loginAction(ActionEvent actionEvent) throws IOException {
        User user = service.findUserForLogin(usernameTextField.getText(),passwordTextField.getText());
        if(user != null)
        {
            openMainWindow(actionEvent,user);
        }
        else
        {
            SimpleAlertBuilder alert = new SimpleAlertBuilder(Alert.AlertType.WARNING, "Message", "User doesn't exist!");
            alert.show();
        }

    }
}
