package com.example.gamificationapp.controller;

import com.example.gamificationapp.domain.Badge;
import com.example.gamificationapp.domain.ProfileDTO;
import com.example.gamificationapp.domain.User;
import com.example.gamificationapp.utils.Constants;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileController extends AbstractController implements Initializable {

    public TableView<ProfileDTO> questProfileTableView;
    public TableColumn<ProfileDTO,String> categoryProfileColumn;
    public TableColumn<ProfileDTO,String> authorProfileColumn;
    public TableColumn<ProfileDTO,String> tokensProfileColumn;

    public TableColumn<ProfileDTO,String> dateProfileColumn;
    public TextArea descriptionTextArea;
    public HBox badgesBox;

    @FXML
    private ObservableList<ProfileDTO> profileDTOS = FXCollections.observableArrayList();

    private User usersProfile;
    private User exUser;

    public ProfileController(User usersProfile, User exUser) {
        this.usersProfile = usersProfile;
        this.exUser = exUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initQuestTableModel();
        initBadgeTableModel();
    }

    void initQuestTableModel()
    {
        categoryProfileColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getQuest().getExercise().getCategory().toString()));
        authorProfileColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getQuest().getAuthor()));
        tokensProfileColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getQuest().getTokens())));
        dateProfileColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateOfCompletion().format(Constants.DATE_FORMAT)));
        questProfileTableView.setItems(profileDTOS);
        profileDTOS.setAll(service.getQuestsCompletedByUser(usersProfile.getId()));
        int numberOfCompletedQuests = profileDTOS.size();
        String textAreaString = usersProfile.toString() + '\n' + "quests completed: " + numberOfCompletedQuests;
        descriptionTextArea.setText(textAreaString);
    }

    void initBadgeTableModel()
    {
        List<Badge> badges = service.getUsersBadges(usersProfile.getId());
        for(Badge b : badges)
        {
            ImageView imageView = new ImageView();
            Tooltip tooltip = new Tooltip(b.getId());
            Tooltip.install(imageView, tooltip);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            String imgPath = "D:\\an 2\\extra\\GamificationApp\\src\\main\\resources\\com\\example\\img\\" +  b.getImage();
            Image image = new Image(imgPath);
            imageView.setImage(image);
            badgesBox.getChildren().add(imageView);
        }

    }

    public void backToMainMenuAction(ActionEvent actionEvent) throws IOException {
        openMainWindow(actionEvent,exUser);
    }
}
