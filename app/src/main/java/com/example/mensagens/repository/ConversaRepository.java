package com.example.mensagens.repository;

import android.content.ContentValues;
import android.content.Context;

import com.example.mensagens.model.Conversa;
import com.example.mensagens.repository.api.Database;

import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConversaRepository extends Database<Conversa> {

    private static final String TABELA_CONVERSA = "CONVERSA";
    private static final String BUSCAR_POR_CONTATO_ID = "select * from CONVERSA where ID_CONTATO = ?";
    private static final String BUSCAR_TODAS = "select * from CONVERSA order by DATA_ULTIMA_MENSAGEM desc";

    public ConversaRepository(Context context) {
        super(context);
    }

    public List<Conversa> buscarTodas() {
        return this.executeQuery(BUSCAR_TODAS);
    }

    public Conversa buscarPorIdContato(String contatoId) {
        List<Conversa> conversas = this.executeQuery(BUSCAR_POR_CONTATO_ID, contatoId);
        return !conversas.isEmpty() ? conversas.get(0) : null;
    }

    public Conversa salvar(Conversa conversa) {
        ContentValues contentValues = conversa.getContentValues();

        if (conversa.id != null) {
            this.update(contentValues, TABELA_CONVERSA, "id = ?", conversa.id.toString());
        } else {
            conversa.id = this.insert(contentValues, TABELA_CONVERSA);

            try {
                String sqlTemplateFile = "sql/template_tabela_mensagem.sql";
                String sqlTemplate = IOUtils.toString(getContext().getAssets().open(sqlTemplateFile),
                        StandardCharsets.UTF_8);
                sqlTemplate = sqlTemplate.replace("__NOME_TABELA__", conversa.tabelaConversa);
                execSQL(sqlTemplate);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return conversa;
    }
}
