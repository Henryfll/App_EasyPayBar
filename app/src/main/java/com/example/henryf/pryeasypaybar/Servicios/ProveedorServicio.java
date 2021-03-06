package com.example.henryf.pryeasypaybar.Servicios;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.henryf.pryeasypaybar.Afiliado;
import com.example.henryf.pryeasypaybar.Cliente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Fabian on 03/06/2017.
 */

public class ProveedorServicio implements Serializable{

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private String nombre_proveedor;
    private String saldo_cliente;
    private String bar_proveedor;
    private String imagen;
    private String uid_Proveedor;
    private ArrayList<String> Lista_recargas;
    private boolean usuarioAfiliado;
    private String imagenURL;

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    private ArrayList<CategoriaProveedor> categoriaProveedors = new ArrayList<>();

    public void setCategoriaProveedors(ArrayList<CategoriaProveedor> categoriaProveedors) {
        this.categoriaProveedors = categoriaProveedors;
    }

    public ArrayList<CategoriaProveedor> getCategoriaProveedors() {
        return categoriaProveedors;
    }

    public boolean isUsuarioAfiliado() {
        return usuarioAfiliado;
    }

    public ArrayList<String> getLista_recargas() {
        return Lista_recargas;
    }


    public ProveedorServicio(String nombre_proveedor, String saldo_cliente, String bar_proveedor, String imagen, String uid, ArrayList<String> recargas, boolean afiliado, ArrayList<CategoriaProveedor> categoriaProveedors , String imagenURL) {
        this.nombre_proveedor = nombre_proveedor;
        this.saldo_cliente = saldo_cliente;
        this.bar_proveedor = bar_proveedor;
        this.imagen = imagen;
        this.uid_Proveedor = uid;
        this.Lista_recargas = recargas;
        this.usuarioAfiliado = afiliado;
        this.categoriaProveedors = categoriaProveedors;
        this.imagenURL = imagenURL;
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
