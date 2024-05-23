package com.example.quizz;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Groupe {
    private IntegerProperty idGroupe;
    private StringProperty nomGroupe;


    public Groupe(Integer idGroupe, String nomGroupe) {
        this.idGroupe = new SimpleIntegerProperty(idGroupe);
        this.nomGroupe = new SimpleStringProperty(nomGroupe);
    }

    // Getter and setter for cin
    public Integer getidGroupe() {
        return idGroupe.get();
    }

    public void setidGroupe(Integer idGroupe) {
        this.idGroupe.set(idGroupe);
    }
    public String getnomGroupe() {
        return nomGroupe.get();
    }

    public void setnomGroupe(String nomGroupe) {
        this.nomGroupe.set(nomGroupe);
    }


    // Getter for  property
    public IntegerProperty idGroupeProperty() {
        return idGroupe;
    }
    public StringProperty nomGroupeProperty() {
        return nomGroupe;
    }

}


