package com.example.henryf.pryeasypaybar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentoPerfil extends Fragment {
    private TextView nameTextView;
    private TextView emailTextView;
    public FragmentoPerfil() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmento_perfil, container, false);
        //nameTextView = (TextView) findViewById(R.id.titulo_informacion_usuario);
        //emailTextView = (TextView) findViewById(R.id.emailTextView);
    }
}
