package com.example.henryf.pryeasypaybar.MenuProveedor;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.henryf.pryeasypaybar.R;
import com.example.henryf.pryeasypaybar.Servicios.CategoriaProveedor;
import com.example.henryf.pryeasypaybar.Servicios.ProductoProveedor;
import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;

import java.util.ArrayList;


/**
 * Created by Fabian on 25/06/2017.
 */

public class AdaptadorMenuP extends RecyclerView.Adapter<AdaptadorMenuP.ViewHolder> {

    private ArrayList<CategoriaProveedor> categoriaProveedors = new ArrayList<>();


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item

        private TextView tituloCategoria;
        private RecyclerView reciclador;
        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private ArrayList<String> mDataset;
        private ArrayList<ProductoProveedor> mDataProveedor;
        private String keyCategoria;



        public ViewHolder(View v) {
            super(v);
            tituloCategoria = (TextView) v.findViewById(R.id.tituloCategoria);
            mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewProductos);


        }
    }

    public AdaptadorMenuP(ArrayList<CategoriaProveedor> categoriaProveedors) {
        this.categoriaProveedors = categoriaProveedors;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items_menu_proveedor, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CategoriaProveedor item =categoriaProveedors.get(position);


        holder.tituloCategoria.setText(item.getNombre());
        holder.mDataProveedor = item.getProductoProveedores();
        holder.keyCategoria = item.getKey();
        holder.mRecyclerView.setHasFixedSize(true);
        holder.mLayoutManager = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        holder.mRecyclerView.setLayoutManager(holder.mLayoutManager);
        holder.mAdapter = new AdaptadorProducto(holder.mDataProveedor, holder.keyCategoria);
        holder.mRecyclerView.setAdapter(holder.mAdapter);
    }



    @Override
    public int getItemCount() {
        return categoriaProveedors.size();
    }
}
