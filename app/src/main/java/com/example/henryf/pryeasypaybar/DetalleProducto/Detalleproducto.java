package com.example.henryf.pryeasypaybar.DetalleProducto;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.henryf.pryeasypaybar.R;
import com.example.henryf.pryeasypaybar.Servicios.ProductoProveedor;
import com.example.henryf.pryeasypaybar.Servicios.ProveedorServicio;

import org.w3c.dom.Text;

public class Detalleproducto extends AppCompatActivity {

    private ImageView imgProducto;
    private TextView nombreProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalleproducto);
        imgProducto = (ImageView) findViewById(R.id.imgProducto);
        nombreProducto = (TextView) findViewById(R.id.nombreProducto);
        Intent intent = getIntent();
        final ProductoProveedor producto = (ProductoProveedor) intent.getExtras().getSerializable("producto");

        Glide.with( this)
                .load(producto.getImagenURL().toString())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        nombreProducto.setText(producto.getNombre().toString());

                        return false;
                    }
                })
                .into(imgProducto);



    }
}
