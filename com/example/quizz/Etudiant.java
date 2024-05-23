package com.example.quizz;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Etudiant {
    private StringProperty cin;
    private StringProperty nom;
    private StringProperty prenom;
    private StringProperty mail;
    private StringProperty motpasse;
    private StringProperty niveau;

    public Etudiant(String cin, String nom, String prenom, String mail, String motpasse, String niveau) {
        this.cin = new SimpleStringProperty(cin);
        this.nom = new SimpleStringProperty(nom);
        this.prenom =new SimpleStringProperty(prenom);
        this.mail = new SimpleStringProperty(mail);
        this.motpasse =new SimpleStringProperty(motpasse);
        this.niveau =new SimpleStringProperty(niveau);
    }


    // Getter and setter for cin
    public String getCin() {
        return cin.get();
    }

    public void setCin(String cin) {
        this.cin.set(cin);
    }
    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }
    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }
    public String getMail() {
        return mail.get();
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }
    public String getMotpasse() {
        return motpasse.get();
    }

    public void setMotpasse(String motpasse) {
        this.motpasse.set(motpasse);
    }
    public String getNiveau() {
        return niveau.get();
    }

    public void setNiveau(String niveau) {
        this.niveau.set(niveau);
    }

    // Getter for cin property
    public StringProperty cinProperty() {
        return cin;
    }
    public StringProperty nomProperty() {
        return nom;
    }
    public StringProperty prenomProperty() {
        return prenom;
    }
    public StringProperty mailProperty() {
        return mail;
    }
    public StringProperty motpasseProperty() {
        return motpasse;
    }
    public StringProperty niveauProperty() {
        return niveau;
    }


}