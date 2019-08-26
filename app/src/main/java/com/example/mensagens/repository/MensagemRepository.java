package com.example.mensagens.repository;

import android.content.Context;

import com.example.mensagens.model.Conversa;
import com.example.mensagens.model.Mensagem;
import com.example.mensagens.repository.api.Database;

import java.util.List;

public class MensagemRepository extends Database<Mensagem> {

    private static final String BUSCAR_MENSAGENS_POR_TABELA =
            "select * from __NOME_TABELA__ order by DATA_RECEBIDA";

    public MensagemRepository(Context context) {
        super(context);
    }

    public Long salvarMensagem(Mensagem mensagem, String tabelaConversa) {
        return this.insert(mensagem.getContentValues(), tabelaConversa);
    }

    public List<Mensagem> buscarMensagensPorConversa(Conversa conversa) {
        String sql = BUSCAR_MENSAGENS_POR_TABELA.replace("__NOME_TABELA__", conversa.tabelaConversa);
        return this.executeQuery(sql);

    }
}
