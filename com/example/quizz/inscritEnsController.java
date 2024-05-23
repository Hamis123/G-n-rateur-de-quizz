package com.example.quizz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class inscritEnsController {


    @FXML
    private TextField mailField;

    @FXML
    private PasswordField passField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField pnameField;
    @FXML
    private TextField cinnField;


    public boolean insertButtonOnAction(ActionEvent event) {
        String email = mailField.getText();
        String password = passField.getText();
        String nom = nameField.getText();
        String prenom= pnameField.getText();
        String cin = cinnField.getText();


        if (email.isEmpty() || password.isEmpty()) {
            showErrorAlert("Champs vides ", "Remplir tous les champs s'il vous plait !.");
            return false;
        }

        try {
            boolean success2 =databaseManager.insertEnseignant(cin,nom,prenom,email, password);
            if(success2)
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("EspaceEnseignant.fxml"));
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
                showErrorAlert("Registration Failed", "Un erreur se produit . Veuillez réessayer.");
            }
        } catch (SQLException e) {
            // Log the exception
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

