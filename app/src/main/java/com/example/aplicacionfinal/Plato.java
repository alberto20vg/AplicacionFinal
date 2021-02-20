package com.example.aplicacionfinal;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Plato implements Serializable {

    private String nombre;
    private String img;
    private ArrayList<DocumentReference> lista;

    public Plato(){

    }

    public Plato(String titulo, String img, ArrayList<DocumentReference> lista){
        this.nombre = nombre;
        this.img = img;
        this.lista = lista;
    }

    public ArrayList<DocumentReference> getLista() {
        return lista;
    }

    public void setLista(ArrayList<DocumentReference> lista) {
        this.lista = lista;
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
