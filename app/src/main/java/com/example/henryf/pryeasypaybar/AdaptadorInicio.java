package com.example.henryf.pryeasypaybar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.henryf.pryeasypaybar.MenuProveedor.MenuProveedor;
import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

/**
 * Created by HenryF on 03/03/2017.
 */

/**
 * Adaptador para mostrar los proveedores
 */
public class AdaptadorInicio
        extends RecyclerView.Adapter<AdaptadorInicio.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item

        public TextView nombre;
        public TextView bar;
        public ImageView imagenProveedor;
        private RecyclerView reciclador;
        //public Switch switch_afiliacion;
        private ImageView imgAfiliar;
        private ImageView imgCategoria;
        private RelativeLayout loading;
        private ProgressBar progressBar;


        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.txt_nombre);
            bar = (TextView) v.findViewById(R.id.txt_bar);
            imagenProveedor = (ImageView) v.findViewById(R.id.img_proveedor);
            imgAfiliar = (ImageView) v.findViewById(R.id.ic_afiliar);
            imgCategoria = (ImageView) v.findViewById(R.id.btnMenu);
            loading = (RelativeLayout) v.findViewById(R.id.loadingPanel);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarInico);


        }
    }

    public AdaptadorInicio() {

    }

    @Override
    public int getItemCount() {
        //System.out.println("tama;o del array= "+FragmentoInicio.Lista_Proveedor.size());
        return FragmentoInicio.Lista_Proveedor.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_inicio, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {


        final ProveedorServicio item = FragmentoInicio.Lista_Proveedor.get(i);

        /*
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://easypaybar.appspot.com/")
                .child(item.getImagen());

        final File localFile;
        try {
            localFile = File.createTempFile(item.getNombre_proveedor().toString(), "jpg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    viewHolder.imagenProveedor.setImageBitmap(bitmap);
                                                                       }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        Glide.with(viewHolder.itemView.getContext()).load(item.getImagenURL().toString())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        viewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })

                .into(viewHolder.imagenProveedor);

        viewHolder.nombre.setText("Proveedor: "+item.getNombre_proveedor());
        viewHolder.bar.setText(item.getBar_proveedor());

        /*viewHolder.switch_afiliacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ProveedorServicio prov=new ProveedorServicio();
                prov.Afiliarse(item.getUid_Proveedor());
            }
        });*/
        /**
         * Nuevo fragmento para categoria
         */
        viewHolder.imgCategoria.setOnClickListener(new View.OnClickListener() {
            ProveedorServicio itemAxi = item;
            @Override
            public void onClick(View v) {


                Context context = v.getContext();
                Intent intent = new Intent(context, MenuProveedor.class);
                intent.putExtra( "proveedor" , item );
                context.startActivity(intent);
            }
        });

        /**
         * Afiliacion de usuario en un bar   */
        if(item.isUsuarioAfiliado()){
            viewHolder.imgAfiliar.setVisibility(View.GONE);

        }else{
            viewHolder.imgAfiliar.setVisibility(View.VISIBLE);
            viewHolder.imgAfiliar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(v.getContext());
                    dialogo1.setTitle("");
                    dialogo1.setMessage("¿Desea afiliarce al "+ item.getBar_proveedor()+"?");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            afiliar(item.getUid_Proveedor());
                            viewHolder.imgAfiliar.setVisibility(View.GONE);
                        }
                    });
                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {

                        }
                    });
                    dialogo1.show();

                }
            });
        }


    }

    public void afiliar(String uidProveedor){
        ProveedorServicio prov=new ProveedorServicio();
        prov.Afiliarse(uidProveedor);


    }

}