package com.example.mensagens.repository.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.common.collect.Lists;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class Database<T extends Model> extends SQLiteOpenHelper {

    private Context ctx;
    private Class<T> modelClass;

    public Database(Context context) {
        super(context, "chat_completo_agosto_2019", null, 1);
        this.ctx = context;

        this.modelClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Context getContext() {
        return this.ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase conn) {
        this.executarSqlArquivo(conn, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase conn, int versaoAntiga, int versaoNova) {
        this.executarSqlArquivo(conn, versaoNova);
    }

    T construir(Cursor cursor) {
        try {
            Model objeto = modelClass.newInstance();
            return (T) objeto.construir(cursor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void executarSqlArquivo(SQLiteDatabase conn, int versaoAlvo) {
        try {
            String arquivo = "sql/versao" + versaoAlvo + ".sql";
            String sql = IOUtils.toString(ctx.getAssets().open(arquivo), StandardCharsets.UTF_8);
            conn.execSQL(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> executeQuery(String query, String... args) {
        SQLiteDatabase conn = this.getReadableDatabase();
        try {
            Cursor cursor = conn.rawQuery(query, args);
            List<T> resultado = Lists.newArrayList();
            while (cursor.moveToNext()) {
                resultado.add(this.construir(cursor));
            }
            return resultado;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }

    public Long insert(ContentValues values, String tabela) {
        SQLiteDatabase conn = this.getWritableDatabase();
        try {
            return conn.insertOrThrow(tabela, null, values);
        } catch (SQLiteException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }

    public void update(ContentValues values, String tabela, String where, String... args) {
        SQLiteDatabase conn = this.getWritableDatabase();
        try {
            conn.update(tabela, values, where, args);
        } catch (SQLiteException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }

    public void delete(String tabela, String where, String... args) {
        SQLiteDatabase conn = this.getWritableDatabase();
        try {
            conn.delete(tabela, where, args);
        } catch (SQLiteException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }
}
