package com.rootsproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.rootsproject.R;

public class scoreword extends AppCompatActivity {
	private Dialog alertdDialog;
	private TextView scoredef;
	private Button backButton;
	private mypublicvalue myapp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String word = intent.getStringExtra("name"); // take the word's string by intent method.

		myapp = (mypublicvalue) getApplication();

		android.support.v7.app.ActionBar ab = getSupportActionBar();
		ab.setTitle(word);
		setContentView(R.layout.zscoreword);

		scoredef = (TextView) this.findViewById(R.id.scoredef);
		backButton = (Button) this.findViewById(R.id.backscore);

		String string = intent.getStringExtra("string");
		scoredef.setText(string);

		backButton.setOnClickListener(new View.OnClickListener() {  // backButton is finish the acitivity.

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	public String underlineclear(String key) {
		String flag = key.replace("_", " ");
		return flag;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			/*
			Intent intent = new Intent(scoreword.this,
					play.class);
			startActivity(intent);*/
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);

		MenuItem musicsound = menu.add(101, 1, 1, "musicsound");
		MenuItem buttonsound = menu.add(101, 2, 2, "buttonsound");
		musicsound.setCheckable(true);
		buttonsound.setCheckable(true);
		if (myapp.getmusic(0) == 0) {
			buttonsound.setChecked(false);

		} else {
			buttonsound.setChecked(true);
		}
		if (myapp.getmusic(1) == 0) {
			musicsound.setChecked(false);

		} else {
			musicsound.setChecked(true);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.level) {
		
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(scoreword.this, play.class);
			startActivity(intent);
			finish();
		}

		
		if (id == R.id.PlayStudyReview) {
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(scoreword.this, list.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.listpage) {
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(scoreword.this, listselectactivity.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.coursepage) {
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(scoreword.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		if (id == R.id.Exit) {

			System.exit(0);

			return true;
		}

		if (id == 1) { // musicsound

			if (item.isChecked()) {
				item.setChecked(false);
				myapp.setmusic(1, 0);
				myapp.stoplevelmusic();

			} else {
				item.setChecked(true);
				myapp.setmusic(1, 1);
				myapp.startlevelmusic();

			}
			return true;
		}

		if (id == 2) {

			if (item.isChecked()) {
				item.setChecked(false);
				myapp.setmusic(0, 0);
			} else {
				item.setChecked(true);
				myapp.setmusic(0, 1);
			}
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


}