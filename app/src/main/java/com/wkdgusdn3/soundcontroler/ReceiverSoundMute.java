package com.wkdgusdn3.soundcontroler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class ReceiverSoundMute extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        for(int i=0; i<15; i++) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER,
                    AudioManager.FLAG_SHOW_UI);
        }
    }
}
