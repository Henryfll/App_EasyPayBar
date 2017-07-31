package com.example.henryf.pryeasypaybar.Servicios;

import android.net.Uri;
import android.widget.Toast;

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
import java.util.Calendar;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Fabian on 25/06/2017.
 */

public class ProductoProveedor implements Serializable {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private String nombre;
    private String precio;
    private String imagen;
    private String veces;
    private String imagenURL;
    private String key;
    private String uidproveedor;
    private boolean sePuedeComentar;
    private float calificacion;


    public ProductoProveedor(){
    }

    public ProductoProveedor(String nombre, String precio, String imagen, String veces, String imagenURL, String key, String uidproveedor, boolean sePuedeComentar, float calificacion) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.veces = veces;
        this.imagenURL = imagenURL;
        this.key=key;
        this.uidproveedor=uidproveedor;
        this.sePuedeComentar = sePuedeComentar;
        this.calificacion = calificacion;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public boolean isSePuedeComentar() {
        return sePuedeComentar;
    }

    public void setSePuedeComentar(boolean sePuedeComentar) {
        this.sePuedeComentar = sePuedeComentar;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUidproveedor() {
        return uidproveedor;
    }

    public void setUidproveedor(String uidproveedor) {
        this.uidproveedor = uidproveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getVeces() {
        return veces;
    }

    public void setVeces(String veces) {
        this.veces = veces;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }


    public void Comentar(final String cuerpo, final String uid_Proveedor, final String uid_Producto, final String uid_categoria , String nombreUsuario , String urlImagen){
        final Calendar fecha=Calendar.getInstance();
        final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("proveedor").child(uid_Proveedor).child("categoria").child(uid_categoria).child("producto").child(uid_Producto).child("comentario").push().setValue(new ComentarioProducto(cuerpo,formato.format(fecha.getTime()),user.getUid(),nombreUsuario,urlImagen.toString()));

    }
    
    public void Calificar(final float calificacion, final String uid_Proveedor, final String uid_Producto, final String uid_Categoria){
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("proveedor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(uid_Proveedor).child("afiliados").child(user.getUid()).exists()) {
                    mFirebaseDatabase.child("proveedor").child(uid_Proveedor).child("categoria").child(uid_Categoria).child("producto").child(uid_Producto).child("calificacion").child(user.getUid()).child("valoracion").setValue(calificacion);
                    Toast mensaje = Toast.makeText(getApplicationContext(), "El producto fue calificado", Toast.LENGTH_SHORT);
                    mensaje.show();
                }else {
                    Toast mensaje = Toast.makeText(getApplicationContext(), "Debe estar afiliado a este Proveedor", Toast.LENGTH_SHORT);
                    mensaje.show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public float ConsultarCalificacion(final String uid_Proveedor, final String uid_Producto, final String uid_Categoria){
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        //final float calificacion;
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("proveedor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot referencia= dataSnapshot.child(uid_Proveedor).child("categoria").
                        child(uid_Categoria).child("producto").child(uid_Producto).child("calificacion").
                        child(user.getUid()).child("valoracion");
                final float calificacion;
                if (referencia.exists())
                    calificacion = Float.parseFloat(referencia.getValue().toString());
                else
                    calificacion = 0;



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return calificacion;
    }
}
