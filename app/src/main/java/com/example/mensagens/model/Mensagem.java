package com.example.mensagens.model;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.example.mensagens.repository.api.Model;
import com.example.mensagens.util.json.JsonModel;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class Mensagem extends JsonModel implements Model<Mensagem> {

    private static final String ID = "ID";
    private static final String ID_EXTERNO = "ID_EXTERNO";
    private static final String CONTEUDO = "CONTEUDO";
    private static final String AUTOR = "AUTOR";
    private static final String AUTOR_ID = "AUTOR_ID";
    private static final String DESTINATARIO = "DESTINATARIO";
    private static final String DESTINATARIO_ID = "DESTINATARIO_ID";
    private static final String DATA_RECEBIDA = "DATA_RECEBIDA";

    public Long id;
    @SerializedName("_id")
    public String idExterno;
    public String conteudo;
    public String autor;
    public String autorId;
    public String destinatario;
    public String destinatarioId;
    public Long dataRecebida;

    public Date getDataRecebida() {
        return new Date(this.dataRecebida);
    }

    @Override
    public Mensagem construir(Cursor cursor) {
        Mensagem mensagem = new Mensagem();
        mensagem.id = cursor.getLong(cursor.getColumnIndex(ID));
        mensagem.idExterno = cursor.getString(cursor.getColumnIndex(ID_EXTERNO));
        mensagem.conteudo = cursor.getString(cursor.getColumnIndex(CONTEUDO));
        mensagem.autor = cursor.getString(cursor.getColumnIndex(AUTOR));
        mensagem.autorId = cursor.getString(cursor.getColumnIndex(AUTOR_ID));
        mensagem.destinatario = cursor.getString(cursor.getColumnIndex(DESTINATARIO));
        mensagem.destinatarioId = cursor.getString(cursor.getColumnIndex(DESTINATARIO_ID));
        mensagem.dataRecebida = cursor.getLong(cursor.getColumnIndex(DATA_RECEBIDA));
        return mensagem;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ID, id);
        cv.put(ID_EXTERNO, idExterno);
        cv.put(CONTEUDO, conteudo);
        cv.put(AUTOR, autor);
        cv.put(AUTOR_ID, autorId);
        cv.put(DESTINATARIO, destinatario);
        cv.put(DESTINATARIO_ID, destinatarioId);
        cv.put(DATA_RECEBIDA, dataRecebida);
        return cv;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idExterno, conteudo, autor, autorId, destinatario,
                destinatarioId, dataRecebida);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        }
        Mensagem that = (Mensagem) o;
        return Objects.equals(this.id, that.id)
                && Objects.equals(this.idExterno, that.idExterno)
                && Objects.equals(this.conteudo, that.conteudo)
                && Objects.equals(this.autor, that.autor)
                && Objects.equals(this.autorId, that.autorId)
                && Objects.equals(this.destinatario, that.destinatario)
                && Objects.equals(this.destinatarioId, that.destinatarioId)
                && Objects.equals(this.dataRecebida, that.dataRecebida);
    }
}
