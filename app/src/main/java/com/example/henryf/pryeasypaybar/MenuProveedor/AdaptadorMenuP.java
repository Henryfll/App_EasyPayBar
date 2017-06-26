package com.example.henryf.pryeasypaybar.MenuProveedor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.henryf.pryeasypaybar.R;
import com.example.henryf.pryeasypaybar.Servicios.CategoriaProveedor;



/**
 * Created by Fabian on 25/06/2017.
 */

public class AdaptadorMenuP extends RecyclerView.Adapter<AdaptadorMenuP.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item

        private TextView tituloCategoria;
        private RecyclerView reciclador;



        public ViewHolder(View v) {
            super(v);
            tituloCategoria = (TextView) v.findViewById(R.id.tituloCategoria);

        }
    }

    public AdaptadorMenuP() {
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items_menu_proveedor, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CategoriaProveedor item = MenuProveedor.getCategoriasProveedor().get(position);
        holder.tituloCategoria.setText(item.getNombre());
    }



    @Override
    public int getItemCount() {
        return MenuProveedor.getCategoriasProveedor().size();
    }
}
