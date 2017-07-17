package com.example.henryf.pryeasypaybar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.henryf.pryeasypaybar.Servicios.CategoriaProveedor;
import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;
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
    private FirebaseAuth firebaseAuth;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FragmentoInicio() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_inicio, container, false);
        //Lista de proveedores que se encuentra registrados en la aplicacion


        //Declaracion de recyclerView y Spiner
        reciclador = (RecyclerView) view.findViewById(R.id.recicladorInicio);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.Swipefragmento_inicio);

        swipeRefreshLayout.setColorSchemeResources(R.color.accentColor, R.color.ap_black,R.color.ap_gray);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);
        new GetDataFromFirebase().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mFirebaseDatabase.child("proveedor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<ProveedorServicio> lista_prove= new ArrayList<ProveedorServicio>();
                boolean afiliado;
                for (DataSnapshot proveedor : dataSnapshot.getChildren()) {
                    afiliado = false;

                        if(proveedor.child("afiliados").child(user.getUid()).getValue() != null) {
                            afiliado = true;
                        }

                        lista_prove.add(new
                                ProveedorServicio(proveedor.child("nombre").getValue().toString(),
                                "",
                                proveedor.child("bar").getValue().toString(),
                                proveedor.child("imagen").getValue().toString(),
                                proveedor.getKey(),
                                null,
                                afiliado,
                                null,
                                proveedor.child("imagenURL").getValue().toString()
                        ));


                }
                adaptador = new AdaptadorInicio(lista_prove);
                reciclador.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);

                    }
                },2000);
            }
        });
        return view;

    }



    private class GetDataFromFirebase extends AsyncTask<Void,Void,Boolean> {



        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }



        @Override

        protected Boolean doInBackground(Void... voids) {

            return false;

        }



        @Override

        protected void onPostExecute(Boolean aBoolean) {

            super.onPostExecute(aBoolean);

        }

    }

}
