package com.example.quizz;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EspaceAdminController {
    public void deconnecterOnClick(ActionEvent actionEvent){
        Stage stage =(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("connexion.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Authentification !");
            newStage.show();

        }catch (IOException e){
            e.getMessage();
        }

    }

    public void GererCoursOnAction(ActionEvent actionEvent){
        Stage stage =(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gererCours.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Gestion des Cours ");
            newStage.show();

        }catch (IOException e){
            e.getMessage();
        }

    }


    public void GererGroupeOnAction(ActionEvent actionEvent){
        Stage stage =(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gererGroupe.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Gestion des Groupes ");
            newStage.show();

        }catch (IOException e){
            e.getMessage();
        }

    }

    public void GererEnseignantOnAction(ActionEvent actionEvent){
        Stage stage =(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gererEnseignant.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Gestion des Enseignants ");
            newStage.show();

        }catch (IOException e){
            e.getMessage();
        }

    }
    public void GererEtudiantOnAction(ActionEvent actionEvent){
        Stage stage =(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gererEtudiant.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Gestion des Etudinants");
            newStage.show();

        }catch (IOException e){
            e.getMessage();
        }

    }
}
