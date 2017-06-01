package com.example.henryf.pryeasypaybar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class FragmentoGrafica extends Fragment {
    private PieChart pieChart;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private double saldoTotal = 0;
    /*creamos una lista para los valores Y*/
    /*public static ArrayList<Entry> valsY = new ArrayList<Entry>();
        /*valsY.add(new Entry(5* 100 / 25,0));
        valsY.add(new Entry(20 * 100 / 25,1));

    /*creamos una lista para los valores X
    public static ArrayList<String> valsX = new ArrayList<String>();

        /*valsX.add("Varones");
        valsX.add("Mujeres");*/

    public FragmentoGrafica() {
        // Required empty public constructor
    }

    /*public static void setLista_X(ArrayList<String> listax) {
        valsX = listax;
    }
    public static void setLista_Y(ArrayList<Entry> listay) {
        valsY = listay;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentoView = inflater.inflate(R.layout.fragmento_grafica, container, false);
        pieChart = (PieChart) fragmentoView.findViewById(R.id.pieChart);

        //definimos algunos atributos
        pieChart.setHoleRadius(40f);
        pieChart.setDrawYValues(true);
        pieChart.setDrawXValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);

        /*firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("proveedor");

        final ArrayList<String> valoresX = new ArrayList<String>();
        final ArrayList<Entry> valoresY = new ArrayList<Entry>();
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot c: dataSnapshot.getChildren()) {
                    try {
                        if (!c.child("afiliados").child(user.getUid()).getValue().equals("")){
                            valoresX.add(c.child("bar").getValue().toString());
                            valoresY.add(new Entry(Float.parseFloat(c.child("afiliados").child(user.getUid()).child("saldo").getValue().toString())* 100 / 25,0));
                            saldoTotal=saldoTotal+Double.parseDouble(c.child("afiliados").child(user.getUid()).child("saldo").getValue().toString());
                        }

                    }catch (Exception e){

                    }

                }
                setLista_X(valoresX);
                setLista_Y(valoresY);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });




 		/*creamos una lista de colores*/
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.bg_screen1));
        colors.add(getResources().getColor(R.color.bg_screen2));

        /*creamos una lista para los valores Y*/
        ArrayList<Entry> valsY = new ArrayList<Entry>();
            valsY.add(new Entry(5* 100 / 25,0));
            valsY.add(new Entry(20 * 100 / 25,1));

        /*creamos una lista para los valores X*/
        final ArrayList<String> valsX = new ArrayList<String>();
            valsX.add("Varones");
            valsX.add("Mujeres");

            /*seteamos los valores de Y y los colores*/
        PieDataSet set1 = new PieDataSet(valsY, "Resultados");
        set1.setSliceSpace(3f);
        set1.setColors(colors);

		/*seteamos los valores de X*/
        PieData data = new PieData(valsX, set1);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();

        /*Ocutar descripcion*/
        pieChart.setDescription("");
        /*Ocultar leyenda*/
        pieChart.setDrawLegend(false);
        return fragmentoView;
    }


}
