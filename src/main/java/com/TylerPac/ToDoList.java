package com.TylerPac;

import jakarta.persistence.*;

@Entity
public class ToDoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
    private int TDL_ID;

    private String TDL_NAME;
    private boolean TDL_DONE;

    public int getTDL_ID() {
        return TDL_ID;
    }

    public String getTDL_NAME() {
        return TDL_NAME;
    }

    public boolean isTDL_DONE() {
        return TDL_DONE;
    }

    public void setTDL_DONE(boolean TDL_DONE) {
        this.TDL_DONE = TDL_DONE;
    }

    public void setTDL_NAME(String TDL_NAME) {
        this.TDL_NAME = TDL_NAME;
    }

    @Override
    public String toString() {
        return "ToDoList{" +
                "TDL_ID=" + TDL_ID +
                ", TDL_NAME='" + TDL_NAME + '\'' +
                ", TDL_DONE=" + TDL_DONE +
                '}';
    }
}
