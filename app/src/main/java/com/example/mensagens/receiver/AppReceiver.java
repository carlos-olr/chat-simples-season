package com.example.mensagens.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mensagens.service.android.SocketService;


/**
 * Created by carlos on 15/06/18.
 */

public class AppReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, SocketService.class);
        context.startService(myIntent);

//        MensagensService mensagemService = new MensagensService(context);
//        mensagemService.recuperarMensagensNaoLidas();
    }
}
