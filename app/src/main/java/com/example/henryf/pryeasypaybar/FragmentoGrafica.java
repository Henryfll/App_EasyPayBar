package com.example.henryf.pryeasypaybar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;



public class FragmentoGrafica extends Fragment {
    private PieChart pieChart;
    private BarChart barChart;

    private float saldoTotal = 0;


    public FragmentoGrafica() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentoView = inflater.inflate(R.layout.fragmento_grafica, container, false);
        pieChart = (PieChart) fragmentoView.findViewById(R.id.pieChart);
        barChart = (BarChart) fragmentoView.findViewById(R.id.chart);

        //definimos algunos atributos
        pieChart.setHoleRadius(40f);
        pieChart.setDrawYValues(true);
        pieChart.setDrawXValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);


        /*creamos una lista de colores*/
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.bg_screen1));
        colors.add(getResources().getColor(R.color.graf_celeste));
        colors.add(getResources().getColor(R.color.graf_verde));
        colors.add(getResources().getColor(R.color.graf_morado));
        colors.add(getResources().getColor(R.color.graf_verdeClaro));
        colors.add(getResources().getColor(R.color.graf_amarillo));
        colors.add(getResources().getColor(R.color.graf_azul));
        colors.add(getResources().getColor(R.color.graf_rojo));

        /*creamos una lista para los valores Y*/
        ArrayList<Entry> valsY = new ArrayList<Entry>();
        ArrayList<BarEntry> entries = new ArrayList<>();

        /*creamos una lista para los valores X*/
        final ArrayList<String> valsX = new ArrayList<String>();

        int cont=0;
        for (ProveedorServicio lista: FragmentoRecargas.lista_result
             ) {
            valsX.add(lista.getBar_proveedor());
            valsY.add(new Entry(Float.parseFloat(lista.getSaldo_cliente()),cont));
            entries.add(new BarEntry(Float.parseFloat(lista.getSaldo_cliente()),cont));
            saldoTotal=saldoTotal+Float.parseFloat(lista.getSaldo_cliente());
            cont++;

        }

            /*seteamos los valores de Y y los colores*/
        PieDataSet set1 = new PieDataSet(valsY, "Resultados");
        BarDataSet dataset = new BarDataSet(entries, "Saldo por proveedor");
        dataset.setColors(colors);
        BarData dataBarras = new BarData(valsX, dataset);
        // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        barChart.setData(dataBarras);
        barChart.animateY(5000);
        barChart.highlightValues(null);
        barChart.invalidate();

        set1.setSliceSpace(3f);
        set1.setColors(colors);

		//seteamos los valores de X
        PieData dataPastel = new PieData(valsX, set1);
        pieChart.setData(dataPastel);
        pieChart.highlightValues(null);
        pieChart.invalidate();

        //Ocutar descripcion
        pieChart.setDescription("Saldo por Proveedor");
        //Ocultar leyenda
        pieChart.setDrawLegend(false);
        return fragmentoView;
    }


}
