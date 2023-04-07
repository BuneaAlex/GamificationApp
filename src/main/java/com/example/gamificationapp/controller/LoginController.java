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
