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

    @Override
    public void onCreate() {

        NotificationManager notificationManager;
        Notification notification;
        context = getApplicationContext();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notification = new Notification(R.drawable.sound_icon, "SoundControler", System.currentTimeMillis());

        if (!InfoManager.boolean_icon) {
            notification.priority = Notification.PRIORITY_MIN;
        }
        notification.flags = Notification.FLAG_NO_CLEAR;

        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.sound_notification);
        views.setImageViewResource(R.id.sound_icon, R.drawable.sound_icon);

        if (InfoManager.boolean_musicPlay) {
            Intent intent_play = new Intent(getApplicationContext(), ReceiverMusicPlay.class);
            PendingIntent pendingIntent_play = PendingIntent.getBroadcast(context, 0, intent_play, 0);
            views.setImageViewResource(R.id.music_play, R.drawable.music_play);
            views.setOnClickPendingIntent(R.id.music_play, pendingIntent_play);
        } else {
            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
            views.setImageViewResource(R.id.music_play, R.drawable.clear);
            views.setOnClickPendingIntent(R.id.music_play, pendingIntent_clear);
        }
        if (InfoManager.boolean_soundDown) {
            Intent intent_down = new Intent(getApplicationContext(), ReceiverSoundDown.class);
            PendingIntent pendingIntent_down = PendingIntent.getBroadcast(context, 0, intent_down, 0);
            views.setImageViewResource(R.id.sound_down, R.drawable.sound_down);
            views.setOnClickPendingIntent(R.id.sound_down, pendingIntent_down);
        } else {
            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
            views.setImageViewResource(R.id.sound_down, R.drawable.clear);
            views.setOnClickPendingIntent(R.id.sound_down, pendingIntent_clear);
        }
        if (InfoManager.boolean_soundUp) {
            Intent intent_up = new Intent(getApplicationContext(), ReceiverSoundUp.class);
            PendingIntent pendingIntent_up = PendingIntent.getBroadcast(context, 0, intent_up, 0);
            views.setImageViewResource(R.id.sound_up, R.drawable.sound_up);
            views.setOnClickPendingIntent(R.id.sound_up, pendingIntent_up);
        } else {
            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
            views.setImageViewResource(R.id.sound_up, R.drawable.clear);
            views.setOnClickPendingIntent(R.id.sound_up, pendingIntent_clear);
        }
        if (InfoManager.boolean_soundMute) {
            Intent intent_mute = new Intent(getApplicationContext(), ReceiverSoundMute.class);
            PendingIntent pendingIntent_mute = PendingIntent.getBroadcast(context, 0, intent_mute, 0);
            views.setImageViewResource(R.id.sound_mute, R.drawable.sound_mute);
            views.setOnClickPendingIntent(R.id.sound_mute, pendingIntent_mute);
        } else {
            Intent intent_clear = new Intent(getApplicationContext(), ReceiverClear.class);
            PendingIntent pendingIntent_clear = PendingIntent.getBroadcast(context, 0, intent_clear, 0);
            views.setImageViewResource(R.id.sound_mute, R.drawable.clear);
            views.setOnClickPendingIntent(R.id.sound_mute, pendingIntent_clear);
        }

        notification.contentView = views;
        notificationManager.notify(0, notification);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
