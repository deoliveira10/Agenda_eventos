package com.example.calendario_agenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Entrar extends AppCompatActivity {

    EditText EmailLogin, PasswordEntra;
    Button Btn_Entra;
    TextView UsuarioNovoTXT;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    String email = "" , password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Entrar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        EmailLogin = findViewById(R.id.EmailLogin);
        PasswordEntra = findViewById(R.id.PasswordEntra);
        Btn_Entra = findViewById(R.id.Btn_Entra);
        UsuarioNovoTXT = findViewById(R.id.UsuarioNovoTXT);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Entrar.this);
        progressDialog.setTitle("Aguarde");
        progressDialog.setCanceledOnTouchOutside(false);

        Btn_Entra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarDados();
            }
        });

        UsuarioNovoTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Entrar.this, Registro.class));
            }
        });

    }

    private void ValidarDados() {
        email = EmailLogin.getText().toString();
        password = PasswordEntra.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Insira sua senha", Toast.LENGTH_SHORT).show();
        }
        else {
            LoginDeUsuario();
        }
    }

    private void LoginDeUsuario() {
        progressDialog.setMessage("iniciando sessão...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(Entrar.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(Entrar.this, MainActivity.class));
                            Toast.makeText(Entrar.this, "Bem vindo(a)", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(Entrar.this, "Verifique se usuario e senha estão corretos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Entrar.this, ""+e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}