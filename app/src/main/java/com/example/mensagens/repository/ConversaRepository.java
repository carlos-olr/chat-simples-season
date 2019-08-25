package com.example.mensagens.repository;

import android.content.Context;

import com.example.mensagens.model.Conversa;
import com.example.mensagens.repository.api.Database;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ConversaRepository extends Database<Conversa> {

    private static final String TABELA_CONVERSA = "CONVERSA";
    private static final String BUSCAR_POR_CONTATO_ID = "select * from CONVERSA where ID_CONTATO = ?";

    public ConversaRepository(Context context) {
        super(context);
    }

    public Conversa busarPorIdContato(String contatoId) {
        List<Conversa> conversas = this.executeQuery(BUSCAR_POR_CONTATO_ID, contatoId);
        return !conversas.isEmpty() ? conversas.get(0) : null;
    }

    public Conversa criarConversa(Conversa conversa) {
        Long idGerado = this.insert(conversa.getContentValues(), TABELA_CONVERSA);
        conversa.id = idGerado;
        return conversa;
    }
}
