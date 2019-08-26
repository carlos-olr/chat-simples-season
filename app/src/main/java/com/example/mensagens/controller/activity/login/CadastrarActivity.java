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
import com.example.mensagens.model.Usuario;
import com.example.mensagens.util.Constantes;
import com.example.mensagens.util.asynctask.AsyncTaskImpl;
import com.example.mensagens.util.asynctask.AsyncTaskOnFinishListener;
import com.example.mensagens.util.asynctask.AsyncTaskParams;
import com.example.mensagens.util.asynctask.AsyncTaskResult;
import com.example.mensagens.util.web.HttpUtils;
import com.google.android.material.snackbar.Snackbar;

public class CadastrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
    }

    public void cadastrar(View view) {
        EditText textLogin = findViewById(R.id.cadastrar_edit_login);
        EditText textSenha = findViewById(R.id.cadastrar_edit_senha);
        EditText textConfirmarSenha = findViewById(R.id.cadastrar_edit_confirmar_senha);

        String login = textLogin.getText().toString();
        String senha = textSenha.getText().toString();
        String confirmarSenha = textConfirmarSenha.getText().toString();

        Usuario usuario = new Usuario();
        usuario.login = login;
        usuario.senha = senha;
        usuario.confirmarSenha = confirmarSenha;

        AsyncTaskParams params = new AsyncTaskParams(this);
        params.put("url", Constantes.HOST + "/salvarUsuario");
        params.put("json", usuario.toJson());
        AsyncTaskImpl asyncTask = new HttpUtils.HttpPostHelper()
                .setOnFinishListener(new CadastrarOnFinishListener());
        asyncTask.execute(params);
    }

    private static class CadastrarOnFinishListener implements AsyncTaskOnFinishListener {

        @Override
        public void onFinish(AsyncTaskResult resultado) {
            CadastrarActivity ctx = resultado.getContext();
            AsyncTaskParams params = resultado.getParams();
            RelativeLayout cadastrarLayout = ctx.findViewById(R.id.cadastrar_layout);

            if (params.getParam("sucesso")) {
                Snackbar.make(cadastrarLayout, "Usuário criado com sucesso!", Snackbar.LENGTH_LONG).show();
                ctx.startActivity(new Intent(ctx, LoginActivity.class));
            } else {
                Snackbar.make(cadastrarLayout, "Erro ao criar usuário!", Snackbar.LENGTH_LONG).show();

                String msg = params.getParam("resposta");
                new AlertDialog.Builder(ctx).setMessage(msg).create().show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
