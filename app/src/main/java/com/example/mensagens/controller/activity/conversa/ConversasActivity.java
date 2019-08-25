package com.example.mensagens.controller.activity.conversa;

import android.os.Bundle;

import com.example.mensagens.service.interno.LoginService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.mensagens.R;

public class ConversasActivity extends AppCompatActivity {

    private LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);

        loginService = new LoginService(this);

        FloatingActionButton fab = findViewById(R.id.conversas_fab_nova_conversa);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new  AlertDialog.Builder(ConversasActivity.this)
                        .setMessage(loginService.getUsuaio().toJson()).create().show();
            }
        });
    }

}
