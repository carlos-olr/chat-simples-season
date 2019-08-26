package com.example.mensagens.util.asynctask;

import android.content.Context;

import java.io.Serializable;

public class AsyncTaskResult implements Serializable {

    private Context context;
    private AsyncTaskParams params;
    private boolean sucesso;

    public AsyncTaskResult(Context context, AsyncTaskParams params, boolean sucesso) {
        this.context = context;
        this.params = params;
        this.sucesso = sucesso;
    }

    public AsyncTaskResult(Context context, AsyncTaskParams params) {
        this.context = context;
        this.params = params;
    }

    public <T extends Context> T getContext() {
        return (T) context;
    }

    public AsyncTaskParams getParams() {
        return params;
    }

    public boolean getSucesso() {
        return sucesso;
    }
}
