package com.easylearnwords;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class music {

	private SoundPool sp;//声明一个SoundPool   
    private int music;//定义一个整型用load（）；来设置suondID    
	private Context context;
	
	public music(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		 sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量   
	        music = sp.load(context, R.raw.right, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级   
	}
	
	public void musicplay() {
		
		 sp.play(music, 1, 1, 0, 0, 1);   
		
	}

}
