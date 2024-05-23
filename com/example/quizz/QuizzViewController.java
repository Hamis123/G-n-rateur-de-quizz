package com.example.quizz;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizzViewController {


    @FXML
    private VBox questionsVBox;

    private List<Integer> studentResponses;
    private final databaseManager databaseManager = new databaseManager();

    public void initialize(int idQuiz) {
        questionsVBox.getChildren().add(new Label("-----------------------------------------"));

            System.out.println("Initializing with quizId: " + idQuiz);
            if (idQuiz <= 0) {
                System.err.println("Invalid quizId: " + idQuiz);
                showErrorAlert("Invalid Quiz ID", "The provided quiz ID is invalid.");
                return;
            }

                try {
            List<Question> quizQuestions = com.example.quizz.databaseManager.getQuestionsForQuizz(idQuiz);
            System.out.println("Retrieved questions: " + quizQuestions.size());

            List<Reponces> correctAnswers = com.example.quizz.databaseManager.getReponsesForQuestions(quizQuestions);
            System.out.println("Retrieved correct answers: " + correctAnswers.size());

            studentResponses = new ArrayList<>(Collections.nCopies(quizQuestions.size() * 3, 0)); // Initialize with default values

            questionsVBox.getChildren().clear(); // Clear any existing content in the VBox

            for (int i = 0; i < quizQuestions.size(); i++) {
                Question question = quizQuestions.get(i);
                System.out.println("Question " + (i + 1) + ": " + question.getEnonce());

                Label questionLabel = new Label("Question " + (i + 1) + ": " + question.getEnonce());
                questionsVBox.getChildren().add(questionLabel); // Add question label

                List<Reponces> answers = correctAnswers.stream()
                        .filter(answer -> answer.getIdReponces() == question.getId())
                        .toList();

                System.out.println("Answers for Question " + (i + 1) + ": " + answers.size());

                CheckBox[] checkBoxes = new CheckBox[answers.size()];
                for (int j = 0; j < answers.size(); j++) {
                    Reponces answer = answers.get(j);
                    checkBoxes[j] = new CheckBox((j + 1) + ". " + answer.getReponces());
                    questionsVBox.getChildren().add(checkBoxes[j]);
                    setCheckBoxListener(checkBoxes[j], i, j);
                }

                questionsVBox.getChildren().add(new Label("-----------------------------------------"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while retrieving quiz data.");
        }
    }


    private void setCheckBoxListener(CheckBox checkBox, int questionIndex, int responseIndex) {
        checkBox.setOnAction(event -> {
            int index = questionIndex * 3 + responseIndex;
            studentResponses.set(index, checkBox.isSelected() ? 1 : 0);
        });
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    }


