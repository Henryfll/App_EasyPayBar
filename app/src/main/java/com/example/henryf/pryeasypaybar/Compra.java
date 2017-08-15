package com.example.henryf.pryeasypaybar;

/**
 * Created by HenryF on 15/8/2017.
 */

public class Compra {
    private String fecha_Compra;
    private String total;

    public Compra() {
    }

    public Compra(String fecha_Compra, String total) {
        this.fecha_Compra = fecha_Compra;
        this.total = total;
    }

    public String getFecha_Compra() {
        return fecha_Compra;
    }

    public void setFecha_Compra(String fecha_Compra) {
        this.fecha_Compra = fecha_Compra;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}

