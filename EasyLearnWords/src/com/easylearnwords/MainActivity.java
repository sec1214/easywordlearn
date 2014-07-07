package com.easylearnwords;

import java.util.List;

import Database.managedb;
import android.app.Activity;
import android.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView listView;
	private ArrayAdapter<String> adapter;
	private mypublicvalue myapp;
	private Dialog alertdDialog;
	 private LinearLayout splash;  
	 private TextView tv;  
	/*	private SoundPool sp;//����һ��SoundPool   
	    private int music;//����һ��������load������������suondID  
*/	
	 
	 private static final int STOPSPLASH = 0;  
	    // time in milliseconds  
	    private static final long SPLASHTIME = 1000;  
	    
	    
	    private Handler splashHandler = new Handler() {  
	        public void handleMessage(Message msg) {  
	            switch (msg.what) {  
	            case STOPSPLASH:  
	                SystemClock.sleep(500);      
	                splash.setVisibility(View.GONE);  
	                    break;  
	            }  
	            super.handleMessage(msg);  
	        }  
	    };  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_PROGRESS);  
		
		setContentView(R.layout.main);
		
		splash = (LinearLayout) findViewById(R.id.splashscreen);  

		/* sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//��һ������Ϊͬʱ���������������������ڶ����������ͣ�����Ϊ��������   
	        music = sp.load(MainActivity.this, R.raw.right, 1); //����������زķŵ�res/raw���2��������Ϊ��Դ�ļ�����3��Ϊ���ֵ����ȼ�   
*/        
        Message msg = new Message();  
        msg.what = STOPSPLASH;  
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);  
		
		
       
        
        
		managedb importDB = new managedb(this);
		// System.out.println(importDB.path()); //������ݿ�·��
		importDB.CopyDatabase(); // �������ݿ�
		List<String> list = new managedb(this).listtablename(null);
		System.out.println(list); // �����������ݿ��еı�����

		// System.out.println("test");

		listView = (ListView) this.findViewById(R.id.listview);

		adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_single_choice, list);

		listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int postion, long arg3) {
				// TODO Auto-generated method stub

				

   
		/*		 sp.play(music, 1, 1, 0, 0, 1);   */
				
				
				
				myapp = (mypublicvalue) getApplication(); // ����������ֵ
				String value = (String) listView.getItemAtPosition(postion) // postion
																			// ��һλ��0��ʼ��
						.toString();
				myapp.set(0, value); // 0 �����ݵ��Ǳ���

				myapp.playmusic(1);
				Toast.makeText(MainActivity.this, myapp.get(0), 1).show(); // ��ʾ����Ŀγ�Ҳ���Ǳ���

				Intent intent = new Intent(MainActivity.this,
						listselectactivity.class);
				


				startActivity(intent);

			}
		});

		

		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			alertdDialog = new AlertDialog.Builder(this)
			.setTitle("EXIT")
			.setMessage("Do you want to exit complete��")
			.setIcon(R.drawable.ic_launcher)
			.setPositiveButton("Confirm",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							System.exit(0);

						}
					})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

					alertdDialog.cancel();
				}
			}).create();
			
			alertdDialog.show();
			
		}
		
		
		
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
