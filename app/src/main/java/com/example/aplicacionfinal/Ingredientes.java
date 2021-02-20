package com.example.aplicacionfinal;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;

public class Ingredientes implements Serializable {

    private String nombre;
    private String img;

    public Ingredientes(){

    }

    public Ingredientes(String titulo, String img){
        this.nombre = nombre;
        this.img = img;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
