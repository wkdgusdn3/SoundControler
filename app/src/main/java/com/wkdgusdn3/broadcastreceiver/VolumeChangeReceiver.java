package com.wkdgusdn3.broadcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.RemoteViews;

import com.wkdgusdn3.manager.InfoManager;
import com.wkdgusdn3.model.ThemeType;
import com.wkdgusdn3.soundcontroller.R;

public class VolumeChangeReceiver extends BroadcastReceiver {

    private final int currentVolumeId = R.id.current_volume;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {

            NotificationManager notificationManager = InfoManager.notificationManager;
            Notification notification = InfoManager.notification;
            RemoteViews remoteViews = InfoManager.notification.contentView;

            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

            // set currentVolume imageView
            if(InfoManager.theme == ThemeType.DARK) { // dark
                remoteViews.setImageViewResource(currentVolumeId, context.getResources().getIdentifier(String.format("current_volume_%s_%s", Long.toString(currentVolume), ThemeType.DARK.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller"));
            } else { // white
                remoteViews.setImageViewResource(currentVolumeId, context.getResources().getIdentifier(String.format("current_volume_%s_%s", Long.toString(currentVolume), ThemeType.WHITE.getResourceAdditionName()), "drawable", "com.wkdgusdn3.soundcontroller"));
            }

            notificationManager.notify(3, notification);
        }
    }
}