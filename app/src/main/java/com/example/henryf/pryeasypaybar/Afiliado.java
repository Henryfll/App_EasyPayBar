package com.example.henryf.pryeasypaybar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Diego Claudio on 11/05/2017.
 */

public class Afiliado {
    public String fechaAfiliacion;
    public String nombre;
    public int saldo;
    public String key;

    public Afiliado() {

    }

    public Afiliado(String codigoQR, String nombre) {

        this.key = codigoQR;
        Calendar fecha = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        this.fechaAfiliacion = formato.format(fecha.getTime());
        this.nombre = nombre;
        this.saldo = 0;
    }

}
