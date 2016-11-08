package com.wkdgusdn3.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

public class ReceiverSound40Per extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int targetSound = 6;

        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        int currentSound = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int gap = Math.abs(targetSound-currentSound);

        if(targetSound > currentSound) {
            for(int i=0; i<gap; i++) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_SHOW_UI);
            }
        } else {
            for(int i=0; i<gap; i++) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_SHOW_UI);
            }
        }
    }
}
