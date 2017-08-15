package com.example.henryf.pryeasypaybar;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;

import java.util.ArrayList;

/**
 * Created by HenryF on 7/8/2017.
 */

public class AdaptadorCompras extends RecyclerView.Adapter<AdaptadorCompras.ViewHolder>  {


    private static ArrayList<ArrayList<String>>  itemsRecargas = new ArrayList<ArrayList<String>>();
    private static ArrayList<ArrayList<String>>  itemsRecargasfinal = new ArrayList<ArrayList<String>>();
    private static ArrayList<ProveedorServicio> listaRecargas ;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre_proveedor;
       // public TextView saldo_cliente;
        public LinearLayout provedorView;
        public TextView bar_proveedor;
        public ImageView imagenProveedor;
        public ListView listaDetalle;
        private LinearLayout layoutAnimado;
        public CardView detalleRecarga;
        private ProgressBar progressBar;


        public ViewHolder(View v) {
            super(v);
            nombre_proveedor = (TextView) v.findViewById(R.id.nombre_proeevdor1);
          //  saldo_cliente = (TextView) v.findViewById(R.id.saldo_cliente1);
            provedorView = (LinearLayout) v.findViewById(R.id.provedorView1);
            bar_proveedor = (TextView) v.findViewById(R.id.bar_proeevdor1);
            imagenProveedor = (ImageView) v.findViewById(R.id.imagenProveedor1);
            listaDetalle = (ListView) v.findViewById(R.id.listViewDetalle1);
            detalleRecarga = (CardView) v.findViewById(R.id.detalleRecarga1);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarRecarga1);
        }
    }


    public AdaptadorCompras(ArrayList<ProveedorServicio> listaRecargas) {
        this.listaRecargas = listaRecargas;
    }

    @Override
    public int getItemCount() {

        return listaRecargas.size() ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_compra, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int contador) {

        viewHolder.detalleRecarga.setVisibility(View.GONE);
        ProveedorServicio item = listaRecargas.get(contador);
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

        viewHolder.nombre_proveedor.setText(item.getNombre_proveedor());
        //viewHolder.saldo_cliente.setText(item.getSaldo_cliente()+ "$");
        viewHolder.bar_proveedor.setText(item.getBar_proveedor());



        ArrayList<String> leadsNames = item.getLista_recargas();




        ListAdapter adaptador = new ArrayAdapter<String>( viewHolder.itemView.getContext() , android.R.layout.simple_list_item_1, leadsNames);

        viewHolder.listaDetalle.setAdapter(adaptador);


        viewHolder.provedorView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(viewHolder.detalleRecarga.getVisibility() == View.GONE){
                    viewHolder.detalleRecarga.setVisibility(View.VISIBLE);

                }else{
                    if(viewHolder.detalleRecarga.getVisibility() == View.VISIBLE){
                        viewHolder.detalleRecarga.setVisibility(View.GONE);
                    }
                }
            }
        });



    }

    private void showMyDialog(Context context, ArrayList<String> detalleRecargas){

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_recargas);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        TextView textView = (TextView) dialog.findViewById(R.id.txtTitle);
        ListView listView = (ListView) dialog.findViewById(R.id.listView);
        Button btnBtmLeft = (Button) dialog.findViewById(R.id.btnBtmLeft);
        Button btnBtmRight = (Button) dialog.findViewById(R.id.btnBtmRight);


        ListAdapter adaptador = new ArrayAdapter<String>( context, android.R.layout.simple_list_item_1, detalleRecargas);
        listView.setAdapter(adaptador);

        // Set The Adapter
        final Dialog dialogAxi = dialog;
        btnBtmLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogAxi.dismiss();
            }
        });

        btnBtmRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want here
            }
        });

        /**
         * if you want the dialog to be specific size, do the following
         * this will cover 85% of the screen (85% width and 85% height)
         */
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dialogWidth = (int)(displayMetrics.widthPixels * 0.85);
        int dialogHeight = (int)(displayMetrics.heightPixels * 0.85);
        dialog.getWindow().setLayout(dialogWidth, dialogHeight);

        dialog.show();
    }



}
