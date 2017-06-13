package com.example.henryf.pryeasypaybar;

/**
 * Created by Fabian on 05/06/2017.
 */

public class Recarga {
    private String fecha_Recarga;
    private  String valor;

    public Recarga() {

    }

    public void setFecha_Recarga(String fecha_recarga) {
        this.fecha_Recarga = fecha_recarga;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Recarga(String fecha_recarga, String valor) {
        this.fecha_Recarga = fecha_recarga;
        this.valor = valor;
    }

    public String getFecha_Recarga() {
        return fecha_Recarga;
    }

    public String getValor() {
        return valor;
    }
}
