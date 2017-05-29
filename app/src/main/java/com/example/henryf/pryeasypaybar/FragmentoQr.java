package com.example.henryf.pryeasypaybar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Button;
import android.graphics.Bitmap;


import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;


/**
 * Fragmento para la pestaña "TARJETAS" de la sección "Mi Cuenta"
 */
public class FragmentoQr extends Fragment{

    ImageView imageView;
    Button btn_generear;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap ;
    private FirebaseUser usuario;

    public FragmentoQr() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // write(container);
        View rootView = inflater.inflate(R.layout.fragmento_qr, container, false);
        imageView = (ImageView)  rootView.findViewById(R.id.imageView);
        btn_generear = (Button) rootView.findViewById(R.id.btn_generar);
        btn_generear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle argsBundle =  getArguments();

                if(argsBundle != null) {
                    String var_value = argsBundle.getString("data");
                    System.out.println("Data"+var_value);
                    System.out.println("Data: if");

                    try {
                        bitmap = TextToImageEncode(var_value);
                        imageView.setImageBitmap(bitmap);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }

            }
        });




        System.out.println("CodigoQR");
        return rootView;
    }
    public void CodigoQR(){
        try {
            bitmap = TextToImageEncode(usuario.getUid());
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }





}
