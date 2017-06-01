package com.example.henryf.pryeasypaybar;

import android.graphics.Bitmap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 29/05/2017.
 */

public class Recargas {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public static final ArrayList<Recargas> lista_recargas = new ArrayList<Recargas>();

    private String nombre_proveedor;
    private String saldo_cliente;
    private String bar_proveedor;
    private String imagen;

    public Recargas(String nombre_proveedor, String saldo_cliente, String bar_proveedor, String imagen) {
        this.nombre_proveedor = nombre_proveedor;
        this.saldo_cliente = saldo_cliente;
        this.bar_proveedor = bar_proveedor;
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public String getSaldo_cliente() {
        return saldo_cliente;
    }

    public Recargas() {
    }

    public String getBar_proveedor() {
        return bar_proveedor;
    }









}
