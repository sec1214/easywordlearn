package com.easylearnwords;

import java.util.ArrayList;
import java.util.List;

import level2.definitionl2;
import level2.idroortsl2;
import level2.wordsl2;
import level3.missroot;
import level4.idroortsl4;
import level4.testtime;

import Database.managedb;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class play extends Activity {

	private TextView textView1, textView2;
	private mypublicvalue myapp;
	private ListView levelListView;
	private ArrayAdapter<String> adapter;
	private List<String> list = new ArrayList<String>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);

		textView1 = (TextView) this.findViewById(R.id.textview1);
		textView2 = (TextView) this.findViewById(R.id.textview2);

		myapp = (mypublicvalue) getApplication();

		textView1.setText(myapp.get(0));
		textView2.setText(myapp.get(1));

		levelListView = (ListView) this.findViewById(R.id.levellist);

		this.addlistinformation();

		adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_single_choice, list);

		levelListView.setChoiceMode(levelListView.CHOICE_MODE_SINGLE);
		levelListView.setAdapter(adapter);

		levelListView.setOnItemClickListener(new OnItemClickListener() {

			// managedb db = new managedb(getBaseContext()); /// 因为在list选择界面已经调入words

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				myapp.set(3, Integer.toString(position + 1)); // 存入level等级

			//	 myapp.setwords(db.getwords()); /// 因为在list选择界面已经调入words

				if (position == 0) {

					double h = Math.random();
/*
					 if (h<0.5) {
					Intent intent = new Intent(play.this, words.class);
					
					startActivity(intent);

					// finish();
					 }
					 else {*/
					 Intent intent = new Intent(play.this,definition.class);
				     startActivity(intent);
			/*		 }*/

				}
				if (position==1) {
					double h = Math.random();

					 if (h<0.5) {
					Intent intent = new Intent(play.this, wordsl2.class);
					
					startActivity(intent);

					// finish();
					 }
					 else {
					 Intent intent = new Intent(play.this,definitionl2.class);
				     startActivity(intent);
					 }
					
					
					
					
				} 
				
				if (position==2) {  //选择level3
					
					Intent intent=new Intent(play.this,missroot.class);
					startActivity(intent);
				}
				
				if (position==3) { //选择level4
					Intent intent=new Intent(play.this,testtime.class);
					startActivity(intent);
				}
				
				
				
				finish();
			}
		});

	}

	private void addlistinformation() {

		list.add(this.getString(R.string.level1));
		list.add(this.getString(R.string.level2));

		list.add(this.getString(R.string.level3));

		list.add(this.getString(R.string.level4));
		list.add(this.getString(R.string.level5));

		list.add(this.getString(R.string.level6));

		list.add(this.getString(R.string.level7));

		list.add(this.getString(R.string.level8));

	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			  
			
			Intent intent=new Intent(play.this,list.class);
			startActivity(intent);
			finish();
			
		}
		
		
		
		
		return super.onKeyDown(keyCode, event);
	}

}
