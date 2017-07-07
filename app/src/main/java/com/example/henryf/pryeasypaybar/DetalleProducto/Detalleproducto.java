package com.example.henryf.pryeasypaybar.DetalleProducto;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button btn_comentar;
    private EditText txt_comentario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalleproducto);
        imgProducto = (ImageView) findViewById(R.id.imgProducto);
        nombreProducto = (TextView) findViewById(R.id.nombreProducto);
        btn_comentar=(Button) findViewById(R.id.btn_comentar);
        txt_comentario=(EditText) findViewById(R.id.txt_comentario);
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

        btn_comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                producto.Comentar(txt_comentario.getText().toString(),producto.getUidproveedor().toString(),producto.getKey());
                txt_comentario.setText(null);
            }
        });
    }
}
