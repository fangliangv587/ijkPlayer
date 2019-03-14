package com.kuaifa.lib.kfplayer.media;

import android.content.Context;
import android.text.TextUtils;
import android.view.SurfaceHolder;

import com.kuaifa.lib.kfplayer.ijk.Settings;

import java.io.IOException;

import tv.danmaku.ijk.media.exo.IjkExoMediaPlayer;
import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;
import tv.danmaku.ijk.media.player.TextureMediaPlayer;

/**
 * Created by Administrator on 2019/3/13.
 */

public class KIjkMediaPlayer implements KMediaPlayer {


    private IMediaPlayer mMediaPlayer;
    private Context mContext;

    public KIjkMediaPlayer(Context context) {
        this.mContext = context;
        mMediaPlayer = createPlayer(Settings.PV_PLAYER__Auto);
    }


    public IMediaPlayer createPlayer(int playerType) {
        IMediaPlayer mediaPlayer = null;
        Settings mSettings = new Settings(mContext);
        switch (playerType) {
            case Settings.PV_PLAYER__IjkExoMediaPlayer: {
                IjkExoMediaPlayer IjkExoMediaPlayer = new IjkExoMediaPlayer(mContext);
                mediaPlayer = IjkExoMediaPlayer;
            }
            break;
            case Settings.PV_PLAYER__AndroidMediaPlayer: {
                AndroidMediaPlayer androidMediaPlayer = new AndroidMediaPlayer();
                mediaPlayer = androidMediaPlayer;
            }
            break;
            case Settings.PV_PLAYER__IjkMediaPlayer:
            default: {
                IjkMediaPlayer ijkMediaPlayer = null;

                    ijkMediaPlayer = new IjkMediaPlayer();
                    ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);

                    if (mSettings.getUsingMediaCodec()) {
                        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
                        if (mSettings.getUsingMediaCodecAutoRotate()) {
                            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
                        } else {
                            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 0);
                        }
                        if (mSettings.getMediaCodecHandleResolutionChange()) {
                            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
                        } else {
                            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 0);
                        }
                    } else {
                        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
                    }

                    if (mSettings.getUsingOpenSLES()) {
                        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1);
                    } else {
                        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0);
                    }

                    String pixelFormat = mSettings.getPixelFormat();
                    if (TextUtils.isEmpty(pixelFormat)) {
                        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32);
                    } else {
                        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", pixelFormat);
                    }
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);

                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);

                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);

                mediaPlayer = ijkMediaPlayer;
            }
            break;
        }

        if (mSettings.getEnableDetachedSurfaceTextureView()) {
            mediaPlayer = new TextureMediaPlayer(mediaPlayer);
        }

        return mediaPlayer;
    }

    @Override
    public void setDisplay(SurfaceHolder holder) {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.setDisplay(holder);
    }

    @Override
    public void setAudioStreamType(int streamtype) {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.setAudioStreamType(streamtype);
    }

    @Override
    public void setScreenOnWhilePlaying(boolean screenOn) {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.setScreenOnWhilePlaying(screenOn);
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.setDataSource(path);
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.prepareAsync();
    }

    @Override
    public long getCurrentPosition() {
        if (mMediaPlayer==null){
            return 0;
        }
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        if (mMediaPlayer==null){
            return 0;
        }
        return mMediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        if (mMediaPlayer==null){
            return false;
        }
        return mMediaPlayer.isPlaying();
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.setVolume(leftVolume,rightVolume);
    }

    @Override
    public void start() throws IllegalStateException {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.start();
    }

    @Override
    public void seekTo(long msec) throws IllegalStateException {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.seekTo(msec);
    }

    @Override
    public void stop() throws IllegalStateException {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.stop();
    }

    @Override
    public void pause() throws IllegalStateException {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.pause();
    }

    @Override
    public void reset() {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.reset();
    }

    @Override
    public void release() {
        if (mMediaPlayer==null){
            return;
        }
        mMediaPlayer.release();
    }

    @Override
    public void setOnPreparedListener(final OnPreparedListener listener) {
        if (mMediaPlayer==null || listener==null){
            return;
        }
        mMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                listener.onPrepared(KIjkMediaPlayer.this);
            }
        });
    }

    @Override
    public void setOnCompletionListener(final OnCompletionListener listener) {
        if (mMediaPlayer==null || listener==null){
            return;
        }
        mMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                listener.onCompletion(KIjkMediaPlayer.this);
            }
        });
    }

    @Override
    public void setOnBufferingUpdateListener(final OnBufferingUpdateListener listener) {
        if (mMediaPlayer==null || listener==null){
            return;
        }
        mMediaPlayer.setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
                listener.onBufferingUpdate(KIjkMediaPlayer.this,percent);
            }
        });
    }

    @Override
    public void setOnSeekCompleteListener(final OnSeekCompleteListener listener) {
        if (mMediaPlayer==null || listener==null){
            return;
        }
        mMediaPlayer.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(IMediaPlayer mp) {
                listener.onSeekComplete(KIjkMediaPlayer.this);
            }
        });
    }

    @Override
    public void setOnVideoSizeChangedListener(final OnVideoSizeChangedListener listener) {
        if (mMediaPlayer==null || listener==null){
            return;
        }
        mMediaPlayer.setOnVideoSizeChangedListener(new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
                listener.onVideoSizeChanged(KIjkMediaPlayer.this,width,height,sar_num,sar_den);
            }
        });
    }

    @Override
    public void setOnErrorListener(final OnErrorListener listener) {
        if (mMediaPlayer==null || listener==null){
            return;
        }
        mMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {

                return listener.onError(KIjkMediaPlayer.this,what,extra);
            }
        });
    }

    @Override
    public void setOnInfoListener(final OnInfoListener listener) {
        if (mMediaPlayer==null || listener==null){
            return;
        }
        mMediaPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {

                return listener.onInfo(KIjkMediaPlayer.this,what,extra);
            }
        });
    }

    @Override
    public void setOnTimedTextListener(final OnTimedTextListener listener) {
        if (mMediaPlayer==null || listener==null){
            return;
        }
        mMediaPlayer.setOnTimedTextListener(new IMediaPlayer.OnTimedTextListener() {
            @Override
            public void onTimedText(IMediaPlayer mp, IjkTimedText text) {
                listener.onTimedText(KIjkMediaPlayer.this);
            }
        });
    }
}
