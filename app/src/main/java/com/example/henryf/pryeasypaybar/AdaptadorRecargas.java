package com.example.henryf.pryeasypaybar;

/**
 * Created by HenryF on 23/05/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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
        public Recargas objRecargas = new Recargas();

        public ViewHolder(View v) {
            super(v);
            nombre_proveedor = (TextView) v.findViewById(R.id.nombre_proeevdor);
            saldo_cliente = (TextView) v.findViewById(R.id.saldo_cliente);
        }
    }


    public AdaptadorRecargas() {
    }

    @Override
    public int getItemCount() {


        return 0 ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_recarga, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }




}
