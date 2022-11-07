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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {

    EditText NomeET, EmailET, SenhaET, ConfirmaSenhaEt;
    Button RegistraUsuario;
    TextView SouCadastrado;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    //
    String nome = "" , email = "" , senha = "" , confirmarsenha = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registrar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        NomeET = findViewById(R.id.NomeET);
        EmailET = findViewById(R.id.EmailET);
        SenhaET = findViewById(R.id.SenhaET);
        ConfirmaSenhaEt = findViewById(R.id.ConfirmaSenhaET);
        RegistraUsuario = findViewById(R.id.RegistraUsuario);
        SouCadastrado = findViewById(R.id.SouCadastrado);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setTitle("Aguarde");
        progressDialog.setCanceledOnTouchOutside(false);

        RegistraUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarDados();
            }
        });

        SouCadastrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registro.this, Login.class));
            }
        });
    }

    private void ValidarDados(){
        nome = NomeET.getText().toString();
        email = EmailET.getText().toString();
        senha = SenhaET.getText().toString();
        confirmarsenha = ConfirmaSenhaEt.getText().toString();

        if (TextUtils.isEmpty(nome)){
            Toast.makeText(this, "Coloque seu nome", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Coloque seu email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(senha)){
            Toast.makeText(this, "Coloque sua senha", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(confirmarsenha)) {
            Toast.makeText(this, "Confirme sua senha", Toast.LENGTH_SHORT).show();
        }
        else if (!senha.equals(confirmarsenha)){
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
        }
        else {
            CriarConta();
        }
    }

    private void CriarConta() {
        progressDialog.setMessage("Criando sua conta");
        progressDialog.show();

        //criar um usuário em Firebase
        firebaseAuth.createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //
                        GuardarInformacao();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void GuardarInformacao() {
        progressDialog.setMessage("Guardando suas informações");
        progressDialog.dismiss();

        //Obter a identificação do usuario atual
        String uid = firebaseAuth.getUid();

        HashMap<String, String> Dados = new HashMap<>();
        Dados.put("uid", uid);
        Dados.put("email", email);
        Dados.put("nome", nome);
        Dados.put("senha", senha);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuários");
        databaseReference.child(uid)
                .setValue(Dados)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, "Conta criada com Sucesso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registro.this, MenuPrincipal.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}