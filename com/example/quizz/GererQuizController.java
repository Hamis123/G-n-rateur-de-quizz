package com.example.quizz;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;




public class GererQuizController {
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private TableColumn<Quizz, String> dateCreationColumn;

    @FXML
    private TableColumn<Quizz, String> matiereColumn;

    @FXML
    private TableColumn<Quizz, Integer> nbquestionColumn;

    @FXML
    private TableColumn<Quizz, String> niveauColumn;

    @FXML
    private TableView<Quizz> quizzTableView;

    @FXML
    private void initialize() {
        // Set up the columns in the TableView
        matiereColumn.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        niveauColumn.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        nbquestionColumn.setCellValueFactory(new PropertyValueFactory<>("nbQuestion"));
       // dateCreationColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        try {
            quizzTableView.setItems(databaseManager.getAllQuizzes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void voirQuizzOnAction(ActionEvent event) {
            Quizz selectedQuizz = quizzTableView.getSelectionModel().getSelectedItem();

            if (selectedQuizz == null) {
                showErrorAlert("No Selection", "Please select a quiz to view.");
                return;
            }

            int idQuiz = selectedQuizz.getIdQuiz();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizzView.fxml"));
                Parent root = loader.load();

                QuizzViewController controller = loader.getController();
                controller.initialize(idQuiz);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showErrorAlert("Error", "Failed to load the quiz view.");
            }
        }

    }