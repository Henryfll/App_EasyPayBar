package com.example.henryf.pryeasypaybar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
        //public ImageView imagen;

        public ViewHolder(View v) {
            super(v);
           // nombre = (TextView) v.findViewById(R.id.nombre_comida);
            nombre = (TextView) v.findViewById(R.id.txt_nombre);
            //imagen = (ImageView) v.findViewById(R.id.miniatura_comida);
        }
    }

    public AdaptadorInicio() {
    }

    @Override
    public int getItemCount() {
        System.out.println("tama;o del array= "+FragmentoInicio.Lista_Proveedor.size());
        return FragmentoInicio.Lista_Proveedor.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_inicio, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Proveedor item = FragmentoInicio.Lista_Proveedor.get(i);

        //Glide.with(viewHolder.itemView.getContext())
          //      .load(item.getIdDrawable())
            //    .centerCrop()
              //  .into(viewHolder.imagen);
       // viewHolder.nombre.setText(item.getNombre());
        viewHolder.nombre.setText(item.getNombre());

    }


}