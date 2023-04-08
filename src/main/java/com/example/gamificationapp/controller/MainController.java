package com.example.gamificationapp.controller;

import com.example.gamificationapp.domain.*;
import com.example.gamificationapp.utils.SimpleAlertBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The main controller that has access to all the features
 */
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

    /**
     * Initialization of the comboboxes
     */
    private void initComboBoxModel()
    {
        loadProofButton.setDisable(true);
        completeQuestButton.setDisable(true);
        categoriesList.setAll(Category.values());
        categoryComboBox.setItems(categoriesList);
        exerciseComboBox.setItems(exercisesList);
    }

    /**
     * Initialization of the leaderboard table
     */
    private void initLeaderboardTable()
    {
        rankLeaderboardColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getRank())));
        nameLeaderboardColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getUser().getId()));
        tokensLeaderboardColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(String.valueOf(cellData.getValue().getTokens())));
        leaderBoardTableView.setItems(leaderboardList);
        loadLeaderboard();
    }

    /**
     * Loads the leaderboard
     */
    private void loadLeaderboard()
    {
        leaderboardList.setAll(service.getLeaderboard());
    }

    /**
     * Initialization of the available quests to complete by user
     */
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

    /**
     * Sets the quests not completed by user to the questsList
     */
    private void loadQuests()
    {
        questsList.setAll(service.getQuestToCompleteByUser(user.getId()));
    }


    private User user;
    private Quest questSelected = null;

    /**
     * Constructor
     * @param user the user that has the account
     */
    public MainController(User user) {
        this.user = user;
    }


    /**
     * Logout
     * @param actionEvent the action that causes the window to open
     * @throws IOException if an error of type IO occurs
     */
    public void logoutAction(ActionEvent actionEvent) throws IOException {
        openLoginWindow(actionEvent);
    }

    /**
     * Select a category for exercises and set the exercise types combobox based on that category
     * @param actionEvent action of the event
     */
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

    /**
     * Select a type of exercise and set the measurement label based on that type
     * @param actionEvent action of the event
     */
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

    /**
     * Function that calculates the tokens of a quest based on exercise and measurement units
     * @param category category
     * @param exercise exercise
     * @return the number of tokens that the quest is worth
     */
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

    /**
     * Creates a quest and saves it, if it is worth more than 0 tokens and less than the amount of tokens that the author has
     * Shows error message if the quest is worth 0 tokens
     * Shows error message if the quest is worth more tokens than the user can afford
     * @param actionEvent action of the event
     */

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

    /**
     * Visits the users from the leaderboard profiles
     * @param actionEvent the action that causes the window to open
     * @throws IOException if an error of type IO occurs
     */
    public void seeUserProfile(ActionEvent actionEvent) throws IOException {
        int index = leaderBoardTableView.getSelectionModel().getSelectedIndex();
        if(index!=-1)
        {
            UserLeaderboardDTO userLeaderboardDTO = leaderBoardTableView.getSelectionModel().getSelectedItem();
            openProfileWindow(actionEvent,userLeaderboardDTO.getUser(),user);
        }
    }

    /**
     * The user visits its own profile
     * @param actionEvent the action that causes the window to open
     * @throws IOException if an error of type IO occurs
     */
    public void seeProfileAction(ActionEvent actionEvent) throws IOException {
        openProfileWindow(actionEvent,user,user);
    }

    /**
     * The user loads the proof that the quest is actually completed (functionality not implemented yet)
     * @param actionEvent action of the event
     */
    public void loadProofAction(ActionEvent actionEvent) {
        completeQuestButton.setDisable(false);
    }

    /**
     * The user completes the quest
     * The user is informed that the quest is completed and if any badges have been earned in the process
     * The controls are reset
     * @param actionEvent action of the event
     */
    public void completeQuestAction(ActionEvent actionEvent) {

        service.completeQuest(user,questSelected, LocalDateTime.now());
        loadQuests();
        loadLeaderboard();
        loadProofButton.setDisable(true);
        completeQuestButton.setDisable(true);
        descriptionQuestTextArea.setText("");

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

    /**
     * The user selects which quest he/she want to complete and a detailed description of the quest is loaded
     * @param actionEvent action of the event
     */
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
