package com.example.henryf.pryeasypaybar;

import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Diego Claudio on 27/02/2017.
 */

public class Cliente {
    public boolean admin;
    public String codigoQR;
    public String fecha_Afiliacion;
    public String nombre;
    public boolean proveedor;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public static ArrayList<String> proveedoresAfiliados = new ArrayList<>();
    public static ArrayList<Float> totalRecargas = new ArrayList<>();
    public static ArrayList<Float> totalSaldo = new ArrayList<>();



    public Cliente() {

    }

    public Cliente(String codigoQR, String nombre) {
        this.admin = false;
        this.codigoQR = codigoQR;
        Calendar fecha = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        this.fecha_Afiliacion = formato.format(fecha.getTime());
        this.nombre = nombre;
        this.proveedor = false;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public String getFecha_Afiliacion() {
        return fecha_Afiliacion;
    }

    public void setFecha_Afiliacion(String fecha_Afiliacion) {
        this.fecha_Afiliacion = fecha_Afiliacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isProveedor() {
        return proveedor;
    }

    public void setProveedor(boolean proveedor) {
        this.proveedor = proveedor;
    }

    public ArrayList<String> getProveedoresAfiliados() {
        return proveedoresAfiliados;
    }

    public void setProveedoresAfiliados(ArrayList<String> proveedoresAfiliados) {
        this.proveedoresAfiliados = proveedoresAfiliados;
    }

    public ArrayList<Float> getTotalRecargas() {
        return totalRecargas;
    }

    public void setTotalRecargas(ArrayList<Float> totalRecargas) {
        this.totalRecargas = totalRecargas;
    }

    public ArrayList<Float> getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(ArrayList<Float> totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public String fecha_Inicio(){
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        //Busca el registro del cliente con el Uid del mismo

        Query queryCliente = mFirebaseDatabase.child("cliente").orderByChild("codigoQR").equalTo(user.getUid());
        queryCliente.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    fecha_Afiliacion = child.child("fecha_Afiliacion").getValue().toString();
                }
                setFecha_Afiliacion(fecha_Afiliacion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return getFecha_Afiliacion();
    }

    public String recuperarDatos(){

        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("problema 1"+fecha_Inicio());
            long afiliacion = formato.parse(fecha_Inicio()).getTime();
            long diferenciaEn_ms = Calendar.getInstance().getTimeInMillis() - afiliacion;
            long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
            return "" + dias;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


    }
/*
    public ArrayList<Date> get_OchoUltimos() {
        Calendar fecha = Calendar.getInstance();
        ArrayList<Date> lista_Dias = new ArrayList<Date>();
        //for para los 8 dias
        for (int i =8; i>0; i--){
            lista_Dias.add(fecha.getTime());//guardo las fechas
            fecha.add(Calendar.DAY_OF_YEAR, -1);//resto un dia
        }
        return lista_Dias;
    }*/

    public void Consulta(){
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        //Busca el registro del cliente con el Uid del mismo

        mFirebaseDatabase.child("proveedor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> proveedores = new ArrayList<String>();
                ArrayList<Float> saldo = new ArrayList<>();
                ArrayList<Float> recarga = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(child.child("afiliados").child(user.getUid()).exists()){
                        float recargaTotal = 0;
                        for (DataSnapshot item : child.child("afiliados").child(user.getUid()).child("recarga").getChildren()) {
                            recargaTotal = recargaTotal+Float.parseFloat(item.child("valor").getValue().toString());
                        }
                        proveedores.add(child.child("bar").getValue().toString());
                        recarga.add(recargaTotal);
                        saldo.add(Float.parseFloat(child.child("afiliados").child(user.getUid()).child("saldo").getValue().toString()));
                    }
                }
                setProveedoresAfiliados(proveedores);
                setTotalRecargas(recarga);
                setTotalSaldo(saldo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
