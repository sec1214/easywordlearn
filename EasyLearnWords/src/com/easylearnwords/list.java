package com.easylearnwords;

import Database.managedb;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class list extends Activity {

	private mypublicvalue myapp;
	private TextView textView1, textView2;
	private Button playButton, studyButton, reviewButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		textView1 = (TextView) this.findViewById(R.id.textview1);
		textView2 = (TextView) this.findViewById(R.id.textview2);

		myapp = (mypublicvalue) getApplication();
		
		managedb db= new managedb(getBaseContext());
		myapp.setwords(db.getwords());

		textView1.setText(myapp.get(0));
		textView2.setText(myapp.get(1));

		playButton = (Button) this.findViewById(R.id.button1);
		studyButton = (Button) this.findViewById(R.id.button2);
		reviewButton = (Button) this.findViewById(R.id.button3);

		studyButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				studyButton.setBackgroundResource(R.drawable.green);
				
				Intent intent = new Intent(list.this, studyword.class);
			
				startActivity(intent);
				finish();
			}
		});

		playButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				playButton.setBackgroundResource(R.drawable.green);
				Intent intent = new Intent(list.this, play.class);
	
				startActivity(intent);
				finish();
			}
		});
		
		reviewButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			  
			
			Intent intent=new Intent(list.this,listselectactivity.class);
			startActivity(intent);
			finish();
			
		}
		
		
		
		
		return super.onKeyDown(keyCode, event);
	}

}
