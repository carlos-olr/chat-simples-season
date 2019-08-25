package com.example.mensagens.repository.api;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

public interface Model<T> extends Serializable {

    T construir(Cursor cursor);

    ContentValues getContentValues();
}
