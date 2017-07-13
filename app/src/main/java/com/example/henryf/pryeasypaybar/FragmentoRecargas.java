package com.example.henryf.pryeasypaybar;

/**
 * Created by HenryF on 23/05/2017.
 */

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

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

    public static void setLista_result(ArrayList<ProveedorServicio> lista_result) {
        FragmentoRecargas.lista_result = lista_result;

    }

    public static ArrayList<ProveedorServicio> lista_result = new ArrayList<ProveedorServicio>();

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
        final ArrayList<ProveedorServicio> lista_recargas = new ArrayList<>();
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

                            ArrayList<String> listDetalle = new ArrayList<String>();
                            for(DataSnapshot detalleRecarga: proveedor.child("afiliados").child(user.getUid()).child("recarga").getChildren()){
                                Recarga recarga = detalleRecarga.getValue(Recarga.class);
                                System.out.println("Recarga"+ recarga.getFecha_Recarga());
                                System.out.println("DetalleRecarga: "+detalleRecarga.getValue());
                                listDetalle.add(""+recarga.getFecha_Recarga() +"           "+ recarga.getValor()+" $");
                            }


                            lista_recargas.add(new
                                    ProveedorServicio(
                                            proveedor.child("nombre").getValue().toString(),
                                            proveedor.child("afiliados").child(user.getUid()).child("saldo").getValue().toString(),
                                            proveedor.child("bar").getValue().toString(),
                                            proveedor.child("imagen").getValue().toString(),
                                            proveedor.child("codigoQR").getValue().toString(),
                                            listDetalle,
                                            true,
                                            null,
                                            proveedor.child("imagenURL").getValue().toString()

                            ));


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







}