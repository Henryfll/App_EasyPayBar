package com.example.henryf.pryeasypaybar;

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
class Recarga{
    private String fecha;
    private String saldo;

    public Recarga(String fecha, String saldo) {
        this.fecha = fecha;
        this.saldo = saldo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
public class Recargas {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public static final ArrayList<Recargas> lista_recargas = new ArrayList<Recargas>();

    private String nombre_proveedor;
    private String saldo_cliente;

    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public String getSaldo_cliente() {
        return saldo_cliente;
    }

    public Recargas() {
    }


    public Recargas(String nombre_proveedor, String saldo_cliente) {
        this.nombre_proveedor = nombre_proveedor;
        this.saldo_cliente = saldo_cliente;

    }







}
