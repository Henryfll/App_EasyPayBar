package com.example.henryf.pryeasypaybar;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * Created by HenryF on 03/03/2017.
 */

/**
 * Fragmento para la sección de "Inicio"
 */
public class FragmentoInicio extends Fragment {
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorInicio adaptador;
    public static ArrayList<ProveedorServicio> Lista_Proveedor = new ArrayList<ProveedorServicio>();
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private Bitmap imagen;
    private FirebaseAuth firebaseAuth;


    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public FragmentoInicio() {
    }

    public static void setLista_Proveedor(ArrayList<ProveedorServicio> lista_Proveedor) {
        Lista_Proveedor = lista_Proveedor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_inicio, container, false);
        getListaProveedor();
        reciclador = (RecyclerView) view.findViewById(R.id.recicladorInicio);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);
        adaptador = new AdaptadorInicio();
        reciclador.setAdapter(adaptador);
        return view;

    }


    public void getListaProveedor(){
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        firebaseAuth = FirebaseAuth.getInstance();



        final FirebaseUser user = firebaseAuth.getCurrentUser();
        //Consulta todos los proveedores
        final ArrayList<ProveedorServicio> lista_prove= new ArrayList<ProveedorServicio>();
        mFirebaseDatabase.child("proveedor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean afiliado;
                for (DataSnapshot proveedor : dataSnapshot.getChildren()) {
                     afiliado = false;
                    try {
                        System.out.println("pruebaProveedor: "+proveedor.child("nombre").getValue().toString());
                        if(proveedor.child("afiliados").child(user.getUid()).getValue() != null) {
                            afiliado = true;
                        }
                        System.out.println("BarAfiliados = "+proveedor.child("afiliados").child(user.getUid()).getValue());
                        // Lista_Proveedores.add(proveedor.child("bar").getValue().toString());//Almacena en el arraylist proveedores cada proveedor
                        lista_prove.add(new ProveedorServicio(proveedor.child("nombre").getValue().toString(),
                                "",
                                proveedor.child("bar").getValue().toString(),
                                proveedor.child("imagen").getValue().toString(),
                                proveedor.child("codigoQR").getValue().toString(), afiliado
                                ));

                        System.out.println("URLimagen: "+ proveedor.child("imagen").getValue().toString());
                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }
                }
              setLista_Proveedor(lista_prove);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
