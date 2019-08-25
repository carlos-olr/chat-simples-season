package com.example.mensagens.util.web;


import com.example.mensagens.util.asynctask.AsyncTaskImpl;
import com.example.mensagens.util.asynctask.AsyncTaskParams;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by carlo on 01/03/2017.
 */

public class HttpUtils {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static class HttpGetHelper extends AsyncTaskImpl {

        @Override
        protected AsyncTaskParams doInBackground(AsyncTaskParams params) {
            try {
                String url = params.getParam("url");
                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                builder.url(url);

                Response response = client.newCall(builder.build()).execute();
                return popularResposta(response, params);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class HttpPostHelper extends AsyncTaskImpl {

        @Override
        protected AsyncTaskParams doInBackground(AsyncTaskParams params) {
            try {
                String url = params.getParam("url");
                String json = params.getParam("json");
                OkHttpClient client = new OkHttpClient();

                Request.Builder builder = new Request.Builder();
                builder.url(url);
                builder.post(RequestBody.create(JSON, json));

                Response response = client.newCall(builder.build()).execute();
                return popularResposta(response, params);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static AsyncTaskParams popularResposta(Response response, AsyncTaskParams params)
            throws IOException {
        params.put("sucesso", response.code() == 200);
        if (response.body() != null) {
            params.put("resposta", response.body().string());
        }
        return params;
    }

}
