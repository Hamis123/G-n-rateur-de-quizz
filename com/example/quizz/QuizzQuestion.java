package com.example.quizz;

public class QuizzQuestion {

    private int idQUIZ;
    private int id;

    public int getIdQUIZ() {
        return idQUIZ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdQUIZ(int idQUIZ) {
        this.idQUIZ = idQUIZ;
    }

    public QuizzQuestion (int id, int idQUIZ) {
        this.id=id;
        this.idQUIZ = idQUIZ;

    }
}
