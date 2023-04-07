package com.example.gamificationapp.controller;

import com.example.gamificationapp.HelloApplication;
import com.example.gamificationapp.domain.*;
import com.example.gamificationapp.utils.SimpleAlertBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends AbstractController implements Initializable {

    public TableView<UserLeaderboardDTO> leaderBoardTableView;
    public TableColumn<UserLeaderboardDTO,String> rankLeaderboardColumn;
    public TableColumn<UserLeaderboardDTO,String> nameLeaderboardColumn;
    public TableColumn<UserLeaderboardDTO,String> tokensLeaderboardColumn;
    public TableColumn<Quest,String> categoryColumn;

    public TableColumn<Quest,String> authorColumn;
    public TableColumn<Quest,String> tokensColumn;
    public TableView<Quest> questTableView;
    public Slider quantitySlider;
    public Label sliderValueLabel;
    public ComboBox<Category> categoryComboBox;
    public ComboBox<Type> exerciseComboBox;
    public Label measurementLabel;
    public Label tokensLabel;
    public TextArea descriptionTextArea;
    public TextArea descriptionQuestTextArea;
    public Button completeQuestButton;
    public Button loadProofButton;

    @FXML
    private ObservableList<Category> categoriesList = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Type> exercisesList = FXCollections.observableArrayList();

    @FXML
    private ObservableList<UserLeaderboardDTO> leaderboardList = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Quest> questsList = FXCollections.observableArrayList();

    private List<Exercise> exercisesAvailable;

    private Exercise currentExercise = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sliderValueLabel.textProperty().bind(
                Bindings.format(
                        "%.0f",
                        quantitySlider.valueProperty()
                )
        );

        quantitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int tokens = calculateTokens(categoryComboBox.getValue(),exerciseComboBox.getValue());
            tokensLabel.setText(String.valueOf(tokens));
        });

        initComboBoxModel();
        initLeaderboardTable();
        initQuestToCompleteTable();
    }

    private void initComboBoxModel()
    {
        loadProofButton.setDisable(true);
        completeQuestButton.setDisable(true);
        categoriesList.setAll(Category.values());
        categoryComboBox.setItems(categoriesList);
        exerciseComboBox.setItems(exercisesList);
    }

    private void initLeaderboardTable()
    {
        rankLeaderboardColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getRank())));
        nameLeaderboardColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getUser().getId()));
        tokensLeaderboardColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(String.valueOf(cellData.getValue().getTokens())));
        leaderBoardTableView.setItems(leaderboardList);
        leaderboardList.setAll(service.getLeaderboard());
    }

    private void initQuestToCompleteTable()
    {
        categoryColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getExercise().getCategory().toString()));
        authorColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAuthor()));
        tokensColumn.setCellValueFactory(cellData ->
            new SimpleStringProperty(String.valueOf(cellData.getValue().getTokens())));
        questTableView.setItems(questsList);
        loadQuests();
    }

    private void loadQuests()
    {
        questsList.setAll(service.getQuestToCompleteByUser(user.getId()));
    }


    private User user;
    private Quest questSelected = null;

    public MainController(User user) {
        this.user = user;
    }


    public void logoutAction(ActionEvent actionEvent) throws IOException {
        openLoginWindow(actionEvent);
    }

    public void selectCategoryAction(ActionEvent actionEvent) {
        exercisesAvailable = service.filterExercisesByCategory(categoryComboBox.getSelectionModel().getSelectedItem());
        List<Type> exerciseTypes = new ArrayList<>();
        for(Exercise ex : exercisesAvailable)
        {
            exerciseTypes.add(ex.getType());
        }

        exercisesList.setAll(exerciseTypes);
        measurementLabel.setText("Measurement");

    }

    public void selectExerciseTypeAction(ActionEvent actionEvent) {
        Type exerciseType = exerciseComboBox.getSelectionModel().getSelectedItem();
        for(Exercise e : exercisesAvailable)
        {
            if(e.getType() == exerciseType)
            {
                currentExercise = e;
                break;
            }
        }
        measurementLabel.setText(currentExercise.getMeasurement().toString());
    }

    private int calculateTokens(Category category,Type exercise)
    {
        int baseTokens = 50;
        double sliderValue = quantitySlider.getValue();
        int tokensMultiplier;

        if (sliderValue < 25) {
            tokensMultiplier = 0;
        } else if (sliderValue < 50) {
            tokensMultiplier = 1;
        } else if (sliderValue < 75) {
            tokensMultiplier = 2;
        } else if (sliderValue < 100) {
            tokensMultiplier = 3;
        } else if (sliderValue < 125) {
            tokensMultiplier = 4;
        } else if (sliderValue < 150) {
            tokensMultiplier = 5;
        } else if (sliderValue < 175) {
            tokensMultiplier = 6;
        } else if (sliderValue < 200) {
            tokensMultiplier = 7;
        } else {
            tokensMultiplier = 8;
        }

        if(category == null || exercise == null)
            return 0;

        switch (category) {
            case LIFTING:
                return switch (exercise) {
                    case BENCHPRESS -> (int) (baseTokens * 1.5 * tokensMultiplier);
                    case DEADLIFT -> (int) (baseTokens * 1.3 * tokensMultiplier);
                    default -> baseTokens * tokensMultiplier;
                };
            case SPORTS:
                return switch (exercise) {
                    case BASKETBALL -> (int) (baseTokens * 1.3 * tokensMultiplier);
                    case FOOTBALL -> (int) (baseTokens * 1.5 * tokensMultiplier);
                    case TENNIS -> (int) (baseTokens * 1.3 * tokensMultiplier);
                    default -> baseTokens * tokensMultiplier;
                };
            case CARDIO:
                return switch (exercise) {
                    case RUNNING -> (int) (baseTokens * 1.4 * tokensMultiplier);
                    case CYCLING -> (int) (baseTokens * 1.2 * tokensMultiplier);
                    default -> baseTokens * tokensMultiplier;
                };
            default:
                return baseTokens * tokensMultiplier;
        }
    }



    public void addQuestAction(ActionEvent actionEvent) {

        int tokens = Integer.parseInt(tokensLabel.getText());
        int usersTokens = service.getUsersTokens(user.getId());

        if(tokens > 0)
        {
            if(usersTokens >= tokens)
            {
                Category category = categoryComboBox.getValue();
                Type type = exerciseComboBox.getValue();
                Measurement measurement = Measurement.valueOf(measurementLabel.getText());
                Exercise exercise = service.getExercise(category,type,measurement);
                String authorName = user.getId();
                String description = descriptionTextArea.getText();
                Quest quest = new Quest(exercise,description,authorName, Integer.parseInt(sliderValueLabel.getText()),tokens);
                service.addQuest(quest);
                quantitySlider.setValue(0);
                SimpleAlertBuilder alert = new SimpleAlertBuilder(Alert.AlertType.INFORMATION, "Message", "Quest added!");
                alert.show();
            }
            else
            {
                SimpleAlertBuilder alert = new SimpleAlertBuilder(Alert.AlertType.WARNING, "Message", "You don't have enough tokens to publish this quest!");
                alert.show();
            }

        }
        else {
            SimpleAlertBuilder alert = new SimpleAlertBuilder(Alert.AlertType.WARNING, "Message", "Cannot make a quest with 0 tokens!");
            alert.show();
        }
    }

    public void seeUserProfile(ActionEvent actionEvent) throws IOException {
        int index = leaderBoardTableView.getSelectionModel().getSelectedIndex();
        if(index!=-1)
        {
            UserLeaderboardDTO userLeaderboardDTO = leaderBoardTableView.getSelectionModel().getSelectedItem();
            openProfileWindow(actionEvent,userLeaderboardDTO.getUser(),user);
        }
    }

    public void seeProfileAction(ActionEvent actionEvent) throws IOException {
        openProfileWindow(actionEvent,user,user);
    }

    public void loadProofAction(ActionEvent actionEvent) {
        completeQuestButton.setDisable(false);
    }

    public void completeQuestAction(ActionEvent actionEvent) {

        service.completeQuest(user,questSelected, LocalDateTime.now());
        loadQuests();
        loadProofButton.setDisable(true);
        completeQuestButton.setDisable(true);

        SimpleAlertBuilder alert = new SimpleAlertBuilder(Alert.AlertType.INFORMATION, "Message", "Quest completed!");
        alert.show();


        for(CategoryBadge cb : service.obtainCategoryBadge(user.getId(), questSelected))
        {
            SimpleAlertBuilder alertBadge = new SimpleAlertBuilder(Alert.AlertType.INFORMATION, "Message", "You obtained: " + cb.getId());
            alertBadge.show();
        }

        for(QuestNumberBadge cb : service.obtainQuestNumberBadge(user.getId()))
        {
            SimpleAlertBuilder alertBadge = new SimpleAlertBuilder(Alert.AlertType.INFORMATION, "Message", "You obtained: " + cb.getId());
            alertBadge.show();
        }
    }

    public void selectQuestToCompleteAction(ActionEvent actionEvent) {
        loadProofButton.setDisable(false);
        int index = questTableView.getSelectionModel().getSelectedIndex();
        if(index!=-1)
        {
            questSelected = questTableView.getSelectionModel().getSelectedItem();
            String description = "Exercise: " + questSelected.getExercise().toString() + '\n' + "Description: " +
                    questSelected.getDescription() + '\n' + "Units: " + questSelected.getMeasurementUnits();
            descriptionQuestTextArea.setText(description);
        }
    }
}
