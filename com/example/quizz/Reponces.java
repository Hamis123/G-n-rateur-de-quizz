package com.example.quizz;

public class Reponces {
    private int id;
    private String reponces;
    private String etat;
    private int idReponces;

    public Reponces (int idReponces, String reponces, String etat) {
        this.idReponces=idReponces;
        this.reponces = reponces;
        this.etat = etat;
    }
    public Reponces(int id) {
        this.id = id;
    }


        public int getIdReponces() {
        return idReponces;
    }

    public void setIdReponces(int idReponces) {
        this.idReponces = idReponces;
    }

    public String getReponces() {
        return reponces;
    }

    public void setReponces(String reponces) {
        this.reponces = reponces;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
    public String getId() {
        return etat;
    }

    public void setId(int id) {
        this.id = id;
    }

}
