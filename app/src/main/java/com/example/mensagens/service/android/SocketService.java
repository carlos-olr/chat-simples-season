package com.example.mensagens.service.android;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mensagens.R;
import com.example.mensagens.controller.activity.conversa.MensagensActivity;
import com.example.mensagens.model.Mensagem;
import com.example.mensagens.service.interno.LoginService;
import com.example.mensagens.service.interno.MensagensService;
import com.example.mensagens.util.Constantes;
import com.example.mensagens.util.json.JsonModel;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class SocketService extends Service {

    public Socket mSocket;
    private LoginService loginService;
    private MensagensService mensagensService;

    @Override
    public void onCreate() {
        super.onCreate();
        loginService = new LoginService(getApplicationContext());
        mensagensService = new MensagensService(getApplicationContext());

        onBind(new Intent());
    }

    @Override
    public IBinder onBind(Intent intent) {
        try {
            if (loginService.isLoginAtivo()) {
                mSocket = IO.socket(Constantes.HOST);
                mSocket.connect();

                String login = loginService.getUsuaio().login;
                String chaveMsg = "mensagem" + login;
                mSocket.on(chaveMsg, listenerMensagens);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Emitter.Listener listenerMensagens = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            Mensagem mensagem = JsonModel.fromJson(args[0].toString(), Mensagem.class);

            Long idMensagemSalva = mensagensService.salvarMensagemRecebida(mensagem);

            Intent new_intent = new Intent();
            new_intent.setAction("MENSAGEM_RECEBIDA");
            sendBroadcast(new_intent);

//            NotificationManager manager = (NotificationManager)
//                    getSystemService(Context.NOTIFICATION_SERVICE);
//
//            NotificationChannel mChannel = manager.getNotificationChannel("msg_channel");
//            if (mChannel == null) {
//                mChannel = new NotificationChannel("msg_channel",
//                        "MensagensRecebidas", NotificationManager.IMPORTANCE_HIGH);
//                mChannel.enableVibration(true);
//                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//                mChannel.setDescription("Mensagens channel");
//                manager.createNotificationChannel(mChannel);
//            }
//
//            NotificationCompat.Builder builder =
//                    new NotificationCompat.Builder(getApplicationContext(), "msg_channel")
//                            .setSmallIcon(R.drawable.ic_launcher_foreground)
//                            .setContentTitle(mensagem.autor)
//                            .setContentText(mensagem.conteudo);
//
//            Intent targetIntent = new Intent(getApplicationContext(), MensagensActivity.class);
//            targetIntent.putExtra("idContato", mensagem.autorId);
//            PendingIntent contentIntent = PendingIntent.getActivity(SocketService.this, 0,
//                    targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(contentIntent);
//            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//            manager.notify(0, builder.build());
        }
    };


}
