package com.example.henryf.pryeasypaybar;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
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
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private ProgressBar progressBar;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.loginButton);

        loginButton.setReadPermissions(Arrays.asList("email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    goMainScreen();
                    mFirebaseInstance = FirebaseDatabase.getInstance();
                    mFirebaseDatabase = mFirebaseInstance.getReference();

                    //Busca el registro del cliente con el Uid del mismo
                    Query queryCliente = mFirebaseDatabase.child("cliente").orderByChild("codigoQR").equalTo(user.getUid());
                    queryCliente.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                //Si encuentra regitro no hace nada mas
                            }else {
                                //Como no esta registrado el cliente procede al registro en firebase
                                userId = mFirebaseDatabase.push().getKey();//genera la key unica de cada cliente
                                Cliente cliente = new Cliente(user.getUid(), user.getDisplayName()); //instancia de cliente
                                mFirebaseDatabase.child("cliente").child(userId).setValue(cliente);//guarda la informacion en firebase
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                /*mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            for (DataSnapshot c: dataSnapshot.getChildren()) {
                                Cliente cli = c.getValue(Cliente.class);
                                //System.out.println("Cliente :"+cli.codigoQR);
                                if(user.getUid() == cli.codigoQR){
                                    cont++;
                                }
                            }
                            if (cont == 0){
                                userId = mFirebaseDatabase.push().getKey();
                                Cliente cliente = new Cliente(user.getUid(), user.getDisplayName());
                                mFirebaseDatabase.child(userId).setValue(cliente);
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                   /* mFirebaseDatabase.addChildEventListener(new ChildEventListener() {

                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                            Cliente cli = dataSnapshot.getValue(Cliente.class);
                            if (user.getUid() == cli.codigoQR)
                                cont++;

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {}

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });

                    if (cont == 0) {
                        userId = mFirebaseDatabase.push().getKey();
                        Cliente cliente = new Cliente(user.getUid(), user.getDisplayName());
                        mFirebaseDatabase.child(userId).setValue(cliente);
                    }*/


                }
            }
        };


    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.firebase_error_login, Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void goMainScreen() {
        System.out.println("pruebaInicio de app");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }
}