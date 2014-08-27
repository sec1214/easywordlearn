package level4;

import com.rootsproject.MainActivity;
import com.rootsproject.R;
import com.rootsproject.mypublicvalue;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.MediaController;

import android.widget.VideoView;

public class testvideo extends Activity {
	private VideoView video;
	private MediaController mediaController;
	private Button skip;
	private CheckBox ignore;
	private mypublicvalue myapp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rootsproject.R.layout.video);
		
		
		
		
		myapp= (mypublicvalue) getApplication();

		if (myapp.getvideo()==0) {
			
		
		
		video = (VideoView) this.findViewById(R.id.video);
		skip = (Button) this. findViewById(R.id.skip);
		ignore = (CheckBox) this.findViewById(R.id.ignore);
		
		mediaController = new MediaController(this);
		video.setVideoURI(Uri.parse("android.resource://com.rootsproject/"
				+ R.raw.testvid));
		video.setMediaController(mediaController);
		video.requestFocus();
		video.start();
	
		
		
		skip.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				video.stopPlayback();
				if (ignore.isChecked()) {
					myapp.setvideo();
				}		
				
				Intent intent=new Intent(testvideo.this,MainActivity.class);
				startActivity(intent);
				finish();
				
				
			}
		});
		
		
		video.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				if (ignore.isChecked()) {
					myapp.setvideo();
				}		
				Intent intent=new Intent(testvideo.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		
		}
		
		if (myapp.getvideo()==1) {
			
			Intent intent=new Intent(testvideo.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		

	}

}
