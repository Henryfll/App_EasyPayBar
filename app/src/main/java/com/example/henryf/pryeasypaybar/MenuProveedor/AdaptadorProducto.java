package com.example.henryf.pryeasypaybar.MenuProveedor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.henryf.pryeasypaybar.DetalleProducto.Detalleproducto;
import com.example.henryf.pryeasypaybar.R;
import com.example.henryf.pryeasypaybar.Servicios.ProductoProveedor;
import com.google.android.gms.tasks.OnFailureListener;
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


/**
 * Created by Fabian on 26/06/2017.
 */

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.ViewHolder> {


    private ArrayList<ProductoProveedor> mDataset;
    private String keyCategoria;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

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
                               // Detalleproducto comment=new Detalleproducto();
                               // comment.getListaComentarios(mDataset.get(position).getUidproveedor(),mDataset.get(position).getKey(),keyCategoria);
                               // String uidopro= mDataset.get(position).getUidproveedor();
                                //String prod=mDataset.get(position).getKey();
                               // String cat=keyCategoria;
                                //hilo(uidopro,prod);
                                if(mDataset.get(position).isSePuedeComentar()){
                                    DatabaseReference myRef = database.getReference("proveedor").child(mDataset.get(position).getUidproveedor().toString());
                                    myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            for(DataSnapshot categoria: dataSnapshot.child("categoria").getChildren()) {
                                                if (categoria.child("producto").child(mDataset.get(position).getKey().toString()).exists())
                                                    keyCategoria = categoria.getKey();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value
                                            System.out.println("Failed to read value." + error.toException());
                                        }

                                    });
                                    Context context = v.getContext();
                                    Intent intent = new Intent(context, Detalleproducto.class);
                                    intent.putExtra( "producto" , mDataset.get(position) );
                                    intent.putExtra("keyCategoria", keyCategoria);
                                    context.startActivity(intent);
                                }else{
                                    Toast.makeText(v.getContext(), "No se puede comentar", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                        return false;
                    }
                })
                .into(holder.imgProducto);
        holder.titulo.setText(mDataset.get(position).getNombre());
        holder.ratingText.setText(mDataset.get(position).getCalificacion()+"");

    }
    /*
    public void hilo(final String uid, final String pro){

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Cuerpo variables"+uid+"-"+pro+"-"+keyCategoria);
                Detalleproducto comment=new Detalleproducto();
                comment.getListaComentarios(uid,pro,keyCategoria);

            }
        });
    }
    */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titulo;
        private ImageView imgProducto;
        private ProgressBar progressBar;
        private TextView ratingText;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            imgProducto = (ImageView) itemView.findViewById(R.id.imagenProducto);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarProductos);
            ratingText = (TextView) itemView.findViewById(R.id.ratingTextView);
        }
    }
     //Implementacion de Metodos de hilo  para crear leer comentarios



    private class SimpleTask extends AsyncTask<String , Integer, Void> {


        @Override
        protected void onPreExecute() {
            System.out.println("Cuerpo inicio");
        }

        /*
        Ejecución del ordenamiento y transmision de progreso
         */
        @Override
        protected Void doInBackground(String... params) {
            Detalleproducto comment=new Detalleproducto();
            //comment.getListaComentarios(params[0],params[1],params[2]);
            return null;
        }

        /*
         Se informa en progressLabel que se canceló la tarea y
         se hace invisile el botón "Cancelar"
          */
        @Override
        protected void onCancelled() {
            super.onCancelled();

        }

        /*
        Impresión del progreso en tiempo real
          */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        /*
        Se notifica que se completó el ordenamiento y se habilita
        de nuevo el botón "Ordenar"
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            System.out.println("Cuerpo de Comentarios finalizada");
        }

    }

}
