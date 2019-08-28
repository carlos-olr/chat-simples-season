package com.example.mensagens.app;

import android.app.Application;
import android.content.Intent;

import com.example.mensagens.service.android.SocketService;

public class MensagensApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Intent intentService = new Intent(getBaseContext(), SocketService.class);
        startService(intentService);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        Intent intentService = new Intent(getBaseContext(), SocketService.class);
        stopService(intentService);
    }
}
