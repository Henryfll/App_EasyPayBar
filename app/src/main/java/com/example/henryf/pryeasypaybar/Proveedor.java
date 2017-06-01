package com.example.henryf.pryeasypaybar;

import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Diego Claudio on 04/05/2017.
 */

public class Proveedor {
    private String nombre;
    private String bar;
    private Bitmap imagen;
    private String uid;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth firebaseAuth;

    public Proveedor(){};

    public Proveedor(String nombre, String bar, Bitmap imagen, String uid) {
        this.nombre = nombre;
        this.bar = bar;
        this.imagen = imagen;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
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
