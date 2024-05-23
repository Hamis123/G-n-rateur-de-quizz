package com.example.quizz;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EspaceEtudiantController {
    public void consulterOnClick(ActionEvent actionEvent){
        Stage stage =(Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GererQuiz.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Quizs disponibles ");
            newStage.show();

        }catch (IOException e){
            e.getMessage();
        }

    }
}
