package com.example.henryf.pryeasypaybar.DetalleProducto;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.henryf.pryeasypaybar.AdaptadorComentario;

import com.example.henryf.pryeasypaybar.R;
import com.example.henryf.pryeasypaybar.Servicios.ComentarioProducto;
import com.example.henryf.pryeasypaybar.Servicios.ProductoProveedor;
import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
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
    private FirebaseAuth firebaseAuth;
    public static ArrayList<ComentarioProducto> ComentarioProductos;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalleproducto);
        recyclerView = (RecyclerView) findViewById(R.id.comentarioRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imgProducto = (ImageView) findViewById(R.id.imgProducto);
        nombreProducto = (TextView) findViewById(R.id.nombreProducto);
        btn_comentar=(Button) findViewById(R.id.btn_comentar);
        txt_comentario=(EditText) findViewById(R.id.txt_comentario);
        Intent intent = getIntent();
        final ProductoProveedor producto = (ProductoProveedor) intent.getExtras().getSerializable("producto");
        final String keyCategoria = (String) intent.getExtras().getSerializable("keyCategoria");

        assert producto != null;
        Glide.with( this)
                .load(producto.getImagenURL())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        nombreProducto.setText(producto.getNombre());

                        return false;
                    }
                })
                .into(imgProducto);

        btn_comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                String facebookUserId="";
                for(UserInfo profile : user.getProviderData()) {
                    facebookUserId = profile.getUid();

                }


                String url = "https://graph.facebook.com/" + facebookUserId + "/picture?height=500";
                producto.Comentar(txt_comentario.getText().toString(), producto.getUidproveedor(),producto.getKey(), keyCategoria ,user.getDisplayName() , url);
                txt_comentario.setText(null);
            }
        });

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        //Consulta todos los comentarios
        assert keyCategoria != null;
        final ArrayList<ComentarioProducto> lista_coment= new ArrayList<ComentarioProducto>();
        mFirebaseDatabase.child("proveedor").child(producto.getUidproveedor()).child("categoria").child(keyCategoria).child("producto").child(producto.getKey()).child("comentario").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String nombre = "Anonimo";
                String imagenUrl ="http://www.ofrases.com/imagenes/anonimo.jpg";
                for (DataSnapshot comentario : dataSnapshot.getChildren()) {
                    if (comentario != null){

                        if(comentario.child("nombreUsuario").exists()){
                            nombre = comentario.child("nombreUsuario").getValue().toString();
                        }
                        if(comentario.child("imagenUrl").exists()){


                            imagenUrl = comentario.child("imagenUrl").getValue().toString();
                        }
                        lista_coment.add(new ComentarioProducto(
                                comentario.child("cuerpo").getValue().toString(),
                                comentario.child("fecha").getValue().toString(),
                                comentario.child("usuario").getValue().toString(),
                                nombre,imagenUrl

                        ));
                    }
                }


                recyclerView.setAdapter(new AdaptadorComentario(lista_coment));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}

