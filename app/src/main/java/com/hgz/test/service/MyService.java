package com.hgz.test.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Administrator on 2017/10/13.
 */

public class MyService extends Service {
    private static final String TAG = "Service============";
    private MyBinder myBinder=new MyBinder();
    private MediaPlayer mediaPlayer;

    //该服务不存在需要被创建时被调用，不管startService()还是bindService()都会在启动时调用该方法
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("标题")//设置通知栏标题
                .setContentText("内容") //设置通知栏显示内容
                .setTicker("通知到来") //通知首次出现在通知栏，带上升动画效果的
                //.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON

        Notification notification = mBuilder.build();
        int id = 199;
        Log.d(TAG, "创建通知");
        mNotificationManager.notify(id, notification);
        //设置到前台
        startForeground(1,notification);
        //创建一个音乐播放器对象
        mediaPlayer = MediaPlayer.create(this, R.raw.mianshi);
        //设置可以重复播放
        mediaPlayer.setLooping(true);
    }
    //用startService方法调用该服务时，在onCreate()方法调用之后，会调用改方法
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mediaPlayer.start();
    }

    //其他对象通过bindService方法通知该Service时该方法会被调用
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        mediaPlayer.start();
        return myBinder;
    }
    //用startService方法调用该服务时，在onCreate()方法调用之后，会调用改方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    //其他对象通过unbindService方法通知该Service时该方法会被调用
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"onUnbind");
        return super.onUnbind(intent);
    }
    //该服务被销毁时调用该方法
    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        super.onDestroy();
        mediaPlayer.stop();
    }
    class MyBinder extends Binder{
        public void startDownload() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 执行具体的下载任务
                }
            }).start();
        }
    }
}
