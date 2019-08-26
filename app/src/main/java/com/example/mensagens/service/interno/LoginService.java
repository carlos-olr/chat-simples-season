package com.example.mensagens.service.interno;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.example.mensagens.model.Usuario;
import com.example.mensagens.util.Constantes;
import com.example.mensagens.util.asynctask.AsyncTaskOnFinishListener;
import com.example.mensagens.util.asynctask.AsyncTaskParams;
import com.example.mensagens.util.json.JsonModel;
import com.example.mensagens.util.web.HttpUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;


public class LoginService {

    private SharedPreferences sharedPrefs;

    public LoginService(Context context) {
        //this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.sharedPrefs = context.getSharedPreferences("app_chat", Context.MODE_PRIVATE);
    }

    public boolean isLoginAtivo() {
        Usuario usuario = this.getUsuarioFromToken();
        return usuario != null && usuario.exp > new Date().getTime() / 1000;
    }

    public Usuario getUsuaio() {
        return this.getUsuarioFromToken();
    }

    //    public String login(Usuario usuario) {
//        try {
//            String url = Constantes.HOST + "/login";
//            RespostaHttp respostaHttp = new HttpUtils.HttpPostHelper().executeInBackGround(url, usuario.getAutenticacao()).get();
//            if (respostaHttp.possuiErro()) {
//                throw new ChatCompletoException(respostaHttp.getErro());
//            }
//            this.sharedPrefs.edit().putString("tokenLogin", respostaHttp.getSucesso()).apply();
//            return respostaHttp.getSucesso();
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        }
//    }
//

    private Usuario getUsuarioFromToken() {
        String tokenLogin = this.sharedPrefs.getString("tokenLogin", "");
        if (StringUtils.isEmpty(tokenLogin)) {
            return null;
        }
        String jsonPayload = new String(Base64.decode(tokenLogin.split("\\.")[1], Base64.DEFAULT));
        return JsonModel.fromJson(jsonPayload, Usuario.class);
    }

    public void setUsuario(String resposta) {
        this.sharedPrefs.edit().putString("tokenLogin", resposta).apply();
    }
}
