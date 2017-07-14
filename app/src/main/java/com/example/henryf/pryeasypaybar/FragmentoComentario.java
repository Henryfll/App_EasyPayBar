package com.example.henryf.pryeasypaybar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.henryf.pryeasypaybar.DetalleProducto.Detalleproducto;
import com.example.henryf.pryeasypaybar.Servicios.ComentarioProducto;
import com.example.henryf.pryeasypaybar.Servicios.ProductoProveedor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;


public class FragmentoComentario extends Fragment {
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorComentario adaptador;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmento_comentario, container, false);
        reciclador = (RecyclerView) view.findViewById(R.id.recicladorcomentario);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);
        adaptador = new AdaptadorComentario();
        reciclador.setAdapter(adaptador);
        return view;
    }
/*
    public void getListaComentarios(final String uid_Proveedor, final String uid_Producto, final String uid_categoria){
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        //Consulta todos los comentarios

        mFirebaseDatabase.child("proveedor").child(uid_Proveedor).child("categoria").child(uid_categoria).child("producto").child(uid_Producto).child("comentario").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ComentarioProducto> lista_coment= new ArrayList<ComentarioProducto>();
                for (DataSnapshot comentario : dataSnapshot.getChildren()) {
                    System.out.print("Cuerpo: "+comentario.child("cuerpo").getValue().toString()+"-"
                            +comentario.child("fecha").getValue().toString()+"-"
                            +comentario.child("usuario").getValue().toString()+"-");
                    lista_coment.add(new ComentarioProducto(
                            comentario.child("cuerpo").getValue().toString(),
                            comentario.child("fecha").getValue().toString(),
                            comentario.child("usuario").getValue().toString()
                    ));
                }
                setComentarioProductos(lista_coment);
                System.out.println("comentarios  :"+lista_coment.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

}
