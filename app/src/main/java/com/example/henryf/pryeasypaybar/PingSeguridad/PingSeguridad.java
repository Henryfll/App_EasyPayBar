package com.example.henryf.pryeasypaybar.PingSeguridad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henryf.pryeasypaybar.Cliente;
import com.example.henryf.pryeasypaybar.MainActivity;
import com.example.henryf.pryeasypaybar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class PingSeguridad extends AppCompatActivity {

    private EditText txt_ping;
    private  EditText txt_pingConfirmar;
    private TextView txt_error;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth firebaseAuth;
    private String ping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_seguridad);

     txt_ping =  (EditText) findViewById(R.id.txt_ping);
      txt_pingConfirmar =  (EditText) findViewById(R.id.txt_pingConfirmacion);
        txt_error = (TextView) findViewById(R.id.txt_error);
        Button confirmar = (Button) findViewById(R.id.btn_confirmar);


       confirmar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ping = txt_ping.getText().toString();
               String pingConfirmar = txt_pingConfirmar.getText().toString();
               if (ping.equals(pingConfirmar)) {

                   firebaseAuth = FirebaseAuth.getInstance();
                   final FirebaseUser user = firebaseAuth.getCurrentUser();
                   mFirebaseInstance = FirebaseDatabase.getInstance();
                   mFirebaseDatabase = mFirebaseInstance.getReference();

                   mFirebaseDatabase.child("cliente").addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {

                           for(DataSnapshot cliente: dataSnapshot.getChildren()){
                               if((cliente.child("codigoQR").getValue().toString()).equals(user.getUid().toString())){
                                    System.out.println("DatosIngresados:");
                                   mFirebaseDatabase.child("cliente").child(cliente.getKey()).child("ping").setValue(ping);
                            finish();
                               }
                           }

                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }

                   });
               }else {
                   txt_error.setText("Confirmacion de ping incorrecta");
               }
           }
       });


    }
}
