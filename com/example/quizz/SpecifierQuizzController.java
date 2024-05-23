package com.example.quizz;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class SpecifierQuizzController {
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    //Retour à la page precedente
    public void deconnecterOnClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EspaceEnseignant.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Espace Enseignant");
            newStage.show();

        } catch (IOException e) {
            e.getMessage();
        }
    }

    @FXML
    private TextField coursTextFiled;

    @FXML
    private RadioButton difficileRadioButton;

    @FXML
    private RadioButton facileRadioButton;

    @FXML
    private RadioButton moyenRadioButton;

    @FXML
    private ToggleGroup niveau;

    @FXML
    private TableColumn<Question, String> questionColumn;

    @FXML
    private TableView<Question> questionTableView;

    private final databaseManager databaseManager = new databaseManager();

   @FXML
    private void initialize() {
       questionColumn.setCellValueFactory(new PropertyValueFactory<>("enonce"));
       questionTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
   }


    @FXML
    private void OKOnAction(ActionEvent event) {

        try {
            String difficulte = "";
            if (facileRadioButton.isSelected()) {
                difficulte = "facile";
            } else if (moyenRadioButton.isSelected()) {
                difficulte = "moyen";
            } else if (difficileRadioButton.isSelected()) {
                difficulte = "difficile";
            }
            String coursText = coursTextFiled.getText();
            questionTableView.setItems(databaseManager.getQuestions(coursText, difficulte));
        }catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while interacting with the database.");
        }

        }
    public void CreerQuizzOnAction(ActionEvent actionEvent) {
        ObservableList<Question> selectedQuestions = questionTableView.getSelectionModel().getSelectedItems();

        if (selectedQuestions.isEmpty()) {
            showErrorAlert("No Selection", "Please select at least one question to create a quiz.");
            return;
        }
        try {
             String matiere = coursTextFiled.getText();

                String difficulte = "";
                if (facileRadioButton.isSelected()) {
                    difficulte = "facile";
                } else if (moyenRadioButton.isSelected()) {
                    difficulte = "moyen";
                } else if (difficileRadioButton.isSelected()) {
                    difficulte = "difficile";
                }
                int nbQuestion = selectedQuestions.size();
            int quizId =  com.example.quizz.databaseManager.insertQuizz(matiere, difficulte, nbQuestion);
            openQuizzGenerer(actionEvent,quizId);

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while inserting data into the database.");
        }

    }
    private void openQuizzGenerer(ActionEvent event, int quizId) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("GererQuiz.fxml"));
            Parent root = loader.load();
            GererQuizController controller = loader.getController();
          //  controller.loadQuizzes(quizId);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Loading Error", "An error occurred while loading GererQuizz.fxml.");
        }
    }


}