package com.example.henryf.pryeasypaybar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Diego Claudio on 27/02/2017.
 */

public class Cliente {
    public boolean admin;
    public String codigoQR;
    public boolean estado;
    public String fecha_Afiliacion;
    public String nombre;
    public boolean proveedor;



    public Cliente() {

    }

    public Cliente(String codigoQR, String nombre) {
        this.admin = false;
        this.codigoQR = codigoQR;
        this.estado = true;
        Calendar fecha = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        this.fecha_Afiliacion = formato.format(fecha.getTime());
        this.nombre = nombre;
        this.proveedor = false;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFecha_Afiliacion() {
        return fecha_Afiliacion;
    }

    public void setFecha_Afiliacion(String fecha_Afiliacion) {
        this.fecha_Afiliacion = fecha_Afiliacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isProveedor() {
        return proveedor;
    }

    public void setProveedor(boolean proveedor) {
        this.proveedor = proveedor;
    }
}
