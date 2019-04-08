package com.cenco.demo.player.test;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.cenco.lib.common.PermissionManager;
import com.kuaifa.lib.kfplayer.ijk.Settings;
import com.kuaifa.lib.kfplayer.media.KIjkMediaPlayer;
import com.kuaifa.lib.kfplayer.media.KMediaPlayer;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, KMediaPlayer.OnPreparedListener, KMediaPlayer.OnInfoListener, KMediaPlayer.OnCompletionListener, KMediaPlayer.OnSeekCompleteListener, KMediaPlayer.OnErrorListener {

    KMediaPlayer mediaPlayer;
    SurfaceHolder holder;
    private String mVideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionManager manager = new PermissionManager(this);
        manager.requestPermission(new PermissionManager.PermissionCallback() {
            @Override
            public void onGrant() {
                Log.e("xin","onGrant");
            }

            @Override
            public void onDeny() {
                Log.e("xin","onDeny");
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        mVideoPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/xz/f.mp4";
        if (!new File(mVideoPath).exists()){
            Toast.makeText(this,"文件不存在",Toast.LENGTH_LONG).show();
            Log.e("xin","文件不存在:"+mVideoPath);
            return;
        }
//        mVideoPath = "http://ivi.bupt.edu.cn/hls/cctv6.m3u8";

////        mediaPlayer = new KIjkMediaPlayer(this, Settings.PV_PLAYER__IjkExoMediaPlayer);//e
//        mediaPlayer = new KIjkMediaPlayer(this, Settings.PV_PLAYER__IjkMediaPlayer);//f
////        mediaPlayer = new KIjkMediaPlayer(this, Settings.PV_PLAYER__AndroidMediaPlayer);
//        mediaPlayer.setOnPreparedListener(this);
//        mediaPlayer.setOnInfoListener(this);
//        mediaPlayer.setOnCompletionListener(this);
//        mediaPlayer.setOnSeekCompleteListener(this);
//        mediaPlayer.setOnErrorListener(this);
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);

        View seekForwardBtn = findViewById(R.id.seekForwardBtn);
        seekForwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer==null){
                    return;
                }
                long duration = mediaPlayer.getDuration();
                long currentPosition = mediaPlayer.getCurrentPosition();
                long seekTo = currentPosition+5000;
                Log.i("xin","当前播放位置："+currentPosition+"，前进到:"+seekTo+",总时长:"+duration);
                mediaPlayer.seekTo(seekTo);
            }
        });

        findViewById(R.id.android).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player(Settings.PV_PLAYER__AndroidMediaPlayer);
            }
        });
        findViewById(R.id.ijk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player(Settings.PV_PLAYER__IjkMediaPlayer);
            }
        });
        findViewById(R.id.exo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player(Settings.PV_PLAYER__IjkExoMediaPlayer);
            }
        });

    }

    private void player(int type){
        try {
            if (holder==null){
                Log.e("xin","holder is null");
                return;
            }
            if (mediaPlayer!=null){
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
            }
            mediaPlayer = new KIjkMediaPlayer(this, type);//f
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnInfoListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnSeekCompleteListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setDisplay(holder);
            mediaPlayer.setDataSource(mVideoPath);
            mediaPlayer.prepareAsync();
            Log.v("xin","prepareAsync");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        Log.v("xin","surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPrepared(KMediaPlayer mp) {
        mediaPlayer.start();
    }

    @Override
    public boolean onInfo(KMediaPlayer mp, int what, int extra) {
        Log.d("xin","onInfo");
        switch (what) {
            case KMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                Log.w("xin","onInfo->MEDIA_INFO_VIDEO_RENDERING_START");
                break;
        }
        return false;
    }

    @Override
    public void onCompletion(KMediaPlayer mp) {
        Log.e("xin","onCompletion");
    }

    @Override
    public void onSeekComplete(KMediaPlayer mp) {
        Log.w("xin","onSeekComplete");
    }

    @Override
    public boolean onError(KMediaPlayer mp, int what, int extra) {
        Log.e("xin","onError:what="+what+",extra="+extra);
        return false;
    }
}
