package com.example.mensagens.util.asynctask;

import java.io.Serializable;
import java.util.HashMap;

public class AsyncTaskParams extends HashMap<String, Object> implements Serializable {

    public <T> T getParam(String chave) {
        return (T) this.get(chave);
    }

}
