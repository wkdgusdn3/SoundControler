package com.wkdgusdn3.observer;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.widget.RemoteViews;

import com.wkdgusdn3.manager.InfoManager;
import com.wkdgusdn3.model.ThemeType;
import com.wkdgusdn3.soundcontroller.R;


public class VolumeChangeObserver extends ContentObserver {

    private static final String THEME_DARK = "dark";
    private static final String THEME_WHITE = "white";

    private final int currentVolumeId = R.id.current_volume;
    private Context context;

    public VolumeChangeObserver(Context context, Handler handler) {
        super(handler);

        this.context = context;
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        NotificationManager notificationManager = InfoManager.notificationManager;
        Notification notification = InfoManager.notification;
        RemoteViews remoteViews = InfoManager.notification.contentView;

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // set currentVolume imageView
        if(InfoManager.theme == ThemeType.DARK) { // dark
            remoteViews.setImageViewResource(currentVolumeId, context.getResources().getIdentifier(String.format("current_volume_%s_%s", Long.toString(currentVolume), THEME_DARK), "drawable", "com.wkdgusdn3.soundcontroller"));
        } else { // white
            remoteViews.setImageViewResource(currentVolumeId, context.getResources().getIdentifier(String.format("current_volume_%s_%s", Long.toString(currentVolume), THEME_WHITE), "drawable", "com.wkdgusdn3.soundcontroller"));
        }

        notificationManager.notify(3, notification);
    }
}