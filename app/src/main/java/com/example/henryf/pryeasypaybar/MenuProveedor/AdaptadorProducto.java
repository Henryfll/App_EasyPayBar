package com.example.henryf.pryeasypaybar.MenuProveedor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.henryf.pryeasypaybar.DetalleProducto.Detalleproducto;
import com.example.henryf.pryeasypaybar.R;
import com.example.henryf.pryeasypaybar.Servicios.ProductoProveedor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Fabian on 26/06/2017.
 */

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.ViewHolder> {


    private ArrayList<ProductoProveedor> mDataset;
    private String keyCategoria;

    public AdaptadorProducto(ArrayList<ProductoProveedor> mDataset, String keyCategoria) {
        this.mDataset = mDataset;
        this.keyCategoria = keyCategoria;
    }

    @Override
    public AdaptadorProducto.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.items_productos, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final AdaptadorProducto.ViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext())
                .load(mDataset.get(position).getImagenURL().toString())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.imgProducto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Detalleproducto comment=new Detalleproducto();
                                comment.getListaComentarios(mDataset.get(position).getUidproveedor(),mDataset.get(position).getKey(),keyCategoria);
                                Context context = v.getContext();
                                Intent intent = new Intent(context, Detalleproducto.class);
                                intent.putExtra( "producto" , mDataset.get(position) );
                                intent.putExtra("keyCategoria", keyCategoria);
                                context.startActivity(intent);
                            }
                        });
                        return false;
                    }
                })
                .into(holder.imgProducto);
        holder.titulo.setText(mDataset.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titulo;
        private ImageView imgProducto;
        private ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            imgProducto = (ImageView) itemView.findViewById(R.id.imagenProducto);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarProductos);
        }
    }
}
