package com.example.henryf.pryeasypaybar;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import rm.com.longpresspopup.LongPressPopup;
import rm.com.longpresspopup.LongPressPopupBuilder;
import rm.com.longpresspopup.PopupInflaterListener;
import rm.com.longpresspopup.PopupOnHoverListener;
import rm.com.longpresspopup.PopupStateListener;

/**
 * Fragmento para la pestaña "PERFIL" De la sección "Mi Cuenta"
 */
public class FragmentoPerfil extends Fragment implements PopupInflaterListener,
        PopupStateListener, PopupOnHoverListener, View.OnClickListener {

    private static final String TAG = FragmentoPerfil.class.getSimpleName();
    private Bitmap bitmap ;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = firebaseAuth.getCurrentUser();
    private ImageView imageView;

    private RecyclerView recyclerView;

    public static ArrayList<ProveedorServicio> lista_result = new ArrayList<ProveedorServicio>();

    public FragmentoPerfil() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        View fragmentoView = inflater.inflate(R.layout.fragmento_perfil, container, false);
        TextView lblnombre = (TextView) fragmentoView.findViewById(R.id.texto_nombre);
        TextView lblcorreo = (TextView) fragmentoView.findViewById(R.id.texto_email);
        ImageView viewfoto = (ImageView) fragmentoView.findViewById(R.id.foto);

        recyclerView = (RecyclerView) fragmentoView.findViewById(R.id.comprasRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //compras inicio
        final ArrayList<ProveedorServicio> lista_recargas = new ArrayList<>();
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        //final FirebaseUser user = firebaseAuth.getCurrentUser();
        //Consulta todos los proveedores
        mFirebaseDatabase.child("proveedor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot proveedor : dataSnapshot.getChildren()) {
                    try {
                        System.out.println("pruebaProveedor: "+proveedor.child("nombre").getValue().toString());
                        if(proveedor.child("afiliados").child(user.getUid()).getValue() != null) {

                            ArrayList<String> listDetalle = new ArrayList<String>();
                            for(DataSnapshot detalleCompra: proveedor.child("afiliados").child(user.getUid()).child("compras").getChildren()){
                                Compra compra = detalleCompra.getValue(Compra.class);
                               // System.out.println("Recarga"+ recarga.getFecha_Recarga());
                               // System.out.println("DetalleRecarga: "+detalleRecarga.getValue());
                                listDetalle.add(""+compra.getFecha_Compra() +"           "+ compra.getTotal()+" $");
                            }


                            lista_recargas.add(new
                                    ProveedorServicio(
                                    proveedor.child("nombre").getValue().toString(),
                                    proveedor.child("afiliados").child(user.getUid()).child("saldo").getValue().toString(),
                                    proveedor.child("bar").getValue().toString(),
                                    proveedor.child("imagen").getValue().toString(),
                                    proveedor.child("codigoQR").getValue().toString(),
                                    listDetalle,
                                    true,
                                    null,
                                    proveedor.child("imagenURL").getValue().toString()

                            ));


                        }

                    }catch (Exception e){
                        System.out.println("Error: "+e.getMessage());
                    }

                }

                recyclerView.setAdapter(new AdaptadorCompras(lista_recargas));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //compras fin



        // You can also chain it to the .build() mehod call above without declaring the "popup" variable before
        // From this moment, the touch events are registered and, if long pressed, will show the given view inside the popup

        //System.out.println("problema: "+R.id.texto_nombre);
        lblnombre.setText(user.getDisplayName());
        lblcorreo.setText(user.getEmail());
        WindowManager wm = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        String facebookUserId="";
        for(UserInfo profile : user.getProviderData()) {
            facebookUserId = profile.getUid();

        }


            String url = "https://graph.facebook.com/" + facebookUserId + "/picture?height=500";
            Picasso.with(getContext())
                    .load(url)
                    .centerCrop()
                    .resize(display.getWidth()/3, display.getHeight()/5)
                    .into(viewfoto);
        LongPressPopup popup = new LongPressPopupBuilder(fragmentoView.getContext())
                .setTarget(viewfoto)
                //.setPopupView(textView)// Not using this time
                .setPopupView(R.layout.popup_layout, this)
                .setLongPressDuration(750)

                .setCancelTouchOnDragOutsideView(true)
                .setLongPressReleaseListener(this)
                .setOnHoverListener(this)
                .setPopupListener(this)
                .setTag("PopupFoo")
                .setAnimationType(LongPressPopup.ANIMATION_TYPE_FROM_CENTER)
                .build();

        // You can also chain it to the .build() mehod call above without declaring the "popup" variable before
        popup.register();

        return fragmentoView;
    }

    Bitmap TextoToQr(String string)
    {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(string, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            return  bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class Async extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            bitmap =  TextoToQr(user.getUid());
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            imageView.setImageBitmap(bitmap);
        }
        @Override
        protected void onCancelled() {

        }

    }

    // Popup inflater listener
    @Override
    public void onViewInflated(@Nullable String popupTag, View root) {

        imageView = (ImageView) root.findViewById(R.id.img_PerfilPopup);
    }


    // Touch released on a View listener
    @Override
    public void onClick(View view) {

    }


    // PopupStateListener
    @Override
    public void onPopupShow(@Nullable String popupTag) {
       Async qrAsync = new Async();
        qrAsync.execute();

    }

    @Override
    public void onPopupDismiss(@Nullable String popupTag) {
        //Toast.makeText(getContext(), "Popup dismissed!", Toast.LENGTH_SHORT).show();
    }


    // Hover state listener
    @Override
    public void onHoverChanged(View view, boolean isHovered) {
        Log.e(TAG, "Hover change: " + isHovered + " on View " + view.getClass().getSimpleName());
    }



}
