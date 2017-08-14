package com.example.henryf.pryeasypaybar.MenuProveedor;

import com.example.henryf.pryeasypaybar.Servicios.CategoriaProveedor;
import com.example.henryf.pryeasypaybar.Servicios.ProductoFavorito;
import com.example.henryf.pryeasypaybar.Servicios.ProductoProveedor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Diego Claudio on 31/07/2017.
 */

public class ConsultasProveedor {
    final ProductoFavorito productoFavorito = new ProductoFavorito();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    CategoriaProveedor categoriaProveedor;
    private String keycategoria;

    public String getKeycategoria() {
        return keycategoria;
    }

    public void setKeycategoria(String keycategoria) {
        this.keycategoria = keycategoria;
    }

    public ConsultasProveedor() {
    }


    public ArrayList<ProductoFavorito> ConsultaFavoritos(String proveedorId){

        DatabaseReference myRef = database.getReference("proveedor").child(proveedorId);
        final ArrayList<ProductoFavorito> productoFavoritoList = new ArrayList<>();
        //Consulta de los Id de productos favoritos del Proveedor
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot productosFavori: dataSnapshot.child("favoritos").getChildren()){
                    productoFavoritoList.add(new ProductoFavorito(
                            productosFavori.child("categoriaId").getValue().toString(),
                            productosFavori.child("productoId").getValue().toString()));


                }
                //productoFavorito.setProductos(productoFavoritoList);
            }

            @Override
            public void onCancelled(DatabaseError error) {

                // Failed to read value

                System.out.println("Failed to read value." + error.toException());

            }

        });
        return productoFavoritoList;
    }

    public CategoriaProveedor CategoriaFavoritos(final String proveedorId){
        DatabaseReference myRef = database.getReference("proveedor").child(proveedorId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean sePuedeComentar = true;
                float calificacionPromedio=0;
                float numeroCalificaciones=0;
                ArrayList<ProductoProveedor> listProductosFavoritos = new ArrayList<ProductoProveedor>();
                for (ProductoFavorito produc: ConsultaFavoritos(proveedorId)) {
                    if (dataSnapshot.child("categoria").child(produc.getCategoriaId())
                            .child("producto").child(produc.getProductoId()).exists()){
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
                        listProductosFavoritos.add(new ProductoProveedor(
                                referencia.child("nombre").getValue().toString(),
                                referencia.child("precio").getValue().toString(),
                                referencia.child("imagen").getValue().toString(),
                                referencia.child("veces").getValue().toString(),
                                referencia.child("imagenURL").getValue().toString(),
                                produc.getProductoId(),
                                proveedorId,
                                sePuedeComentar,
                                calificacionPromedio
                        ));
                    }
                    categoriaProveedor = new CategoriaProveedor(
                            "Key",
                            "Productos favoritos",
                            "Favoritos",
                            listProductosFavoritos);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

                // Failed to read value

                System.out.println("Failed to read value." + error.toException());

            }

        });
        return categoriaProveedor;
    }

}
