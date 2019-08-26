package com.example.mensagens.util.asynctask;

import android.os.AsyncTask;

import org.apache.commons.lang3.BooleanUtils;

public abstract class AsyncTaskImpl extends AsyncTask<AsyncTaskParams, AsyncTaskParams, AsyncTaskResult> {

    protected AsyncTaskOnFinishListener onFinishListener;

    public AsyncTaskImpl(){}

    public AsyncTaskImpl(AsyncTaskOnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public AsyncTaskImpl setOnFinishListener(AsyncTaskOnFinishListener listener) {
        this.onFinishListener = listener;
        return this;
    }

    protected abstract AsyncTaskParams executeInBackGround(AsyncTaskParams params);

    @Override
    protected AsyncTaskResult doInBackground(AsyncTaskParams... params) {
        AsyncTaskParams paramsRetornados = this.executeInBackGround(params[0]);
        boolean sucesso = BooleanUtils.isTrue((Boolean) paramsRetornados.getParam("sucesso"));
        return new AsyncTaskResult(paramsRetornados.getContext(), paramsRetornados, sucesso);
    }

    @Override
    protected void onPostExecute(AsyncTaskResult result) {
        if (onFinishListener != null) {
            onFinishListener.onFinish(result);
        }
    }
}