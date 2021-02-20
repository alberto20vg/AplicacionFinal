package com.example.aplicacionfinal;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlatoPureba  {

    private String nombre;
    private String img;
    private ArrayList<String> lista;

    //DocumentReference  poner esto si le paso la referencia del docuemtn
    public PlatoPureba(){

    }

    public PlatoPureba(String titulo, String img, ArrayList<String> lista){
        this.nombre = nombre;
        this.img = img;
        this.lista = lista;
    }

    public ArrayList<String> getLista() {
        return lista;
    }

    public void setLista(ArrayList<String> lista) {
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
