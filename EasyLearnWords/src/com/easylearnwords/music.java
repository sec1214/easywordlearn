package com.easylearnwords;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class music {

	private SoundPool sp;//����һ��SoundPool   
    private int music;//����һ��������load������������suondID    
	private Context context;
	
	public music(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		 sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//��һ������Ϊͬʱ���������������������ڶ����������ͣ�����Ϊ��������   
	        music = sp.load(context, R.raw.right, 1); //����������زķŵ�res/raw���2��������Ϊ��Դ�ļ�����3��Ϊ���ֵ����ȼ�   
	}
	
	public void musicplay() {
		
		 sp.play(music, 1, 1, 0, 0, 1);   
		
	}

}
