package com.example.henryf.pryeasypaybar;

import android.support.v7.widget.RecyclerView;
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

import org.w3c.dom.Text;

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
        public Switch switch_afiliacion;

        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.txt_nombre);
            bar = (TextView) v.findViewById(R.id.txt_bar);
            imagenProveedor = (ImageView) v.findViewById(R.id.img_proveedor);
            switch_afiliacion = (Switch) v.findViewById(R.id.Switch_afiliacion);

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
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Proveedor item = FragmentoInicio.Lista_Proveedor.get(i);

        viewHolder.nombre.setText("Proveedor: "+item.getNombre());
        viewHolder.bar.setText(item.getBar());
        viewHolder.imagenProveedor.setImageBitmap(item.getImagen());
        viewHolder.switch_afiliacion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Proveedor prov=new Proveedor();
                prov.Afiliarse(item.getUid());

            }
        });
    }


}