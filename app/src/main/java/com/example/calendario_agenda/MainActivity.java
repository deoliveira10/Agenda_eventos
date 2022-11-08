package com.example.calendario_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{

    Button FinalizarSessao;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_Adiciona = (Button) findViewById(R.id.btn_Adiciona);
        Button btn_Agenda = (Button) findViewById(R.id.btn_Agenda);
        FinalizarSessao = findViewById(R.id.FinalizarSessao);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        btn_Adiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityAdd.class);
                startActivity(intent);
            }
        });

        btn_Agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityAgenda.class);
                startActivity(intent);
            }
        });

        FinalizarSessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SairAplicacao();
            }
        });


    }

    private void SairAplicacao() {
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, Login.class));
        Toast.makeText(this, "Encerrar sessão", Toast.LENGTH_SHORT).show();
    }
}