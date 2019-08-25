package com.example.mensagens.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.mensagens.repository.api.Model;

public class Conversa implements Model<Conversa> {

    private static final String ID = "ID";
    private static final String TABELA_CONVERSA = "TABELA_CONVERSA";
    private static final String NOME_CONTATO = "NOME_CONTATO";
    private static final String ID_CONTATO = "ID_CONTATO";

    public Long id;
    public String tabelaConversa;
    public String nomeContato;
    public String idContato;

    @Override
    public Conversa construir(Cursor cursor) {
        Conversa c = new Conversa();
        c.id = cursor.getLong(cursor.getColumnIndex(ID));
        c.tabelaConversa = cursor.getString(cursor.getColumnIndex(TABELA_CONVERSA));
        c.nomeContato = cursor.getString(cursor.getColumnIndex(NOME_CONTATO));
        c.idContato = cursor.getString(cursor.getColumnIndex(ID_CONTATO));
        return c;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ID, id);
        cv.put(TABELA_CONVERSA, tabelaConversa);
        cv.put(NOME_CONTATO, nomeContato);
        cv.put(ID_CONTATO, idContato);
        return cv;
    }
}
