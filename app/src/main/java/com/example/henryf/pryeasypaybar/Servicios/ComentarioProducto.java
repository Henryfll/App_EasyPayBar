package com.example.henryf.pryeasypaybar.Servicios;

import java.util.Date;

/**
 * Created by Fabian on 08/07/2017.
 */

public class ComentarioProducto {
    private String cuerpo;
    private String fecha;
    private String usuario;

    public ComentarioProducto(String cuerpo, String fecha, String usuario) {
        this.cuerpo = cuerpo;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
