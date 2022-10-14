package edu.uoc.notestop.Models;

public class CheckListModel {

    public CheckListModel(String item, String booleano) {
        this.item = item;
        this.booleano = booleano;
    }

    String item;
    String booleano;

    @Override
    public String toString() {
        return "*  "+ item  +
                "   -----   " + booleano +'\n';
    }


    public CheckListModel() {
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getBooleano() {
        return booleano;
    }

    public void setBooleano(String booleano) {
        this.booleano = booleano;
    }
}
