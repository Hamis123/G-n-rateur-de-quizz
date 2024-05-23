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


public class gererCoursController {
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
    private TableView<Cours> coursTableView;
    @FXML
    private TableColumn<Cours, Integer> idCoursColumn;
    @FXML
    private TableColumn<Cours, String> nomCoursColumn;
    @FXML
    private TableColumn<Cours, String> niveauColumn;

    private final databaseManager databaseManager = new databaseManager();
    @FXML
    private void initialize() {

        idCoursColumn.setCellValueFactory(cellData -> cellData.getValue().idCoursProperty().asObject());
        nomCoursColumn.setCellValueFactory(cellData -> cellData.getValue().nomCoursProperty());
        niveauColumn.setCellValueFactory(cellData -> cellData.getValue().niveauProperty());

        try {
            coursTableView.setItems(databaseManager.getTousCours());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreerButtonAction(ActionEvent event) {

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Créer Cours");


            TextField idCoursField = new TextField();
            TextField nomCoursField = new TextField();
            TextField niveauField = new TextField();


            dialog.getDialogPane().setContent(new VBox(
                    new Label("idCours:"), idCoursField,
                    new Label("nomCours:"), nomCoursField,
                    new Label("Niveau:"), niveauField
            ));


            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.setResultConverter(buttonType -> {
                if (buttonType == ButtonType.OK) {

                    Integer idCours = Integer.valueOf(idCoursField.getText());
                    String nomCours = nomCoursField.getText();
                    String niveau = niveauField.getText();

                    Cours newCours = new Cours(idCours, nomCours, niveau);

                    coursTableView.getItems().add(newCours);

                    try {
                        databaseManager.CreerCours(newCours);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                return null;
            });

            dialog.showAndWait();
        }



//**************** SUPPRIMER UN COURS *************************//
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Cours selectedCours = coursTableView.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            coursTableView.getItems().remove(selectedCours);

            deleteCoursFromDatabase(selectedCours);
        } else {
            showErrorAlert("No Row Selected", "Please select a row to delete.");
        }
    }


    private void deleteCoursFromDatabase(Cours cours) {
        try {
            databaseManager.deleteCours(cours);
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while deleting the record from the database.");
        }
    }


    //*************MODIFIER UN COURS**************************

    @FXML
    private void handleModifyButtonAction(ActionEvent event) {
        Cours selectedCours = coursTableView.getSelectionModel().getSelectedItem();
        if (selectedCours != null) {
            openEditDialog(selectedCours);

            coursTableView.refresh();
        } else {
            showErrorAlert("No Row Selected", "Please select a row to modify.");
        }
    }


    private void openEditDialog(Cours cours) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Cours");
        TextField idCoursField = new TextField(String.valueOf(cours.getidCours()));
        TextField nomCoursField = new TextField(cours.getnomCours());
        TextField niveauField = new TextField(cours.getNiveau());

        dialog.getDialogPane().setContent(new VBox(
                new Label("IdCours:"), idCoursField,
                new Label("NomCours:"), nomCoursField,
                new Label("Niveau:"), niveauField
        ));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                Integer modifiedidCours = Integer.valueOf(idCoursField.getText());
                String modifiednomCours = nomCoursField.getText();
                String modifiedNiveau = niveauField.getText();


                cours.setidCours(modifiedidCours);
                cours.setnomCours(modifiednomCours);
                cours.setNiveau(modifiedNiveau);


                coursTableView.refresh();

                updateCoursInDatabase(cours);
            }
            return null;
        });

        dialog.showAndWait();
    }
    private void updateCoursInDatabase(Cours cours) {
        try {
            databaseManager.modifierCours(cours);
            System.out.println("Cours updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while updating the database.");
        }
    }

}
