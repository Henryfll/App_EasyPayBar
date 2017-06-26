package com.example.henryf.pryeasypaybar.Servicios;

import java.io.Serializable;

/**
 * Created by Fabian on 25/06/2017.
 */

public class ProductoProveedor implements Serializable {
    private String nombre;
    private String precio;
    private String imagen;
    private String veces;
    private String imagenURL;

    public ProductoProveedor(){
    }

    public ProductoProveedor(String nombre, String precio, String imagen, String veces, String imagenURL) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.veces = veces;
        this.imagenURL = imagenURL;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getVeces() {
        return veces;
    }

    public void setVeces(String veces) {
        this.veces = veces;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }
}
