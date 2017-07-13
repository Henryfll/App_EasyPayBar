package com.example.henryf.pryeasypaybar;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Button;
import android.graphics.Bitmap;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeEncoder;


/**
 * Fragmento para la pestaña "TARJETAS" de la sección "Mi Cuenta"
 */
public class FragmentoQr extends Fragment{

    ImageView imageView;

    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;

    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final FirebaseUser user = firebaseAuth.getCurrentUser();

    public FragmentoQr() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // write(container);
        View rootView = inflater.inflate(R.layout.fragmento_qr, container, false);
        imageView = (ImageView)  rootView.findViewById(R.id.imageView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarQR);
        progressBar.setVisibility(View.GONE);
       /* Creacion de hilo */
        Async qrAsync = new Async();
        qrAsync.execute();
        return rootView;
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

    /*Implementacion de Metodos de hilo  para crear Codigo QR*/

    private class Async extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... params) {
           bitmap =  TextoToQr(user.getUid());
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            imageView.setImageBitmap(bitmap);
        }
        @Override
        protected void onCancelled() {
            
        }

    }


}
