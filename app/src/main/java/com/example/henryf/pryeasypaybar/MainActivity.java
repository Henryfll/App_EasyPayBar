package com.example.henryf.pryeasypaybar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

import com.example.henryf.pryeasypaybar.PingSeguridad.PingSeguridad;
import com.facebook.login.LoginManager;
import android.net.Uri;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;

import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {



    private String userId;
    private DrawerLayout drawerLayout;
    private FirebaseUser usuario;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    final Cliente cliente = new Cliente();

    private String name;
    private  String fechaAf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        final TextView fechaAfiliacion = (TextView) hView.findViewById(R.id.fechaAf);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        cliente.Consulta();




        if (user != null) {

            mFirebaseInstance = FirebaseDatabase.getInstance();
            mFirebaseDatabase = mFirebaseInstance.getReference();

            mFirebaseDatabase.child("cliente").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot cliente: dataSnapshot.getChildren()){
                        if((cliente.child("codigoQR").getValue().toString()).equals(user.getUid().toString())){


                                Date date = new Date();
                                try {
                                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(cliente.child("fecha_Afiliacion").getValue().toString());
                                    long diferencia = (date.getTime() - date1.getTime()) / 86400000;
                                    long años = diferencia / 365;
                                    long meses = (diferencia - (años * 365)) / 30;
                                    long dias = diferencia - (años * 365) - (meses * 30);
                                    fechaAfiliacion.setText(dias + " dias," + meses + " meses, " + años + " años");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }


                    }



                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            name = user.getDisplayName();
            usuario = user;



        } else {
            goLoginScreen();
        }

        agregarToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Insertar Nombre de usuario
        final TextView nombre_Usuario = (TextView) hView.findViewById(R.id.nombre_Usuario);
        nombre_Usuario.setText(name);

        //Insertar imagen redondeada
        final ImageView nav_img = (ImageView) hView.findViewById(R.id.imagen_Cliente);
        String facebookUserId="";
        for(UserInfo profile : user.getProviderData()) {
            facebookUserId = profile.getUid();
                    }
        System.out.println("uid :"+facebookUserId);
        String url = "https://graph.facebook.com/" + facebookUserId + "/picture?height=500";
        WindowManager wm = (WindowManager) hView.getContext().getSystemService(hView.getContext().WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Picasso.with(hView.getContext())
                .load(url)
                .centerCrop()
                .resize(display.getWidth()/5, display.getHeight()/8)
                .into(nav_img, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) nav_img.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        nav_img.setImageDrawable(imageDrawable);
                    }
                    @Override
                    public void onError() {
                        //viewfoto.setImageResource(R.drawable.default_image);
                    }
                });




        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
    }
    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }
    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;

        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new FragmentoInicio();
                break;
            case R.id.item_cuenta:
                fragmentoGenerico = new FragmentoCuenta(usuario);
                break;
            case R.id.item_salir:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.logout)
                        .setMessage("Desea Salir?")
                        .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                                goLoginScreen();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                //FirebaseAuth.getInstance().signOut();
                //LoginManager.getInstance().logOut();
                //goLoginScreen();
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }
    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void goPingScreen() {
        Intent intent = new Intent(this, PingSeguridad.class);
        startActivity(intent);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
