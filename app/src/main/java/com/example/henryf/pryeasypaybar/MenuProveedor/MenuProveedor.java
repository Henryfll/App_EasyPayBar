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

public class MenuProveedor extends AppCompatActivity {

    private ImageView imgProveedor;
    private Toolbar titulo;
    private RecyclerView recyclerView;
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
        recyclerView = (RecyclerView) findViewById(R.id.categoriasRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        final ProveedorServicio proveedorServicio = (ProveedorServicio) intent.getExtras().getSerializable("proveedor");
        final ArrayList<CategoriaProveedor> categoriaProveedorsList = new ArrayList<>();

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("proveedor").child(proveedorServicio.getUid_Proveedor());


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot categorias: dataSnapshot.child("categoria").getChildren()){
                    ArrayList<ProductoProveedor> listProductos = new ArrayList<ProductoProveedor>();
                    for(DataSnapshot producto: categorias.child("producto").getChildren()){

                        listProductos.add(new ProductoProveedor(
                                producto.child("nombre").getValue().toString(),
                                producto.child("precio").getValue().toString(),
                                producto.child("imagen").getValue().toString(),
                                producto.child("veces").getValue().toString(),
                                producto.child("imagenURL").getValue().toString(),
                                producto.getKey().toString(),
                                proveedorServicio.getUid_Proveedor()
                        ));


                    }

                    categoriaProveedorsList.add(new CategoriaProveedor(
                            categorias.getKey().toString(),
                            categorias.child("descripcion").getValue().toString(),
                            categorias.child("nombre").getValue().toString(),
                            listProductos));

                }

                proveedorServicio.setCategoriaProveedors(categoriaProveedorsList);
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
