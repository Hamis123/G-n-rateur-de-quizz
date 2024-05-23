package com.example.quizz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class gererEtudiantController {

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EspaceAdmin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Espace Admin");
            newStage.show();

        } catch (IOException e) {
            e.getMessage();
        }

    }


    @FXML
    private TableView<Etudiant> studentTableView;
    @FXML
    private TableColumn<Etudiant, String> cinColumn;
    @FXML
    private TableColumn<Etudiant, String> nomColumn;
    @FXML
    private TableColumn<Etudiant, String> prenomColumn;
    @FXML
    private TableColumn<Etudiant, String> emailColumn;
    @FXML
    private TableColumn<Etudiant, String> passwordColumn;
    @FXML
    private TableColumn<Etudiant, String> niveauColumn;

    private final databaseManager databaseManager = new databaseManager();

    @FXML
    private void initialize() {
        cinColumn.setCellValueFactory(cellData -> cellData.getValue().cinProperty());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().mailProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().motpasseProperty());
        niveauColumn.setCellValueFactory(cellData -> cellData.getValue().niveauProperty());

        try {
            studentTableView.setItems(databaseManager.getTousEtudiants());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModifyButtonAction(ActionEvent event) {
        Etudiant selectedEtudiant = studentTableView.getSelectionModel().getSelectedItem();
        if (selectedEtudiant != null) {

            openEditDialog(selectedEtudiant);

            studentTableView.refresh();
        } else {
            // Show an error message if no row is selected
            showErrorAlert("No Row Selected", "Please select a row to modify.");
        }
    }




    private void openEditDialog(Etudiant etudiant) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Etudiant");
        TextField cinField = new TextField(etudiant.getCin());
        TextField nomField = new TextField(etudiant.getNom());
        TextField prenomField = new TextField(etudiant.getPrenom());
        TextField mailField = new TextField(etudiant.getMail());
        TextField motpasseField = new TextField(etudiant.getMotpasse());
        TextField niveauField = new TextField(etudiant.getNiveau());

        dialog.getDialogPane().setContent(new VBox(
                new Label("CIN:"), cinField,
                new Label("Nom:"), nomField,
                new Label("Prenom:"), prenomField,
                new Label("Mail:"), mailField,
                new Label("Mot de Passe:"), motpasseField,
                new Label("Niveau:"), niveauField
        ));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String modifiedCin = cinField.getText();
                String modifiedNom = nomField.getText();
                String modifiedPrenom = prenomField.getText();
                String modifiedMail = mailField.getText();
                String modifiedMotpasse = motpasseField.getText();
                String modifiedNiveau = niveauField.getText();


                etudiant.setCin(modifiedCin);
                etudiant.setNom(modifiedNom);
                etudiant.setPrenom(modifiedPrenom);
                etudiant.setMail(modifiedMail);
                etudiant.setMotpasse(modifiedMotpasse);
                etudiant.setNiveau(modifiedNiveau);

                studentTableView.refresh();

                updateEtudiantInDatabase(etudiant);
            }
            return null;
        });

        dialog.showAndWait();
    }
    private void updateEtudiantInDatabase(Etudiant etudiant) {
        try {
            databaseManager.modifierEtudiant(etudiant);
            System.out.println("Etudiant updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while updating the database.");
        }
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Etudiant selectedEtudiant = studentTableView.getSelectionModel().getSelectedItem();
        if (selectedEtudiant != null) {
            studentTableView.getItems().remove(selectedEtudiant);

            deleteEtudiantFromDatabase(selectedEtudiant);
        } else {
            showErrorAlert("No Row Selected", "Please select a row to delete.");
        }
    }


    private void deleteEtudiantFromDatabase(Etudiant etudiant) {
        try {
            databaseManager.deleteEtudiant(etudiant);
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while deleting the record from the database.");
        }
    }


 @FXML
 private void handleCreerButtonAction(ActionEvent event) {
     // Create and configure the dialog
     Dialog<ButtonType> dialog = new Dialog<>();
     dialog.setTitle("Créer Étudiant");

     TextField cinField = new TextField();
     TextField nomField = new TextField();
     TextField prenomField = new TextField();
     TextField mailField = new TextField();
     TextField motpasseField = new TextField();
     TextField niveauField = new TextField();

     dialog.getDialogPane().setContent(new VBox(
             new Label("CIN:"), cinField,
             new Label("Nom:"), nomField,
             new Label("Prénom:"), prenomField,
             new Label("Mail:"), mailField,
             new Label("Mot de Passe:"), motpasseField,
             new Label("Niveau:"), niveauField
     ));

     dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

     dialog.setResultConverter(buttonType -> {
         if (buttonType == ButtonType.OK) {
             String cin = cinField.getText();
             String nom = nomField.getText();
             String prenom = prenomField.getText();
             String mail = mailField.getText();
             String motpasse = motpasseField.getText();
             String niveau = niveauField.getText();


             Etudiant newEtudiant = new Etudiant(cin, nom, prenom, mail, motpasse, niveau);

             studentTableView.getItems().add(newEtudiant);

             try {
                 databaseManager.CreerEtudiant(newEtudiant);
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         }
         return null;
     });

     dialog.showAndWait();
 }

}

