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


public class gererGroupeController {

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
            newStage.setTitle("Espace Admin");
            newStage.show();

        }catch (IOException e){
            e.getMessage();
        }

    }

    @FXML
    private TableView<Groupe> groupeTableView;
    @FXML
    private TableColumn<Groupe, Integer> idGroupeColumn;
    @FXML
    private TableColumn<Groupe, String> nomGroupeColumn;


    private final databaseManager databaseManager = new databaseManager();
    @FXML
    private void initialize() {

        idGroupeColumn.setCellValueFactory(cellData -> cellData.getValue().idGroupeProperty().asObject());
        nomGroupeColumn.setCellValueFactory(cellData -> cellData.getValue().nomGroupeProperty());

        try {
            groupeTableView.setItems(databaseManager.getTousGroupes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreerButtonAction(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Créer Groupe");


        TextField idGroupeField = new TextField();
        TextField nomGroupeField = new TextField();

        dialog.getDialogPane().setContent(new VBox(
                new Label("idGroupe:"), idGroupeField,
                new Label("nomGroupe:"), nomGroupeField
        ));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                Integer idGroupe = Integer.valueOf(idGroupeField.getText());
                String nomGroupe = nomGroupeField.getText();


                Groupe newGroupe = new Groupe(idGroupe, nomGroupe);

                groupeTableView.getItems().add(newGroupe);

                try {
                    databaseManager.CreerGroupe(newGroupe);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }
    //**************** SUPPRIMER UN GROUPE *************************//
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Groupe selectedGroupe = groupeTableView.getSelectionModel().getSelectedItem();
        if (selectedGroupe != null) {
            groupeTableView.getItems().remove(selectedGroupe);

            deleteGroupeFromDatabase(selectedGroupe);
        } else {
            showErrorAlert("No Row Selected", "Please select a row to delete.");
        }
    }


    private void deleteGroupeFromDatabase(Groupe groupe) {
        try {
            databaseManager.deleteGroupe(groupe);
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while deleting the record from the database.");
        }
    }


    //*************MODIFIER UN GROUPE**************************

    @FXML
    private void handleModifyButtonAction(ActionEvent event) {
        Groupe selectedGroupe = groupeTableView.getSelectionModel().getSelectedItem();
        if (selectedGroupe != null) {
            openEditDialog(selectedGroupe);

            groupeTableView.refresh();
        } else {
            showErrorAlert("No Row Selected", "Please select a row to modify.");
        }
    }


    private void openEditDialog(Groupe groupe) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Groupe");
        TextField idGroupeField = new TextField(String.valueOf(groupe.getidGroupe()));
        TextField nomGroupeField = new TextField(groupe.getnomGroupe());


        dialog.getDialogPane().setContent(new VBox(
                new Label("IdGroupe:"), idGroupeField,
                new Label("NomGroupe:"), nomGroupeField

        ));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                Integer modifiedidGroupe = Integer.valueOf(idGroupeField.getText());
                String modifiednomGroupe = nomGroupeField.getText();


                groupe.setidGroupe(modifiedidGroupe);
                groupe.setnomGroupe(modifiednomGroupe);

               groupeTableView.refresh();

                updateCoursInDatabase(groupe);
            }
            return null;
        });

        dialog.showAndWait();
    }
    private void updateCoursInDatabase(Groupe groupe) {
        try {

            databaseManager.modifierGroupe(groupe);
            System.out.println("Groupe updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while updating the database.");
        }
    }

}
