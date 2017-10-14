package com.hgz.test.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Service==========";
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.btn_bind)
    Button btnBind;
    @BindView(R.id.btn_unbind)
    Button btnUnbind;
    private MyService.MyBinder mybinder;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
                mybinder= (MyService.MyBinder) service;
                mybinder.startDownload();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_bind, R.id.btn_unbind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Log.i(TAG,"开启服务");
                Intent intent = new Intent(this, MyService.class);
                startService(intent);
                break;
            case R.id.btn_stop:
                Log.i(TAG,"关闭服务");
                Intent intent2 = new Intent(this, MyService.class);
                stopService(intent2);
                break;
            case R.id.btn_bind:
                Log.i(TAG,"绑定服务");
                Intent intent3 = new Intent(this, MyService.class);
                bindService(intent3,connection,BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                Log.i(TAG,"解绑服务");
                unbindService(connection);
                break;
        }
    }
}
