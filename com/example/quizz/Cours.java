package com.example.quizz;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Cours {
    private IntegerProperty idCours;
    private StringProperty nomCours;
    private StringProperty niveau;


    public Cours(Integer idCours, String nomCours, String niveau) {
        this.idCours = new SimpleIntegerProperty(idCours);
        this.nomCours = new SimpleStringProperty(nomCours);
        this.niveau =new SimpleStringProperty(niveau);
    }

    // Getter and setter for cin
    public Integer getidCours() {
        return idCours.get();
    }

    public void setidCours(Integer idCours) {
        this.idCours.set(idCours);
    }
    public String getnomCours() {
        return nomCours.get();
    }

    public void setnomCours(String nomCours) {
        this.nomCours.set(nomCours);
    }
    public String getNiveau() {
        return niveau.get();
    }

    public void setNiveau(String niveau) {
        this.niveau.set(niveau);
    }



    // Getter for  property
    public IntegerProperty idCoursProperty() {
        return idCours;
    }
    public StringProperty nomCoursProperty() {
        return nomCours;
    }
    public StringProperty niveauProperty() {
        return niveau;
    }



}


