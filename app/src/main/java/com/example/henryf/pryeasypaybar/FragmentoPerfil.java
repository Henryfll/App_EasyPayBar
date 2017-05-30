package com.example.henryf.pryeasypaybar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


import static com.example.henryf.pryeasypaybar.R.id.imageView;

/**
 * Fragmento para la pestaña "PERFIL" De la sección "Mi Cuenta"
 */
public class FragmentoPerfil extends Fragment {

    private TextView lblnombre;
    private TextView lblcorreo;
    private ImageView viewfoto;
    private FirebaseAuth firebaseAuth;

    public FragmentoPerfil() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentoView = inflater.inflate(R.layout.fragmento_perfil, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        lblnombre = (TextView) fragmentoView.findViewById(R.id.texto_nombre);
        lblcorreo = (TextView) fragmentoView.findViewById(R.id.texto_email);
        viewfoto = (ImageView) fragmentoView.findViewById(R.id.foto);
        //System.out.println("problema: "+R.id.texto_nombre);
        lblnombre.setText(user.getDisplayName());
        lblcorreo.setText(user.getEmail());
        WindowManager wm = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated
        System.out.println("problema: "+width + height);
        Picasso.with(getContext()).load(user.getPhotoUrl()).centerCrop().resize(display.getWidth()/3, display.getHeight()/5).into(viewfoto);

        /*BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        System.out.println("problema: "+user.getPhotoUrl().toString());
        Bitmap imagen = BitmapFactory.decodeFile(user.getPhotoUrl().toString(), options);
        Bitmap imagen = BitmapFactory.decodeFile(user.getPhotoUrl().getPath());
        viewfoto.setImageBitmap(imagen);*/
        //viewfoto.setImageURI(user.getPhotoUrl());
        //System.out.println("problema: "+nombre+" "+correo);
        return fragmentoView;
    }

}
