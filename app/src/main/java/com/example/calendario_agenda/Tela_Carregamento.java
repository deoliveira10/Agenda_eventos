package com.example.calendario_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Tela_Carregamento extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_carregamento);

        firebaseAuth = FirebaseAuth.getInstance();

        int Tempo = 3000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*startActivity(new Intent(getBaseContext(), Login.class));
                finish();*/
                VerificarUsuario();
            }
        },Tempo);

    }

    private void VerificarUsuario(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null){
            startActivity(new Intent(Tela_Carregamento.this, Login.class));
            finish();
        } else {
            startActivity(new Intent(Tela_Carregamento.this, MainActivity.class));
            finish();
        }
    }
}