package com.example.mensagens;

import com.example.mensagens.model.Mensagem;
import com.example.mensagens.util.json.JsonModel;
import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        Mensagem m = new Mensagem();
        m.id = 45L;
        m.idExterno = "4656484354";

        String a = m.toJson();

        Assert.assertEquals("{\"id\":45,\"_id\":\"4656484354\"}", a);

        String json = "{\"id\":45,\"_id\":\"4656484354\"}";

        Mensagem fromJson = JsonModel.fromJson(json, Mensagem.class);

        Assert.assertEquals(m, fromJson);
    }
}