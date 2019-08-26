package com.example.mensagens.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.mensagens.repository.api.Model;

public class Conversa implements Model<Conversa> {

    private static final String ID = "ID";
    private static final String TABELA_CONVERSA = "TABELA_CONVERSA";
    private static final String LOGIN_CONTATO = "LOGIN_CONTATO";
    private static final String ID_CONTATO = "ID_CONTATO";
    private static final String DATA_ULTIMA_MENSAGEM = "DATA_ULTIMA_MENSAGEM";

    public Long id;
    public String tabelaConversa;
    public String idContato;
    public String loginContato;
    public Long dataUltimaMensagem;

    @Override
    public Conversa construir(Cursor cursor) {
        Conversa c = new Conversa();
        c.id = cursor.getLong(cursor.getColumnIndex(ID));
        c.tabelaConversa = cursor.getString(cursor.getColumnIndex(TABELA_CONVERSA));
        c.loginContato = cursor.getString(cursor.getColumnIndex(LOGIN_CONTATO));
        c.idContato = cursor.getString(cursor.getColumnIndex(ID_CONTATO));

        int columnIndexDataUltimaMsg = cursor.getColumnIndex(DATA_ULTIMA_MENSAGEM);
        if (columnIndexDataUltimaMsg != -1 && !cursor.isNull(columnIndexDataUltimaMsg)) {
            c.dataUltimaMensagem = cursor.getLong(columnIndexDataUltimaMsg);
        }

        return c;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ID, id);
        cv.put(TABELA_CONVERSA, tabelaConversa);
        cv.put(LOGIN_CONTATO, loginContato);
        cv.put(ID_CONTATO, idContato);
        cv.put(DATA_ULTIMA_MENSAGEM, dataUltimaMensagem);
        return cv;
    }
}
