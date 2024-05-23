package com.example.quizz;

public class Question {
    private int id;
    private String enonce;
    private String niveau;
    private String cours;

    public Question (int id, String enonce, String niveau , String cours) {
        this.id=id;
        this.enonce = enonce;
        this.niveau = niveau;
        this.cours = cours;
    }
    public Question(int id) {
        this.id= id;
    }
    public Question(String enonce) {
        this.enonce= enonce;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    public String getCours() {
        return cours;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

}
