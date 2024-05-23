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


public class gererEnseignantController {
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void deconnecterOnClick(ActionEvent actionEvent){
        Stage stage =(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EspaceAdmin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Espace Administrateur");
            newStage.show();

        }catch (IOException e){
            e.getMessage();
        }

    }


    @FXML
    private TableView<Enseignant> enseignantTableView;
    @FXML
    private TableColumn<Enseignant, String> cinColumn;
    @FXML
    private TableColumn<Enseignant, String> nomColumn;
    @FXML
    private TableColumn<Enseignant, String> prenomColumn;
    @FXML
    private TableColumn<Enseignant, String> emailColumn;
    @FXML
    private TableColumn<Enseignant, String> passwordColumn;

    private final databaseManager databaseManager = new databaseManager();

    @FXML
    private void initialize() {
        cinColumn.setCellValueFactory(cellData -> cellData.getValue().cinProperty());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().mailProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().motpasseProperty());

        try {
            enseignantTableView.setItems(databaseManager.getTousEnseigant());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleModifyButtonAction(ActionEvent event) {
        Enseignant selectedEnseignant = enseignantTableView.getSelectionModel().getSelectedItem();
        if (selectedEnseignant != null) {
            openEditDialog(selectedEnseignant);

            enseignantTableView.refresh();
        } else {
            showErrorAlert("No Row Selected", "Please select a row to modify.");
        }
    }


    private void openEditDialog(Enseignant enseignant) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Enseignant");
        TextField cinField = new TextField(enseignant.getCin());
        TextField nomField = new TextField(enseignant.getNom());
        TextField prenomField = new TextField(enseignant.getPrenom());
        TextField mailField = new TextField(enseignant.getMail());
        TextField motpasseField = new TextField(enseignant.getMotpasse());

        dialog.getDialogPane().setContent(new VBox(
                new Label("CIN:"), cinField,
                new Label("Nom:"), nomField,
                new Label("Prenom:"), prenomField,
                new Label("Mail:"), mailField,
                new Label("Mot de Passe:"), motpasseField
        ));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String modifiedCin = cinField.getText();
                String modifiedNom = nomField.getText();
                String modifiedPrenom = prenomField.getText();
                String modifiedMail = mailField.getText();
                String modifiedMotpasse = motpasseField.getText();


                enseignant.setCin(modifiedCin);
                enseignant.setNom(modifiedNom);
                enseignant.setPrenom(modifiedPrenom);
                enseignant.setMail(modifiedMail);
                enseignant.setMotpasse(modifiedMotpasse);


                enseignantTableView.refresh();

                updateEnseignantInDatabase(enseignant);
            }
            return null;
        });

        dialog.showAndWait();
    }
    private void updateEnseignantInDatabase(Enseignant enseignant) {
        try {
            databaseManager.modifierEnseignant(enseignant);
            System.out.println("Enseignant updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while updating the database.");
        }
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Enseignant selectedEnseignant = enseignantTableView.getSelectionModel().getSelectedItem();
        if (selectedEnseignant != null) {
            enseignantTableView.getItems().remove(selectedEnseignant);

            deleteEnseignantFromDatabase(selectedEnseignant);
        } else {
            showErrorAlert("No Row Selected", "Please select a row to delete.");
        }
    }
    private void deleteEnseignantFromDatabase(Enseignant enseignant) {
        try {
            databaseManager.deleteEnseignant(enseignant);
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while deleting the record from the database.");
        }
    }


    @FXML
    private void handleCreerButtonAction(ActionEvent event) {
        // Create and configure the dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Créer Enseignant");

        // Create input fields for student details
        TextField cinField = new TextField();
        TextField nomField = new TextField();
        TextField prenomField = new TextField();
        TextField mailField = new TextField();
        TextField motpasseField = new TextField();

        dialog.getDialogPane().setContent(new VBox(
                new Label("CIN:"), cinField,
                new Label("Nom:"), nomField,
                new Label("Prénom:"), prenomField,
                new Label("Mail:"), mailField,
                new Label("Mot de Passe:"), motpasseField
        ));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String cin = cinField.getText();
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String mail = mailField.getText();
                String motpasse = motpasseField.getText();


                Enseignant newEnseignant = new Enseignant(cin, nom, prenom, mail, motpasse);

                enseignantTableView.getItems().add(newEnseignant);

                try {
                    databaseManager.CreerEnseignant(newEnseignant);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }


}
