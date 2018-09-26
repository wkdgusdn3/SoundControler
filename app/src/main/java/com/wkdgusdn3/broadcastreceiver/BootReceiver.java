package com.wkdgusdn3.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.wkdgusdn3.observer.VolumeChangeObserver;
import com.wkdgusdn3.service.SoundService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            VolumeChangeObserver volumeChangeObserver = new VolumeChangeObserver(context, new Handler());
            context
                    .getContentResolver()
                    .registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, volumeChangeObserver );

            Intent soundServiceIntent = new Intent(context, SoundService.class);
            context.startService(soundServiceIntent);
        }
    }
}
