package com.example.henryf.pryeasypaybar;

/**
 * Created by HenryF on 23/05/2017.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.*;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

/**
       * Fragmento para la pestaña "DIRECCIONES" De la sección "Mi Cuenta"
        */
public class FragmentoRecargas extends Fragment {
    private RecyclerView recyclerView;

    private FloatingActionButton fab;
    private LinearLayoutManager layoutManager;
    public static AdaptadorRecargas adaptador;

    private LinearLayoutManager linearLayout;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private Bitmap imagen;

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public static void setLista_result(ArrayList<Recargas> lista_result) {
        FragmentoRecargas.lista_result = lista_result;
    }

    public static ArrayList<Recargas> lista_result = new ArrayList<Recargas>();

    public FragmentoRecargas() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragmento_recarga, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        recyclerView = (RecyclerView) view.findViewById(R.id.recicladorRecarg);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adaptador = new AdaptadorRecargas();
        recyclerView.setAdapter(adaptador);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListRecargas();

                recyclerView.setAdapter(adaptador);
            }
        });
        getListRecargas();


        return view;
    }



    public void getListRecargas(){
        final ArrayList<Recargas> lista_recargas = new ArrayList<Recargas>();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        firebaseAuth = FirebaseAuth.getInstance();



        final FirebaseUser user = firebaseAuth.getCurrentUser();
        //Consulta todos los proveedores
        mFirebaseDatabase.child("proveedor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot proveedor : dataSnapshot.getChildren()) {
                    try {
                        System.out.println("pruebaProveedor: "+proveedor.child("nombre").getValue().toString());
                        if(proveedor.child("afiliados").child(user.getUid()).getValue() != null) {
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReferenceFromUrl("gs://easypaybar.appspot.com/proveedores")
                                    .child("bar12.jpg");

                                final File localFile = File.createTempFile("images", "jpg");
                                storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                 @Override
                                  public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                  Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                   setImagen(bitmap);                                                    }
                                });


                            lista_recargas.add(new Recargas(proveedor.child("nombre").getValue().toString(),
                                    proveedor.child("afiliados").child(user.getUid()).child("saldo").getValue().toString(),
                                            proveedor.child("bar").getValue().toString(),
                                    imagen));
                        }

                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                }

                setLista_result(lista_recargas);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    public void ejem(ArrayList<Recargas> recarga){
        System.out.println("Nombre p"+recarga.size());
    }


}