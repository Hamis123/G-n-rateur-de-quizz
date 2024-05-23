package com.example.quizz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.quizz.databaseManager.insertReponses;

public class AjouterReponcesController {
    @FXML
    private TextField reponceATextField;

    @FXML
    private TextField reponceBTextField;

    @FXML
    private TextField reponceCTextField;
    @FXML
    private CheckBox answerACheckBox;

    @FXML
    private CheckBox answerBCheckBox;

    @FXML
    private CheckBox answerCCheckBox;

    //*******************Retour à la page precedente*********************/
    public void deconnecterOnClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterQuestion.fxml"));
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


    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void onValiderButtonClick(ActionEvent event) throws SQLException {

        Integer id = databaseManager.getLastInsertedQuestionId();
        if (id != null) {
            String reponcesAText = reponceATextField.getText();
            String reponcesBText = reponceBTextField.getText();
            String reponcesCText = reponceCTextField.getText();

            String etatA = answerACheckBox.isSelected() ? "1" : "0";
            String etatB = answerBCheckBox.isSelected() ? "1" : "0";
            String etatC = answerCCheckBox.isSelected() ? "1" : "0";

            insertReponses(reponcesAText, etatA, id);
            insertReponses(reponcesBText, etatB, id);
            insertReponses(reponcesCText, etatC, id);
        } else {
            showErrorAlert("Question ID Erreur", "Échec de la récupération de l'ID de question à partir de la base de données.");
        }
    }

}