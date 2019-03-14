package com.cenco.demo.player.test;

import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.kuaifa.lib.kfplayer.media.KIjkMediaPlayer;
import com.kuaifa.lib.kfplayer.media.KMediaPlayer;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, KMediaPlayer.OnPreparedListener, KMediaPlayer.OnInfoListener, KMediaPlayer.OnCompletionListener, KMediaPlayer.OnSeekCompleteListener {

    KMediaPlayer mediaPlayer;
    private String mVideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/xz/b.mp4";
        if (!new File(mVideoPath).exists()){
            Toast.makeText(this,"文件不存在",Toast.LENGTH_LONG).show();
            return;
        }

        mediaPlayer = new KIjkMediaPlayer(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
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

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mediaPlayer.setDisplay(holder);
            mediaPlayer.setDataSource(mVideoPath);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
