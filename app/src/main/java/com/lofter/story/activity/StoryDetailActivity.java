package com.lofter.story.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.lofter.story.R;
import com.lofter.story.base.BaseActivity;

import java.io.InputStream;
import java.util.Locale;

/**
 * Created by asus on 2018-1-3.
 */

public class StoryDetailActivity extends BaseActivity {

    private static String TAG = StoryDetailActivity.class.getSimpleName();

    private TextView mTvText;
    private ImageView mIvPlay;
    private ImageView mIvPause;
    private ImageView mIvStop;
    private ImageView mIvSelect;

    private String title;
    private String content;

    // 语音合成对象
    private SpeechSynthesizer mTts;

    // 默认发音人
    private String voicer = "nannan";

    private int selectedNum = 0;

    private String[] mCloudVoicersEntries;
    private String[] mCloudVoicersValue;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    private boolean isPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        setTitle(title);

        initTTS();

        initView();
    }

    private void initView() {
        mTvText = findViewById(R.id.textView_content);
        mIvPlay = findViewById(R.id.imageview_play);
        mIvPause = findViewById(R.id.imageview_pause);
        mIvStop = findViewById(R.id.imageview_stop);
        mIvSelect = findViewById(R.id.imageview_select);

        mTvText.setText(content);

        mIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 移动数据分析，收集开始合成事件
                FlowerCollector.onEvent(StoryDetailActivity.this, "tts_play");

                setParam();
                int code = mTts.startSpeaking(content, mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
//			String path = Environment.getExternalStorageDirectory()+"/tts.ico";
//			int code = mTts.synthesizeToUri(text, path, mTtsListener);

                if (code != ErrorCode.SUCCESS) {
                    Toast("语音合成失败,错误码: " + code);
                }
            }
        });
        mIvPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPause) {
                    mTts.pauseSpeaking();
                    isPause = true;
                } else {
                    mTts.resumeSpeaking();
                    isPause = false;
                }

            }
        });
        mIvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTts.stopSpeaking();
            }
        });
        mIvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }


    private void initTTS() {
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);

        // 云端发音人名称列表
        mCloudVoicersEntries = getResources().getStringArray(R.array.voicer_cloud_entries);
        mCloudVoicersValue = getResources().getStringArray(R.array.voicer_cloud_values);
    }

    private void showDialog() {
        new AlertDialog.Builder(this).setTitle("发音人")
                .setSingleChoiceItems(mCloudVoicersEntries,
                        selectedNum, // 默认的选项
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                voicer = mCloudVoicersValue[which];
                                selectedNum = which;
                                dialog.dismiss();
                            }
                        }).show();
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Toast("初始化失败,错误码：" + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            Toast("开始播放");
        }

        @Override
        public void onSpeakPaused() {
            Toast("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            Toast("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {

        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {

        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                Toast("播放完成");
            } else if (error != null) {
                Toast(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }

    @Override
    protected void onResume() {
        //移动数据统计分析
        FlowerCollector.onResume(this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        //移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }

}
