package com.example.henryf.pryeasypaybar.Servicios;

import com.example.henryf.pryeasypaybar.MenuProveedor.MenuProveedor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 25/06/2017.
 */

public class CategoriaProveedor implements Serializable{

    private String descripcion;
    private String nombre;

    private ArrayList<ProductoProveedor> productoProveedores = new ArrayList<ProductoProveedor>();


    public CategoriaProveedor(){
    }

    public CategoriaProveedor(String descripcion, String nombreCategoria, ArrayList<ProductoProveedor> productoProveedores) {
        this.descripcion = descripcion;
        this.nombre= nombreCategoria;
        this.productoProveedores = productoProveedores;
    }

    public void setProductoProveedores(ArrayList<ProductoProveedor> productoProveedores) {
        this.productoProveedores = productoProveedores;
    }

    public ArrayList<ProductoProveedor> getProductoProveedores() {
        return productoProveedores;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombre = nombreCategoria;
    }
}
