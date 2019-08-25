package com.example.mensagens.service.android;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.mensagens.model.Mensagem;
import com.example.mensagens.service.interno.MensagemService;
import com.example.mensagens.service.interno.LoginService;
import com.example.mensagens.util.Constantes;
import com.example.mensagens.util.json.JsonModel;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class SocketService extends Service {

    public Socket mSocket;
    private LoginService loginService;
    private MensagemService mensagemService;

    @Override
    public void onCreate() {
        super.onCreate();
        loginService = new LoginService(getApplicationContext());
        mensagemService =  new MensagemService(getApplicationContext());

        try {
            if (loginService.isLoginAtivo()) {
                mSocket = IO.socket(Constantes.HOST);
                mSocket.connect();

                String id = loginService.getUsuaio().id;
                String chaveMsg = "mensagem" + id;
                mSocket.on(chaveMsg, listenerMensagens);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private Emitter.Listener listenerMensagens = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            Mensagem mensagem = JsonModel.fromJson((String) args[0], Mensagem.class);

            Long idMensagemSalva = mensagemService.salvarMensagemRecebida(mensagem);

//            NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(SocketService.this, "App").setSmallIcon(R.drawable.ic_launcher)
//                    .setContentTitle(mensagem.getRemetente()).setContentText(mensagem.getConteudo());
//
//            int NOTIFICATION_ID = 12345;
//
//            Intent targetIntent = new Intent(SocketService.this, HomeActivity.class);
//            PendingIntent contentIntent =
//                PendingIntent.getActivity(SocketService.this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(contentIntent);
//            NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            if (nManager != null) {
//                nManager.notify(NOTIFICATION_ID, builder.build());
//            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
