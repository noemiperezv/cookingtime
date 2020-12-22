package mx.edu.utng.cookingtime;

import java.io.Serializable;

public class Receta implements Serializable {

    private  int calorias;
    private String nombre;
    private String ingredientes;
    private String categoria;

    public Receta() {
    }


    public Receta(int calorias, String nombre, String ingredientes, String categoria) {
        this.calorias = calorias;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.categoria = categoria;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
