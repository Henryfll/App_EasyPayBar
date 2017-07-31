package com.example.henryf.pryeasypaybar.MenuProveedor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.henryf.pryeasypaybar.R;
import com.example.henryf.pryeasypaybar.Servicios.CategoriaProveedor;
import com.example.henryf.pryeasypaybar.Servicios.ProductoFavorito;
import com.example.henryf.pryeasypaybar.Servicios.ProductoProveedor;
import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import static com.example.henryf.pryeasypaybar.R.id.producto;

public class MenuProveedor extends AppCompatActivity {

    private ImageView imgProveedor;
    private Toolbar titulo;
    private RecyclerView recyclerView;
    private RecyclerView favoritosView;
    private LinearLayoutManager layoutManager;
    private static ArrayList<CategoriaProveedor> categoriasProveedor;
    private ProgressBar progressBar;
    private CollapsingToolbarLayout collapsingToolbarLayout ;


    public static ArrayList<CategoriaProveedor> getCategoriasProveedor() {
        return categoriasProveedor;
    }

    public static void setCategoriasProveedor(ArrayList<CategoriaProveedor> categoriasProveedor) {
        MenuProveedor.categoriasProveedor = categoriasProveedor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_proveedor);
        favoritosView = (RecyclerView) findViewById(R.id.categoriaFavoritos);
        favoritosView.setHasFixedSize(true);
        favoritosView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView = (RecyclerView) findViewById(R.id.categoriasRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        final ProveedorServicio proveedorServicio = (ProveedorServicio) intent.getExtras().getSerializable("proveedor");
        final ProductoFavorito productoFavorito = new ProductoFavorito();

        setCategoriasProveedor(proveedorServicio.getCategoriaProveedors());
        imgProveedor = (ImageView) findViewById(R.id.imagenProveedorMenu);
        titulo = (Toolbar) findViewById(R.id.toolbarMenu);

        titulo.setTitle(proveedorServicio.getBar_proveedor().toString());
        progressBar = (ProgressBar) findViewById(R.id.progressBarMenu);
        collapsingToolbarLayout =   (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        Glide.with(this).load(proveedorServicio.getImagenURL())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imgProveedor);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("proveedor").child(proveedorServicio.getUid_Proveedor());

        //Consulta de los Id de productos favoritos del Proveedor
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<ProductoFavorito> productoFavoritoList = new ArrayList<>();
                for(DataSnapshot productosFavori: dataSnapshot.child("favoritos").getChildren()){
                    productoFavoritoList.add(new ProductoFavorito(
                            productosFavori.child("categoriaId").getValue().toString(),
                            productosFavori.child("productoId").getValue().toString()));


                }
                productoFavorito.setProductos(productoFavoritoList);
            }

            @Override
            public void onCancelled(DatabaseError error) {

                // Failed to read value

                System.out.println("Failed to read value." + error.toException());

            }

        });

        //Consulta de los productos Favoritos
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<ProductoProveedor> listProductos = new ArrayList<ProductoProveedor>();
                final ArrayList<CategoriaProveedor> categoriaProveedorsList = new ArrayList<>();
                for (ProductoFavorito produc: productoFavorito.getProductos()                    ) {
                    if (dataSnapshot.child("categoria").child(produc.getCategoriaId())
                            .child("producto").child(produc.getProductoId()).exists()){
                        boolean sePuedeComentar = true;
                        float calificacionPromedio = 0;
                        float numeroCalificaciones = 0;
                        DataSnapshot referencia = dataSnapshot.child("categoria").child(produc.getCategoriaId())
                                .child("producto").child(produc.getProductoId());
                        if(referencia.child("comentar").exists()){
                            sePuedeComentar = Boolean.parseBoolean(referencia.child("comentar").getValue().toString());
                        }
                        if(referencia.child("calificacion").exists()){
                            for(DataSnapshot calificacion: referencia.child("calificacion").getChildren()){
                                calificacionPromedio = calificacionPromedio+Float.parseFloat(calificacion.child("valoracion").getValue().toString());
                                numeroCalificaciones++;
                            }
                            calificacionPromedio= calificacionPromedio/numeroCalificaciones;
                        }else{
                            calificacionPromedio = 0;

                        }
                        listProductos.add(new ProductoProveedor(
                                referencia.child("nombre").getValue().toString(),
                                referencia.child("precio").getValue().toString(),
                                referencia.child("imagen").getValue().toString(),
                                referencia.child("veces").getValue().toString(),
                                referencia.child("imagenURL").getValue().toString(),
                                produc.getProductoId(),
                                proveedorServicio.getUid_Proveedor(),
                                sePuedeComentar,
                                calificacionPromedio
                        ));
                    }
                }


                categoriaProveedorsList.add(new CategoriaProveedor(
                        "Key"/*dataSnapshot.child("categoria").getKey().toString()*/,
                        "Productos favoritos",
                        "Favoritos",
                        listProductos));


                favoritosView.setAdapter(new AdaptadorMenuP(categoriaProveedorsList));

            }



            @Override

            public void onCancelled(DatabaseError error) {

                // Failed to read value

                System.out.println("Failed to read value." + error.toException());

            }

        });

        //Consulta de todos los productos del Proveedor
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean sePuedeComentar = true;
                float calificacionPromedio = 0;
                float numeroCalificaciones = 0;
                final ArrayList<CategoriaProveedor> categoriaProveedorsList = new ArrayList<>();
                for(DataSnapshot categorias: dataSnapshot.child("categoria").getChildren()){

                    ArrayList<ProductoProveedor> listProductos = new ArrayList<ProductoProveedor>();

                    for(DataSnapshot producto: categorias.child("producto").getChildren()){
                        calificacionPromedio = 0;
                        numeroCalificaciones = 0;
                        if(producto.child("comentar").exists()){
                            sePuedeComentar = Boolean.parseBoolean(producto.child("comentar").getValue().toString());
                        }
                        if(producto.child("calificacion").exists()){
                            for(DataSnapshot calificacion: producto.child("calificacion").getChildren()){
                                calificacionPromedio = calificacionPromedio+Float.parseFloat(calificacion.child("valoracion").getValue().toString());
                                numeroCalificaciones++;
                            }
                        }else{
                            calificacionPromedio = 0;
                            numeroCalificaciones = 1;
                        }

                        listProductos.add(new ProductoProveedor(
                                producto.child("nombre").getValue().toString(),
                                producto.child("precio").getValue().toString(),
                                producto.child("imagen").getValue().toString(),
                                producto.child("veces").getValue().toString(),
                                producto.child("imagenURL").getValue().toString(),
                                producto.getKey().toString(),
                                proveedorServicio.getUid_Proveedor(),
                                sePuedeComentar,
                                calificacionPromedio/numeroCalificaciones
                        ));


                    }

                    categoriaProveedorsList.add(new CategoriaProveedor(
                            categorias.getKey().toString(),
                            categorias.child("descripcion").getValue().toString(),
                            categorias.child("nombre").getValue().toString(),
                            listProductos));

                }


                recyclerView.setAdapter(new AdaptadorMenuP(categoriaProveedorsList));

            }



            @Override

            public void onCancelled(DatabaseError error) {

                // Failed to read value

                System.out.println("Failed to read value." + error.toException());

            }

        });
    }
}
