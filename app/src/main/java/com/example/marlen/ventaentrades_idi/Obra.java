package com.example.marlen.ventaentrades_idi;

/**
 * Created by marlen on 19/05/2016.
 */
public class Obra {
    //en principi només utilitzaré els paràmetres que vull mostrar al recycler
    private String titol;
    private int preu;


    //constructora amb paràmetres concrets
    Obra(String titol, int preu){
        this.titol = titol;
        this.preu = preu;
        //this.image = image;
    }

    //constructora buida
    Obra(){}

    //getters i setters
    public String getTitol(){ return titol; }
    public void setTitol(String titol){ this.titol = titol; }

    public Integer getPreu(){ return preu; }
    public void setPreu(int preu){ this.preu = preu; }
}
