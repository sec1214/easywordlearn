package com.rootsproject;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.MediaController;
import android.widget.VideoView;
import com.rootsproject.R;

public class videointro extends AppCompatActivity {

	public static final String PREFS_NAME = "RootsPrefsFile";

	private VideoView video;
	private MediaController mediaController;
	private Button skip;
	private CheckBox ignore;
	private mypublicvalue myapp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(com.rootsproject.R.layout.zvideo);

		android.support.v7.app.ActionBar ab = getSupportActionBar();
		ab.setTitle("Roots: Play with Words");
		
		
		myapp= (mypublicvalue) getApplication();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		final SharedPreferences.Editor editor = settings.edit();

		//if (myapp.getvideo()==0) {
		if (!settings.getBoolean("skipVideo", false)) {
			
		
		
		video = (VideoView) this.findViewById(R.id.video);
		skip = (Button) this.findViewById(R.id.skip);
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
					//myapp.setvideo();
					editor.putBoolean("skipVideo", true);
					editor.commit();
				}
				else {
					editor.putBoolean("skipVideo", false);
					editor.commit();
				}


				Intent intent = new Intent(videointro.this, MainActivity.class);
				startActivity(intent);
				finish();


			}
		});
		
		
		video.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				if (ignore.isChecked()) {
					//myapp.setvideo();
					editor.putBoolean("skipVideo", true);
					editor.commit();
				}
				else {
					editor.putBoolean("skipVideo", false);
					editor.commit();
				}

				video.stopPlayback();
				Intent intent = new Intent(videointro.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		
		}
		
		//if (myapp.getvideo()==1) {
		if (settings.getBoolean("skipVideo", false)) {
			
			Intent intent=new Intent(videointro.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		

	}

}
