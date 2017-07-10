package com.example.henryf.pryeasypaybar.DetalleProducto;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.henryf.pryeasypaybar.R;
import com.example.henryf.pryeasypaybar.Servicios.ComentarioProducto;
import com.example.henryf.pryeasypaybar.Servicios.ProductoProveedor;
import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Detalleproducto extends AppCompatActivity {

    private ImageView imgProducto;
    private TextView nombreProducto;
    private Button btn_comentar;
    private EditText txt_comentario;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private static ArrayList<ComentarioProducto> comentarioProductos;

    public static ArrayList<ComentarioProducto> getComentarioProductos() {
        return comentarioProductos;
    }

    public static void setComentarioProductos(ArrayList<ComentarioProducto> comentarioProductos) {
        Detalleproducto.comentarioProductos = comentarioProductos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalleproducto);
        imgProducto = (ImageView) findViewById(R.id.imgProducto);
        nombreProducto = (TextView) findViewById(R.id.nombreProducto);
        btn_comentar=(Button) findViewById(R.id.btn_comentar);
        txt_comentario=(EditText) findViewById(R.id.txt_comentario);
        Intent intent = getIntent();
        final ProductoProveedor producto = (ProductoProveedor) intent.getExtras().getSerializable("producto");
        final String keyCategoria = (String) intent.getExtras().getSerializable("keyCategoria");
        getListaComentarios(producto.getUidproveedor().toString(),producto.getKey(), keyCategoria);
        Glide.with( this)
                .load(producto.getImagenURL().toString())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        nombreProducto.setText(producto.getNombre().toString());

                        return false;
                    }
                })
                .into(imgProducto);

        btn_comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                producto.Comentar(txt_comentario.getText().toString(),producto.getUidproveedor().toString(),producto.getKey(), keyCategoria);
                txt_comentario.setText(null);
            }
        });
    }
    public void getListaComentarios(final String uid_Proveedor, final String uid_Producto, final String uid_categoria){
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        //Consulta todos los comentarios
        final ArrayList<ComentarioProducto> lista_coment= new ArrayList<ComentarioProducto>();
        mFirebaseDatabase.child("proveedor").child(uid_Proveedor).child("categoria").child(uid_categoria).child("producto").child(uid_Producto).child("comentario").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot comentario : dataSnapshot.getChildren()) {
                    System.out.print("Cuerpo"+comentario.child("cuerpo").getValue().toString());
                    lista_coment.add(new ComentarioProducto(
                            comentario.child("cuerpo").getValue().toString(),
                            comentario.child("fecha").getValue().toString(),
                            comentario.child("usuario").getValue().toString()
                    ));
                }
                setComentarioProductos(lista_coment);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

