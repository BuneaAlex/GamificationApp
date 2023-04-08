package com.example.gamificationapp.controller;

import com.example.gamificationapp.domain.Badge;
import com.example.gamificationapp.domain.ProfileDTO;
import com.example.gamificationapp.domain.User;
import com.example.gamificationapp.domain.UserLeaderboardDTO;
import com.example.gamificationapp.utils.Constants;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The controller the deals with the users profiles
 */
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

    /**
     * Constructor
     * @param usersProfile the user that the profile belongs to
     * @param exUser the user that has requested to see the profile
     */

    public ProfileController(User usersProfile, User exUser) {
        this.usersProfile = usersProfile;
        this.exUser = exUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initQuestTableModel();
        initBadgeModel();
    }

    /**
     * Initialization of the tables that contains the quests
     */
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
        String rankString = "";
        int rank = getRank();
        if(rank != -1)
            rankString = String.valueOf(rank);
        else
            rankString = "No rank";
        String textAreaString = usersProfile.toString() + '\n' + "quests completed: " + numberOfCompletedQuests + '\n' +
                "rank: " + rankString;
        descriptionTextArea.setText(textAreaString);
    }

    /**
     * Initialization the badges list
     */
    void initBadgeModel()
    {
        List<Badge> badges = service.getUsersBadges(usersProfile.getId());

        if(badges.size()==0)
        {
            badgesBox.getChildren().add(new Label("No badges"));
        }

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

    /**
     *
     * @return the rank of the user or -1 if the user doesn't have a rank
     */
    private int getRank()
    {
        List<UserLeaderboardDTO> users = service.getLeaderboard();
        for(UserLeaderboardDTO u : users)
        {
            if(Objects.equals(u.getUser().getId(), usersProfile.getId()))
                return u.getRank();
        }
        return -1;
    }

    /**
     * Returns the user to the main menu
     * @param actionEvent the action that causes the window to open
     * @throws IOException if an error of type IO occurs
     */
    public void backToMainMenuAction(ActionEvent actionEvent) throws IOException {
        openMainWindow(actionEvent,exUser);
    }
}
