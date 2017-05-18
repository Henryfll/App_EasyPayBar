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

public class AdaptadorInicio
        extends RecyclerView.Adapter<AdaptadorInicio.ViewHolder> {
    MainActivity objlogin = new MainActivity();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;

        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre_proveedor);
        }
    }

    public AdaptadorInicio() {
    }

    @Override
    public int getItemCount() {
        System.out.println("pruebaConteo:"+objlogin.Lista_Proveedores.size());
        for (Proveedor item: objlogin.Lista_Proveedores
                ) {
            System.out.println("pruebaProv: "+item.getNombre());
        }
        return objlogin.Lista_Proveedores.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_inicio, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Proveedor item =  objlogin.Lista_Proveedores.get(i);
        viewHolder.nombre.setText(item.getNombre());


    }


}
