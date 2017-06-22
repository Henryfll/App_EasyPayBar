package com.example.henryf.pryeasypaybar;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

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
        //public Switch switch_afiliacion;
        private ImageView imgAfiliar;
        private ImageView imgVer;


        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.txt_nombre);
            bar = (TextView) v.findViewById(R.id.txt_bar);
            imagenProveedor = (ImageView) v.findViewById(R.id.img_proveedor);
            imgAfiliar = (ImageView) v.findViewById(R.id.ic_afiliar);
            imgVer = (ImageView) v.findViewById(R.id.btn_menu);
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

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://easypaybar.appspot.com/")
                .child(item.getImagen());

        final File localFile;
        try {
            localFile = File.createTempFile("images"+item.getNombre_proveedor().toString(), "jpg");
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

        viewHolder.nombre.setText("Proveedor: "+item.getNombre_proveedor());
        viewHolder.bar.setText(item.getBar_proveedor());

        /*viewHolder.switch_afiliacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ProveedorServicio prov=new ProveedorServicio();
                prov.Afiliarse(item.getUid_Proveedor());
            }
        });*/
        if(item.isUsuarioAfiliado()){
            viewHolder.imgAfiliar.setVisibility(View.GONE);

        }else{
            viewHolder.imgAfiliar.setVisibility(View.VISIBLE);
            viewHolder.imgAfiliar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(v.getContext());
                    dialogo1.setTitle("Importante");
                    dialogo1.setMessage("¿Desea afiliarce al "+ item.getBar_proveedor()+"?");
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            afiliar(item.getUid_Proveedor());
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

        viewHolder.imgVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.Fragment fragmentoGenerico =new FragmentoProductos();
                android.support.v4.app.FragmentTransaction transaction=fragmentoGenerico.getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor_principal,fragmentoGenerico);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


    }

    public void afiliar(String uidProveedor){
        ProveedorServicio prov=new ProveedorServicio();
        prov.Afiliarse(uidProveedor);

    }

}