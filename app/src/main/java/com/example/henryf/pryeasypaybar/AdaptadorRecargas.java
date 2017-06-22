package com.example.henryf.pryeasypaybar;

/**
 * Created by HenryF on 23/05/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para poblar la lista de Recargas de la sección "Mi Cuenta"
 */
public class AdaptadorRecargas
        extends RecyclerView.Adapter<AdaptadorRecargas.ViewHolder> {

    private static ArrayList<ArrayList<String>>  itemsRecargas = new ArrayList<ArrayList<String>>();
    private static ArrayList<ArrayList<String>>  itemsRecargasfinal = new ArrayList<ArrayList<String>>();
    private static ArrayList<String> listaDetalle = new ArrayList<>();
    public static ArrayList<ArrayList<String>> getItemsRecargasfinal() {
        return itemsRecargasfinal;
    }

    public static void setItemsRecargasfinal(ArrayList<ArrayList<String>> itemsRecargasfinal) {
        AdaptadorRecargas.itemsRecargasfinal = itemsRecargasfinal;
    }

    public static ArrayList<ArrayList<String>> getItemsRecargas() {
        return itemsRecargas;
    }

    public static void setItemsRecargas(ArrayList<String> itemsRecargas) {
        AdaptadorRecargas.itemsRecargas.add(itemsRecargas);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre_proveedor;
        public TextView saldo_cliente;
        public LinearLayout provedorView;
        public TextView bar_proveedor;
        public ImageView imagenProveedor;
        public ListView listaDetalle;
        private LinearLayout layoutAnimado;
        public CardView detalleRecarga;


        public ViewHolder(View v) {
            super(v);
            nombre_proveedor = (TextView) v.findViewById(R.id.nombre_proeevdor);
            saldo_cliente = (TextView) v.findViewById(R.id.saldo_cliente);
            provedorView = (LinearLayout) v.findViewById(R.id.provedorView);
            bar_proveedor = (TextView) v.findViewById(R.id.bar_proeevdor);
            imagenProveedor = (ImageView) v.findViewById(R.id.imagenProveedor);
            listaDetalle = (ListView) v.findViewById(R.id.listViewDetalle);
            detalleRecarga = (CardView) v.findViewById(R.id.detalleRecarga);


        }
    }


    public AdaptadorRecargas() {

    }

    @Override
    public int getItemCount() {

              return FragmentoRecargas.lista_result.size() ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_recarga, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int contador) {

        viewHolder.detalleRecarga.setVisibility(View.GONE);
        ProveedorServicio item = FragmentoRecargas.lista_result.get(contador);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://easypaybar.appspot.com/")
                .child(item.getImagen());

        final File localFile;
        try {
            localFile = File.createTempFile("images"+item.getNombre_proveedor().toString(), "jpg");
            final ViewHolder viewHolderAuxi = viewHolder;
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                        viewHolderAuxi.imagenProveedor.setImageBitmap(bitmap);
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }



        viewHolder.nombre_proveedor.setText(item.getNombre_proveedor());
        viewHolder.saldo_cliente.setText(item.getSaldo_cliente()+ "$");
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
        System.out.println("ABCD: "+detalleRecargas.size() );

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
