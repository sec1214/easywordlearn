package com.easylearnwords;

import java.util.ArrayList;
import java.util.List;

import Database.managedb;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class studyword extends Activity {

	private Dialog alertdDialog;
	private TextView textView1, textView2;
	private ListView wordListView;
	private mypublicvalue myapp;
	private ArrayAdapter<String> adapter;
	private String[][] words;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordlist);

		textView1 = (TextView) this.findViewById(R.id.textview1);
		textView2 = (TextView) this.findViewById(R.id.textview2);

		myapp = (mypublicvalue) getApplication();

		textView1.setText(myapp.get(0));
		textView2.setText(myapp.get(1));

		wordListView = (ListView) this.findViewById(R.id.wordlistview);

		
		
		words = myapp.getwords();
		List<String> list = new ArrayList<String>();

		/*int a1 = words.length;
		int a2 = words[0].length;
		
		
		 System.out.println("Î¬¶È"+a1+a2);*/
		
		for (int i = 0; i < 20; i++) {
			
			String string = null;

			if (!words[i][0].equals("")) {

				string = words[i][0] + ": " + words[i][1]+".\n"+"\n";
			
			if (!words[i][2].equals("")) {

				string = string+words[i][2] + ": " + words[i][3]+".\n"+"\n";
			}
			if (!words[i][4] .equals("")) {

				string = string+words[i][4] + ": " + words[i][5]+".\n"+"\n";
			}
			if (!words[i][6] .equals("")) {

				string = string+words[i][6] + ": " + words[i][7]+".\n"+"\n";
			}
			if (!words[i][8] .equals("")) {

				string = string+words[i][8] + ": " + words[i][9]+".\n"+"\n";
			}
			
			
			list.add(string);}

		}
		
		//System.out.println(list);
		
		adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, list);
		wordListView.setAdapter(adapter);
		

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			alertdDialog = new AlertDialog.Builder(this)
			.setTitle("EXIT LEVEL")
			.setMessage("Do you want to exit this list word£¿")
			.setIcon(R.drawable.ic_launcher)
			.setPositiveButton("Confirm",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub

							
							Intent intent = new Intent(studyword.this,list.class);
							startActivity(intent);
							finish();

						}
					})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
                  /*  Intent intent= new Intent(Word.this,Play.class);
                    startActivity(intent);*/
					alertdDialog.cancel();
				}
			}).create();
			
			alertdDialog.show();
			
		}
		
		
		
		
		return super.onKeyDown(keyCode, event);
	}

}
