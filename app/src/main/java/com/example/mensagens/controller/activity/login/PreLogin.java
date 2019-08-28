package com.example.mensagens.controller.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mensagens.R;
import com.example.mensagens.controller.activity.conversa.ConversasActivity;

public class PreLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void acessarLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void acessarCadastro(View view) {
        startActivity(new Intent(this, CadastrarActivity.class));
    }

}
