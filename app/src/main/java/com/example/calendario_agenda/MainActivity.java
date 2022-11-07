package com.example.calendario_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_Adiciona = (Button) findViewById(R.id.btn_Adiciona);
        Button btn_Agenda = (Button) findViewById(R.id.btn_Agenda);

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


    }
}