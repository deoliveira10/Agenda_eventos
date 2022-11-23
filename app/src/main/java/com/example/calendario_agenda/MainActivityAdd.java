package com.example.calendario_agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivityAdd extends AppCompatActivity {

    EditText EventoET, LocalET, HorarioET, DataET;
    Button Btn_Salvar;
    FirebaseDatabase firebaseDatabase;

    ProgressDialog progressDialog;

    //
    String evento = "" , local = "" , horario = "" , data = "" ;

@Override
protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_add);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Calendário");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        EventoET = findViewById(R.id.EventoET);
        LocalET = findViewById(R.id.LocalET);
        HorarioET = findViewById(R.id.HorarioET);
        DataET = findViewById(R.id.DataET);
        Btn_Salvar = findViewById(R.id.Btn_Salvar);



     firebaseDatabase = FirebaseDatabase.getInstance();

     Btn_Salvar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            SalvarEvento();


         }
     });

        }

        private void SalvarEvento(){

            evento = EventoET.getText().toString();
            local = LocalET.getText().toString();
            horario = HorarioET.getText().toString();
            data = DataET.getText().toString();

            if (TextUtils.isEmpty(evento)){
                Toast.makeText(this, "Informe o nome do evento", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(local)){
                Toast.makeText(this, "informe o local", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(horario)){
                Toast.makeText(this, "Informe o Horario", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(data)) {
                Toast.makeText(this, "Informe a data", Toast.LENGTH_SHORT).show();
            }


            HashMap<String, String> Eventos = new HashMap<>();

            Eventos.put("Evento", evento);
            Eventos.put("Local", local);
            Eventos.put("Horario", horario);
            Eventos.put("Data", data);

            firebaseDatabase.getReference("Eventos")
                    .child(evento)
                    .setValue(Eventos)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivityAdd.this, "Evento criado com Sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivityAdd.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }



    @Override
    public boolean onSupportNavigateUp() {
    onBackPressed();
        return super.onSupportNavigateUp();
    }
};