package com.example.mensagens.controller.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mensagens.R;
import com.example.mensagens.controller.activity.MainActivity;
import com.example.mensagens.controller.activity.conversa.ConversasActivity;
import com.example.mensagens.model.Usuario;
import com.example.mensagens.service.android.SocketService;
import com.example.mensagens.service.interno.LoginService;
import com.example.mensagens.util.Constantes;
import com.example.mensagens.util.asynctask.AsyncTaskImpl;
import com.example.mensagens.util.asynctask.AsyncTaskOnFinishListener;
import com.example.mensagens.util.asynctask.AsyncTaskParams;
import com.example.mensagens.util.asynctask.AsyncTaskResult;
import com.example.mensagens.util.web.HttpUtils;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginService = new LoginService(this);
    }

    public void login(View view) {
        EditText textLogin = findViewById(R.id.login_edit_login);
        EditText textSenha = findViewById(R.id.login_edit_senha);

        String login = textLogin.getText().toString();
        String senha = textSenha.getText().toString();

        Usuario usuario = new Usuario();
        usuario.login = login;
        usuario.senha = senha;

        AsyncTaskParams params = new AsyncTaskParams(this);
        params.put("url", Constantes.HOST + "/login");
        params.put("json", usuario.getAutenticacao());
        AsyncTaskImpl asyncTask = new HttpUtils.HttpPostHelper()
                .setOnFinishListener(new LoginActivity.LoginOnFinishListener());
        asyncTask.execute(params);
    }

    private static class LoginOnFinishListener implements AsyncTaskOnFinishListener {

        @Override
        public void onFinish(AsyncTaskResult resultado) {
            LoginActivity ctx = resultado.getContext();
            AsyncTaskParams params = resultado.getParams();
            RelativeLayout loginLayout = ctx.findViewById(R.id.login_layout);
            String msg = params.getParam("resposta");

            if (params.getParam("sucesso")) {
                // msg nesse caso é token de login
                ctx.loginService.setUsuario(msg);

                ctx.startActivity(new Intent(ctx, ConversasActivity.class));
            } else {
                Snackbar.make(loginLayout, "Erro ao logar!", Snackbar.LENGTH_LONG).show();
                new AlertDialog.Builder(ctx).setMessage(msg).create().show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PreLogin.class));
    }
}
