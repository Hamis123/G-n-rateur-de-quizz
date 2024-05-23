package com.example.quizz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.List;


public class GererQuestionController {
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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

    public void CreerQuestionOnAction(ActionEvent event){


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterQuestion.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                ((Node)(event.getSource())).getScene().getWindow().hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    private final databaseManager databaseManager = new databaseManager();

    @FXML
    private void initialize() {

        idQuestionColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // Field names are case-sensitive
        questionTextColumn.setCellValueFactory(new PropertyValueFactory<>("enonce"));   // Corrected case
        niveauColumn.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        NomCoursColumn.setCellValueFactory(new PropertyValueFactory<>("cours"));


        try {
            questionTableView.setItems(databaseManager.getAllQuestions());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TableColumn<Question, String> NomCoursColumn;

    @FXML
    private TableColumn<Question, Integer> idQuestionColumn;

    @FXML
    private TableColumn<Question, String> niveauColumn;

    @FXML
    private TableView<Question> questionTableView;

    @FXML
    private TableColumn<Question, String> questionTextColumn;


    //**************** SUPPRIMER UN Question *************************/
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Question selectedQuestion = questionTableView.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            questionTableView.getItems().remove(selectedQuestion);

            deleteQuestionFromDatabase(selectedQuestion);
        } else {
            showErrorAlert("No Row Selected", "Please select a row to delete.");
        }
    }


    private void deleteQuestionFromDatabase(Question question) {
        try {
            databaseManager.deleteQuestion(question);
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while deleting the record from the database.");
        }
    }

    //*************MODIFIER UN QUESTION**************************

    @FXML
    private void handleModifyButtonAction(ActionEvent event) {
        Question selectedQuestion = questionTableView.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            openEditDialog(selectedQuestion);

            questionTableView.refresh();
        } else {
            showErrorAlert("No Row Selected", "Please select a row to modify.");
        }
    }
    private void openEditDialog(Question question) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier Question ");

        TextField enonceField = new TextField(question.getEnonce());
        TextField niveauField = new TextField(question.getNiveau());
        TextField coursField = new TextField(question.getCours());


        dialog.getDialogPane().setContent(new VBox(
                new Label("Question:"), enonceField,
                new Label("Niveau(Facile,Moyen,Difficile):"), niveauField,
                new Label("Matières:"), coursField

        ));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String modifiedQuestion = enonceField.getText();
                String modifiedNiveau = niveauField.getText();
                String modifiedCours = coursField.getText();



                question.setEnonce(modifiedQuestion);
                question.setNiveau(modifiedNiveau);
                question.setNiveau(modifiedCours);


                questionTableView.refresh();

                updateQuestionInDatabase(question);
            }
            return null;
        });

        dialog.showAndWait();
    }
    private void updateQuestionInDatabase(Question question) {
        try {
            databaseManager.modifierQuestion(question);
            System.out.println("Question updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while updating the database.");
        }
    }

}


