package com.example.henryf.pryeasypaybar.MenuProveedor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.henryf.pryeasypaybar.R;
import com.example.henryf.pryeasypaybar.Servicios.ProductoProveedor;
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

    public AdaptadorProducto(ArrayList<ProductoProveedor> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public AdaptadorProducto.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.items_productos, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final AdaptadorProducto.ViewHolder holder, int position) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://easypaybar.appspot.com/")
                .child(mDataset.get(position).getImagen());

        final File localFile;
        try {
            localFile = File.createTempFile(mDataset.get(position).getNombre(), "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    holder.imgProducto.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.titulo.setText(mDataset.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titulo;
        private ImageView imgProducto;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
            imgProducto = (ImageView) itemView.findViewById(R.id.imagenProducto);
        }
    }
}
