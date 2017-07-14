package com.example.henryf.pryeasypaybar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.henryf.pryeasypaybar.DetalleProducto.Detalleproducto;
import com.example.henryf.pryeasypaybar.Servicios.ComentarioProducto;

/**
 * Created by HenryF on 10/7/2017.
 */
/**
 * Adaptador para mostrar la sección Comentarios AdaptadorComentario
 */

public class AdaptadorComentario
        extends RecyclerView.Adapter<AdaptadorComentario.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comentario_cuerpo;
        public TextView comentario_fecha;

        public ViewHolder(View v) {
            super(v);
            comentario_cuerpo = (TextView) v.findViewById(R.id.lbl_comentario);
            comentario_fecha = (TextView) v.findViewById(R.id.lbl_fecha_comentario);

        }
    }
    public AdaptadorComentario() {
    }

    @Override
    public int getItemCount() {
        return Detalleproducto.getComentarioProductos().size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_comentario, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int contador) {
        final ComentarioProducto item = Detalleproducto.ComentarioProductos.get(contador);
        viewHolder.comentario_cuerpo.setText(item.getCuerpo());
        viewHolder.comentario_fecha.setText(item.getFecha());
    }

}


