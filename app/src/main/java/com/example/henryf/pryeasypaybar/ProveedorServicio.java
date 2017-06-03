package com.example.henryf.pryeasypaybar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Fabian on 03/06/2017.
 */

public class ProveedorServicio {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth firebaseAuth;



    private String nombre_proveedor;
    private String saldo_cliente;
    private String bar_proveedor;
    private String imagen;
    private String uid_Proveedor;


    public ProveedorServicio(String nombre_proveedor, String saldo_cliente, String bar_proveedor, String imagen, String uid) {
        this.nombre_proveedor = nombre_proveedor;
        this.saldo_cliente = saldo_cliente;
        this.bar_proveedor = bar_proveedor;
        this.imagen = imagen;
        this.uid_Proveedor = uid;
    }

    public String getUid_Proveedor() {
        return uid_Proveedor;
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

    public ProveedorServicio() {
    }

    public String getBar_proveedor() {
        return bar_proveedor;
    }


    public void Afiliarse(String proveedorId){//proveedorId es el UID del proveedor en firebase
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("proveedor");
        try {
            Afiliado afiliado = new Afiliado(user.getUid(), user.getDisplayName());//instancia la clase afiliado con datos del usuario
            mFirebaseDatabase.child(proveedorId).child("afiliados").child(user.getUid()).setValue(afiliado);//registra en firebase
        }catch (Exception e){
            System.out.println("problema: "+e.getMessage());
        }
    }





}
