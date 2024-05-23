package com.example.quizz;

import java.time.LocalDateTime;

public class Quizz {
    private int idQuiz;
    private String matiere;
    private String niveau;
    private int nbQuestion;
    private LocalDateTime CREATED_AT;

    public Quizz (int idQuiz, String matiere, String niveau , int nbQuestion) {
        this.idQuiz=idQuiz;
        this.matiere = matiere;
        this.niveau = niveau;
        this.nbQuestion = nbQuestion;
        //this.CREATED_AT = CREATED_AT;
    }
    public Quizz(int idQuiz) {
        this.idQuiz= idQuiz;
    }


    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    public int getNbQuestion() {
        return nbQuestion;
    }

    public void setNbQuestion(int nbQuestion) {
        this.nbQuestion = nbQuestion;
    }

}
