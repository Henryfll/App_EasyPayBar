package com.example.henryf.pryeasypaybar.MenuProveedor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.henryf.pryeasypaybar.R;

/**
 * Created by Fabian on 26/06/2017.
 */

public class FragmentoMenuProveedor extends Fragment {

    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorMenuP adaptador;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_menu, container, false);
/*
        reciclador = (RecyclerView) view.findViewById(R.id.recyclerViewCategoria);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);
        adaptador = new AdaptadorMenuP();
        reciclador.setAdapter(adaptador);
        */
        return view;

    }
}
