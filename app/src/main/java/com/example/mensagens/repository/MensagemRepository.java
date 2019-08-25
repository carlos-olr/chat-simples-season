package com.example.mensagens.repository;

import android.content.Context;

import com.example.mensagens.model.Mensagem;
import com.example.mensagens.repository.api.Database;

public class MensagemRepository extends Database {

    public MensagemRepository(Context context) {
        super(context);
    }

    public Long salvarMensagem(Mensagem mensagem, String tabelaConversa) {
        return this.insert(mensagem.getContentValues(), tabelaConversa);
    }
}
