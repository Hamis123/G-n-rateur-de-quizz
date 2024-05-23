package com.example.quizz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class connexionController {

    @FXML
    private Label actionEvent;
    @FXML
    private TextField login;
    @FXML
    private PasswordField pass;
    @FXML
    private TextField mailTextFiled;
    @FXML
    private RadioButton adminRadioButton;
    @FXML
    private RadioButton studentRadioButton;
    @FXML
    private RadioButton teacherRadioButton;

    public void loginButtonOnAction(ActionEvent e) throws SQLException {
        if (!adminRadioButton.isSelected() && !studentRadioButton.isSelected() && !teacherRadioButton.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Catégorie non sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Vous devez choisir votre catégorie (étudiant, enseignant, administrateur).");
            alert.showAndWait();
            return;
        } else {
            String email = login.getText();
            String mdp = pass.getText();
            boolean isConnected = false;
            if (adminRadioButton.isSelected()) {
                isConnected = databaseManager.verifierAdmin(email, mdp);
                if (isConnected) {
                    System.out.println("L'administrateur est connecté.");
                    loadAdminSpaceView();
                    return;
                }
            } else if (studentRadioButton.isSelected()) {
                isConnected = databaseManager.verifierEtudiant(email, mdp);
                if (isConnected) {
                    System.out.println("L'étudiant est connecté.");
                    loadStudentSpaceView();
                    return;
                }
            } else if (teacherRadioButton.isSelected()) {
                isConnected = databaseManager.verifierEnseignant(email, mdp);
                if (isConnected) {
                    System.out.println("Le professeur est connecté.");
                    loadTeacherSpaceView();
                    return;
                }
            }

            System.out.println("L'utilisateur n'est pas connecté.");
        }
    }
    public  void inscrirButtonOnAction(ActionEvent e){
        if (adminRadioButton.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Catégorie non sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText(" Admin n'est pas  autorisé à inscrir ,il faut choisir etudiant ou enseignant");
            alert.showAndWait();
            return;
        }
        else if (!adminRadioButton.isSelected() && !studentRadioButton.isSelected() && !teacherRadioButton.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Catégorie non sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("il faut choisir etudiant ou enseignant.");
            alert.showAndWait();
            return;}
        else {
            if (teacherRadioButton.isSelected()){
             loadSpaceView("inscritEnseignant.fxml");}
            else{
                loadSpaceView("inscritEtudiant.fxml");}
        }
    }


    private void loadAdminSpaceView() {
        loadSpaceView("EspaceAdmin.fxml");
    }

    private void loadStudentSpaceView() {
        loadSpaceView("EspaceEtudiant.fxml");
    }

    private void loadTeacherSpaceView() {
        loadSpaceView("EspaceEnseignant.fxml");
    }

    private void loadSpaceView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage logStage = (Stage) login.getScene().getWindow();
            logStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
