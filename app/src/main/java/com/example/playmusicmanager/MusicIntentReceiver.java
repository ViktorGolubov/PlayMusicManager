package com.example.playmusicmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MusicIntentReceiver extends Service {
    public MusicIntentReceiver() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
