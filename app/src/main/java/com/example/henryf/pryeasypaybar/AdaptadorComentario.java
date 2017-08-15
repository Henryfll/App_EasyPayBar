package com.example.henryf.pryeasypaybar;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.henryf.pryeasypaybar.DetalleProducto.Detalleproducto;
import com.example.henryf.pryeasypaybar.Servicios.ComentarioProducto;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by HenryF on 10/7/2017.
 */
/**
 * Adaptador para mostrar la sección Comentarios AdaptadorComentario
 */

public class AdaptadorComentario
        extends RecyclerView.Adapter<AdaptadorComentario.ViewHolder> {

    private ArrayList<ComentarioProducto> lista_coment= new ArrayList<ComentarioProducto>();
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comentario_cuerpo;
        public TextView comentario_fecha;
        public TextView nombre_usuario;
        public ImageView imageUsuario;

        public ViewHolder(View v) {
            super(v);
            comentario_cuerpo = (TextView) v.findViewById(R.id.lbl_comentario);
            comentario_fecha = (TextView) v.findViewById(R.id.lbl_fecha_comentario);
            nombre_usuario = (TextView) v.findViewById(R.id.lbl_nombre);
            imageUsuario = (ImageView) v.findViewById(R.id.img_usuario);

        }
    }
    public AdaptadorComentario(ArrayList<ComentarioProducto> comentarioProductosList) {
        this.lista_coment = comentarioProductosList;
    }

    @Override
    public int getItemCount() {
        return lista_coment.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_comentario, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int contador) {
        final ComentarioProducto item = lista_coment.get(contador);

        Glide.with(viewHolder.itemView.getContext()).load(item.getImagenUrl().toString()).into(viewHolder.imageUsuario);
        viewHolder.comentario_cuerpo.setText(item.getCuerpo());
        viewHolder.comentario_fecha.setText(item.getFecha());
        viewHolder.nombre_usuario.setText(item.getNombreUsuario());
    }

}


