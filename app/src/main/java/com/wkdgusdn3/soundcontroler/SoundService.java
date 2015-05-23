package com.wkdgusdn3.soundcontroler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class SoundService extends Service {

    NotificationManager notificationManager;
    Notification notification;
    int i = 0;

    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification(android.R.drawable.ic_input_add, "asdf", System.currentTimeMillis());


        final RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.progressbar_sound);
        views.setImageViewResource(R.id.sound_0, android.R.drawable.ic_input_add);
        views.setImageViewResource(R.id.sound_20, android.R.drawable.ic_input_add);
        views.setImageViewResource(R.id.sound_40, android.R.drawable.ic_input_add);
        views.setImageViewResource(R.id.sound_60, android.R.drawable.ic_input_add);
        views.setImageViewResource(R.id.sound_80, android.R.drawable.ic_input_add);
        views.setImageViewResource(R.id.sound_100, android.R.drawable.ic_input_add);
        notification.contentView = views;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1234, notification);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
