package com.example.henryf.pryeasypaybar;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Fragmento para la pestaña "PERFIL" De la sección "Mi Cuenta"
 */
public class FragmentoPerfil extends Fragment {

    private TextView lblnombre;
    private TextView lblcorreo;
    private ImageView viewfoto;
    private FirebaseAuth firebaseAuth;
    private TextView lblfecha;
    private TextView lblproducto;
    private TextView lblcosto;

    public FragmentoPerfil() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        View fragmentoView = inflater.inflate(R.layout.fragmento_perfil, container, false);
        lblnombre = (TextView) fragmentoView.findViewById(R.id.texto_nombre);
        lblcorreo = (TextView) fragmentoView.findViewById(R.id.texto_email);
        viewfoto = (ImageView) fragmentoView.findViewById(R.id.foto);

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


        return fragmentoView;
    }

}
