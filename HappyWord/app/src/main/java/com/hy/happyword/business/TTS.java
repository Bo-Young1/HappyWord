package com.hy.happyword.business;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by 980559 on 2017/4/26.
 */
public class TTS  {
    private TextToSpeech tts;
    public void speak(Context context,String toSay){
        tts = new TextToSpeech(context,ttsInitListener);
        tts.speak(toSay,TextToSpeech.QUEUE_FLUSH,null);
    }

    private TextToSpeech.OnInitListener ttsInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
	      /* 使用美国时区目前不支持中文 */
            Locale loc = new Locale("us", "", "");
	      /* 检查是否支持输入的时区 */
            if (tts.isLanguageAvailable(loc) == TextToSpeech.LANG_AVAILABLE)
            {
	        /* 设定语言 */
                tts.setLanguage(loc);
            }
            tts.setOnUtteranceCompletedListener(ttsUtteranceCompletedListener);
        }
    };

    private TextToSpeech.OnUtteranceCompletedListener ttsUtteranceCompletedListener = new TextToSpeech.OnUtteranceCompletedListener()
    {
        @Override
        public void onUtteranceCompleted(String utteranceId)
        {
        }
    };
}
