package com.example.mensagens.util.asynctask;

import android.content.Context;

import java.io.Serializable;
import java.util.HashMap;

public class AsyncTaskParams extends HashMap<String, Object> implements Serializable {

    private Context context;

    public AsyncTaskParams() {
    }

    public AsyncTaskParams(Context ctx) {
        this.context = ctx;
    }

    public Context getContext() {
        return context;
    }

    public <T> T getParam(String chave) {
        return (T) this.get(chave);
    }

}
