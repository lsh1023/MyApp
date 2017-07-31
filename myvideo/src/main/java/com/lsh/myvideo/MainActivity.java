package com.lsh.myvideo;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * 多媒体视频播放器
 */
public class MainActivity extends AppCompatActivity {

    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = (VideoView) findViewById(R.id.videoView);

        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"2017.mp4";
        /**
         * 本地视频播放
         */
//        mVideoView.setVideoPath(path);

        /**
         * 网络播放
         * http://221.228.226.23/6/n/a/y/l/naylspkwvsujoltcqursegarxzowax/hd.yinyuetai.com/C02F015B377EA255563C19FBEF88B071.mp4
         */
        mVideoView.setVideoURI(Uri.parse("http://221.228.226.23/6/n/a/y/l/naylspkwvsujoltcqursegarxzowax/hd.yinyuetai.com/C02F015B377EA255563C19FBEF88B071.mp4"));


//        /**
//         * 使用MediaController控制视频播放
//         */
//        MediaController controller = new MediaController(this);
//        /**
//         * 设置VideoView与MediaController建立关联
//         */
//        mVideoView.setMediaController(controller);
//        /**
//         * 设置MediaController与VideoView建立关联
//         */
//        controller.setMediaPlayer(mVideoView);

    }
}
