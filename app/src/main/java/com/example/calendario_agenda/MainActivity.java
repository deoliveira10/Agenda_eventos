package com.example.calendario_agenda;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    Button FinalizarSessao;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    CalendarView calendario;

    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference BASE_DE_DADOS;
    ArrayList<Eventos> proximos;

    LinearLayoutManager linearLayoutManager;
     MyAdapter myAdapter;


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

        listView = findViewById(R.id.ListView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        BASE_DE_DADOS = firebaseDatabase.getReference("Eventos");
        listView.setHasTransientState(true);

        proximos = new ArrayList<>();



    }

    private void SairAplicacao() {
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, Login.class));
        Toast.makeText(this, "Encerrar sessão", Toast.LENGTH_SHORT).show();
    }
}