package com.example.mensagens.model;

import androidx.annotation.Nullable;

import com.example.mensagens.util.json.JsonModel;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Usuario extends JsonModel {

    @SerializedName("_id")
    public String id;
    public String login;
    public String senha;
    public String confirmarSenha;
    public Long exp;

    public String getAutenticacao() {
        return "{\"login\":\"" + this.login + "\",\"senha\":\"" + this.senha +"\"}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, senha, confirmarSenha, exp);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        }
        Usuario that = (Usuario) o;
        return Objects.equals(this.id, that.id)
                && Objects.equals(this.login, that.login)
                && Objects.equals(this.senha, that.senha)
                && Objects.equals(this.confirmarSenha, that.confirmarSenha)
                && Objects.equals(this.exp, that.exp);
    }

}
