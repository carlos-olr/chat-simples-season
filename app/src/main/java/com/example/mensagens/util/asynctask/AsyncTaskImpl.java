package com.example.mensagens.util.asynctask;

import android.os.AsyncTask;

public abstract class AsyncTaskImpl extends AsyncTask<AsyncTaskParams, AsyncTaskParams, AsyncTaskParams> {

    protected AsyncTaskOnFinishListener onFinishListener;

    public AsyncTaskImpl(){}

    public AsyncTaskImpl(AsyncTaskOnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public AsyncTaskImpl setOnFinishListener(AsyncTaskOnFinishListener listener) {
        this.onFinishListener = listener;
        return this;
    }

    protected abstract AsyncTaskParams doInBackground(AsyncTaskParams params);

    @Override
    protected AsyncTaskParams doInBackground(AsyncTaskParams... params) {
        return this.doInBackground(params[0]);
    }

    @Override
    protected void onPostExecute(AsyncTaskParams asyncTaskParams) {
        if (onFinishListener != null) {
            onFinishListener.onFinish(asyncTaskParams);
        }
    }
}