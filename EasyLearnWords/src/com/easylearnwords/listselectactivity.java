package com.easylearnwords;

import java.util.ArrayList;
import java.util.List;

import Database.managedb;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class listselectactivity extends Activity {

	private mypublicvalue myapp;
	private TextView ListtextView;
	private ArrayAdapter<String> adapter;
	private ListView ListlistView;
	private  managedb db;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listselect);

		myapp = (mypublicvalue) getApplication();

		String tablename = myapp.get(0);  //得到表名

		ListtextView = (TextView) this.findViewById(R.id.Listtextview);

		ListtextView.setText(tablename);

	     db = new managedb(this);
	     
	     int numlist = db.numlist(tablename);
		System.out.println("numberlist of the course "+numlist); // 计算有多少list  因为每个表至少包含5个单词所以必须有1个list
	
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < numlist; i++) {
			String listname = "List " + (i + 1);
			list.add(listname);     

		}

		adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_single_choice, list);
		ListlistView = (ListView) this.findViewById(R.id.Listlistview);
		
		ListlistView.setChoiceMode(ListlistView.CHOICE_MODE_SINGLE);
		ListlistView.setAdapter(adapter);
		
		ListlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
				myapp = (mypublicvalue) getApplication(); // 公共变量传值
				String value = (String) ListlistView.getItemAtPosition(position)  //postion 1号位 从0开始算
						.toString();
				myapp.set(1, value); // 0 代表传递的是表名，1代表传递的list名称
				myapp.set(2, Integer.toString(position+1));  // 所以需要转换为+1

				
			//	myapp.setwords(db.getwords());
				
				Toast.makeText(listselectactivity.this, myapp.get(1), 1).show();

				Intent intent = new Intent(listselectactivity.this,
						com.easylearnwords.list.class);
				finish();
				startActivity(intent);
				
				
				//System.out.println(myapp.get(2)+"myapptest");
				
			}
		});
		
		
		
		

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			  
			
			Intent intent=new Intent(listselectactivity.this,MainActivity.class);
			startActivity(intent);
			finish();
			
		}
		
		
		
		
		return super.onKeyDown(keyCode, event);
	}
}
