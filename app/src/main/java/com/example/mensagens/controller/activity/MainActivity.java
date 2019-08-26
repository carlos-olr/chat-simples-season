package com.example.mensagens.controller.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mensagens.R;
import com.example.mensagens.controller.activity.conversa.ConversasActivity;
import com.example.mensagens.controller.activity.login.PreLogin;
import com.example.mensagens.service.interno.LoginService;
import com.example.mensagens.util.asynctask.AsyncTaskImpl;
import com.example.mensagens.util.asynctask.AsyncTaskParams;
import com.example.mensagens.util.asynctask.AsyncTaskResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginService loginService = new LoginService(this);

        AsyncTaskParams params = new AsyncTaskParams(this);
        params.put("loginService", loginService);
        new VerificaLoginAsyncTask().execute(params);
    }

    private static class VerificaLoginAsyncTask extends AsyncTaskImpl {

        @Override
        protected AsyncTaskParams executeInBackGround(AsyncTaskParams params) {
            LoginService loginService = params.getParam("loginService");

            params.put("isLoginAtivo", loginService.isLoginAtivo());

            return params;
        }

        @Override
        protected void onPostExecute(AsyncTaskResult result) {
            boolean isLoginAtivo = result.getParams().getParam("isLoginAtivo");
            MainActivity ctx = result.getContext();
            Class klass = isLoginAtivo ? ConversasActivity.class : PreLogin.class;
            ctx.startActivity(new Intent(ctx, klass));
        }
    }
}
