package com.example.quizz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class inscritEtudController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField cinField;
    @FXML
    private TextField niveauField;



    public boolean insertButtonOnAction(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();
        String nom = nomField.getText();
        String prenom= prenomField.getText();
        String cin = cinField.getText();
        String niveau = niveauField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showErrorAlert("Empty Fields", "Please fill in all fields.");
            return false;
        }

        try {
            boolean success =databaseManager.insertEtudiant(cin,nom,prenom,email, password,niveau);
            if(success)
            {


                        FXMLLoader loader = new FXMLLoader(getClass().getResource("EspaceEtudiant.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                } else {
                showErrorAlert("Registration Failed", "An error occurred while registering. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while communicating with the database.");
            return false;
        }

        return false;

    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
