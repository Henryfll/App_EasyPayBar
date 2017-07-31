package com.example.henryf.pryeasypaybar.Servicios;

import java.util.ArrayList;

/**
 * Created by Diego Claudio on 29/07/2017.
 */

public class ProductoFavorito {
    private String categoriaId;
    private String productoId;
    private ArrayList<ProductoFavorito> productos;

    public ProductoFavorito() {

    }

    public ProductoFavorito(String categoriaId, String productoId) {
        this.categoriaId = categoriaId;
        this.productoId = productoId;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public ArrayList<ProductoFavorito> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoFavorito> productos) {
        this.productos = productos;
    }
}
