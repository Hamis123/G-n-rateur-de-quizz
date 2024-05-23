package com.example.quizz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class AjouterQuestionController {

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //**********************Retour à la page precedente************************/
    public void deconnecterOnClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GererQuestion.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Gestion des questions");
            newStage.show();

        } catch (IOException e) {
            e.getMessage();
        }
    }

    @FXML
    private RadioButton difficileRadioButton;
    @FXML
    private RadioButton facileRadioButton;
    @FXML
    private RadioButton moyenRadioButton;
    @FXML
    private TextField questionTextField;
    @FXML
    private TextField coursTextField;
    @FXML
    void onValidateButtonClick(ActionEvent event) throws SQLException {

        try {
            String questionText = questionTextField.getText();

            String difficulte = "";
            if (facileRadioButton.isSelected()) {
                difficulte = "facile";
            } else if (moyenRadioButton.isSelected()) {
                difficulte = "moyen";
            } else if (difficileRadioButton.isSelected()) {
                difficulte = "difficile";
            }

            String coursText = coursTextField.getText();

            Integer id = databaseManager.insertQuestion(questionText, difficulte,coursText);

            if (id != -1) {
                // ouvrir  AjouterReponces.fxml pour insérer les reponses
                openAjouterReponces(event);
            } else {
                showErrorAlert("Question Insertion Error", "Failed to insert the question into the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while interacting with the database.");
        }
    }

    //******************* Methode pour ouvrir AjouterReponces.fxml *****************/
    private void openAjouterReponces(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterReponces.fxml"));
            Parent root = loader.load();
            AjouterReponcesController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Choix des reponces ");
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("FXML Loading Error", "An error occurred while loading AjouterReponces.fxml.");
        }
    }
}