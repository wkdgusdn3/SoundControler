package com.wkdgusdn3.soundcontroler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class SoundService extends Service {

    Context context;

    NotificationManager notificationManager;
    Notification notification;

    @Override
    public void onCreate() {

        context = getApplicationContext();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification(R.drawable.sound_icon, "SoundControler", System.currentTimeMillis());

        Intent intent_play = new Intent(getApplicationContext(), ReceiverMusicPlay.class);
        Intent intent_up = new Intent(getApplicationContext(), ReceiverSoundUp.class);
        Intent intent_down = new Intent(getApplicationContext(), ReceiverSoundDown.class);
        Intent intent_mute = new Intent(getApplicationContext(), ReceiverSoundMute.class);

        PendingIntent pendingIntent_play = PendingIntent.getBroadcast(context, 0, intent_play, 0);
        PendingIntent pendingIntent_up = PendingIntent.getBroadcast(context, 0, intent_up, 0);
        PendingIntent pendingIntent_down = PendingIntent.getBroadcast(context, 0, intent_down, 0);
        PendingIntent pendingIntent_mute = PendingIntent.getBroadcast(context, 0, intent_mute, 0);

        final RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.sound_notification);
        views.setImageViewResource(R.id.sound_icon, R.drawable.sound_icon);
        if(InfoManager.boolean_musicPlay) {
            views.setImageViewResource(R.id.music_play, R.drawable.music_play);
            views.setOnClickPendingIntent(R.id.music_play, pendingIntent_play);
        }
        if(InfoManager.boolean_soundUp) {
            views.setImageViewResource(R.id.sound_up, R.drawable.sound_up);
            views.setOnClickPendingIntent(R.id.sound_up, pendingIntent_up);
        }
        if(InfoManager.boolean_soundDown) {
            views.setImageViewResource(R.id.sound_down, R.drawable.sound_down);
            views.setOnClickPendingIntent(R.id.sound_down, pendingIntent_down);
        }
        if(InfoManager.boolean_soundMute) {
            views.setImageViewResource(R.id.sound_mute, R.drawable.sound_mute);
            views.setOnClickPendingIntent(R.id.sound_mute, pendingIntent_mute);
        }

        notification.contentView = views;
        notification.flags = Notification.FLAG_NO_CLEAR;
        notificationManager.notify(3, notification);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
