package com.example.henryf.pryeasypaybar;

/**
 * Created by HenryF on 23/05/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para poblar la lista de Recargas de la sección "Mi Cuenta"
 */
public class AdaptadorRecargas
        extends RecyclerView.Adapter<AdaptadorRecargas.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre_proveedor;
        public TextView saldo_cliente;
        public CardView provedorView;
        public TextView bar_proveedor;
        public ImageView imagenProveedor;



        public ViewHolder(View v) {
            super(v);
            nombre_proveedor = (TextView) v.findViewById(R.id.nombre_proeevdor);
            saldo_cliente = (TextView) v.findViewById(R.id.saldo_cliente);
            provedorView = (CardView) v.findViewById(R.id.provedorView);
            bar_proveedor = (TextView) v.findViewById(R.id.bar_proeevdor);
            imagenProveedor = (ImageView) v.findViewById(R.id.imagenProveedor);
        }
    }


    public AdaptadorRecargas() {
    }

    @Override
    public int getItemCount() {

        System.out.println("tamano del array= "+FragmentoRecargas.lista_result.size());
        return FragmentoRecargas.lista_result.size() ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_recarga, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {


        ProveedorServicio item = FragmentoRecargas.lista_result.get(i);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        Bitmap bitmaplocal;
        StorageReference storageRef = storage.getReferenceFromUrl("gs://easypaybar.appspot.com/")
                .child(item.getImagen());

        final File localFile;
        try {
            localFile = File.createTempFile("images"+item.getNombre_proveedor().toString(), "jpg");

            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                File fichero;
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                        viewHolder.imagenProveedor.setImageBitmap(bitmap);

                }



            });
        } catch (IOException e) {
            e.printStackTrace();
        }



        viewHolder.nombre_proveedor.setText(item.getNombre_proveedor());
        viewHolder.saldo_cliente.setText(item.getSaldo_cliente()+ "$");
        viewHolder.bar_proveedor.setText(item.getBar_proveedor());
        //viewHolder.imagenProveedor.setImageBitmap(item.getImagen());
        viewHolder.provedorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }




}
