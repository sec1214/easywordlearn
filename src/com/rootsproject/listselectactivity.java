package com.rootsproject;

import java.util.ArrayList;
import java.util.List;

import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import Database.managedb;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rootsproject.R;

public class listselectactivity extends AppCompatActivity {

	private mypublicvalue myapp;
	private ArrayAdapter<String> adapter;
	private ListView ListlistView; // the listview display the list number.
	private managedb db; // call database
	private BroadcastReceiver receiver; // the receiver is used to control Home
										// key

	private ShareButton share;
	private LikeView like;

	/* added by yi wan, 2015-01-03, over */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myapp = (mypublicvalue) getApplication();

		String tablename = myapp.get(0);

		android.support.v7.app.ActionBar ab = getSupportActionBar();
		ab.setTitle(underlineclear(tablename));

		setContentView(R.layout.zlistselect);

		receiver = new HomeKeyEventBroadCastReceiver();
		getApplicationContext().registerReceiver(receiver,
				new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));


		// myapp.setreceiver(receiver);
		myapp.startsplashmusic();

		db = new managedb(this);

		int numlist = db.numlist(tablename); // calculate the amount of list

		myapp.setlistnum(numlist); // store the amount of list to public values.
									// it will be used to control bring 20
									// words.

		System.out.println("numberlist of the course " + numlist); // �����ж���list
																	// ��Ϊÿ�������ٰ���5���������Ա�����1��list

		List<String> list = new ArrayList<String>();

		for (int i = 0; i < numlist; i++) {
			//String listname = "List " + (i + 1);
			String listname = "Words " + (i * 20 + 1) + " - " + (i+1) * 20;
			list.add(listname);

		}

		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
				list);
		ListlistView = (ListView) this.findViewById(R.id.Listlistview);

		// ListlistView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		ListlistView.setAdapter(adapter);

		ListlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
									int position, long arg3) {
				// TODO Auto-generated method stub

				myapp.playmusic(1); // button sound
				String value = ListlistView.getItemAtPosition(position) // //
						// the
						// begin
						// of
						// postion
						// is 0;
						.toString();
				myapp.set(1, value); // store list name to public value 0
				// �����ݵ��Ǳ�����1�����ݵ�list����
				myapp.set(2, Integer.toString(position + 1)); // store list
				// number to
				// public value
				// ������Ҫת��Ϊ+1

				// myapp.setwords(db.getwords());

				Toast.makeText(listselectactivity.this, myapp.get(1), Toast.LENGTH_SHORT).show();

				/* added by xiaoqian yu, 2014-12-21, start */
				EasyTracker easyTracker = EasyTracker
						.getInstance(listselectactivity.this);
				String category = "ListSelectionOf_" + myapp.get(0);
				// MapBuilder.createEvent().build() returns a Map of event
				// fields and values
				// that are set and sent with the hit.
				easyTracker.send(MapBuilder.createEvent(category, // Event
						// category
						// (required)
						"button_press_to_select_list", // Event action
						// (required)
						myapp.get(1), // Event label
						null) // Event value
						.build());
				/* added by xiaoqian yu, 2014-12-21, end */

				Intent intent = new Intent(listselectactivity.this,
						com.rootsproject.list.class);
				startActivity(intent);
				finish();
				// System.out.println(myapp.get(2)+"myapptest");

			}
		});

		ShareLinkContent content = new ShareLinkContent.Builder()
				.setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.rootsproject"))
				.setContentTitle("Roots: Play with words")
				.setImageUrl(Uri.parse("http://roots.nyc/wp-content/uploads/2015/09/RootsPlayWords_TransWoutline1.png"))
				.setContentDescription("Check out Roots, a mobile game that teaches vocabulary "
						+ "through etymology! It's a great tool for students "
						+ "studying for the SAT and ESL students. \n"
						+ "Get it now on the Google Play Store!")
				.build();

		share = (ShareButton) findViewById(R.id.share);
		share.setShareContent(content);

		like = (LikeView) findViewById(R.id.likeView);
		like.setObjectIdAndType("https://www.facebook.com/rootsmobileapp", LikeView.ObjectType.UNKNOWN);
		like.setLikeViewStyle(LikeView.Style.BOX_COUNT);

	}

	public String underlineclear(String key) { // this method is used to delele
												// the _ from course name
		String flag = key.replace("_", " ");
		return flag;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) { // key back event

			Intent intent = new Intent(listselectactivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();

		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("Start");

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		myapp.startsplashmusic();
		System.out.println("Restart");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("Resume");

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("Stop");

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("Pause");

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("Destroy");
		getApplicationContext().unregisterReceiver(receiver);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	protected class HomeKeyEventBroadCastReceiver extends BroadcastReceiver {

		static final String SYSTEM_REASON = "reason";
		static final String SYSTEM_HOME_KEY = "homekey";// home key
		static final String SYSTEM_RECENT_APPS = "recentapps";// long home key

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
				String reason = intent.getStringExtra(SYSTEM_REASON);
				if (reason != null) {
					if (reason.equals(SYSTEM_HOME_KEY)) {
						myapp.pausesplashmusic(); // home key event ����ￄ1�7

					} else if (reason.equals(SYSTEM_RECENT_APPS)) {
						myapp.pausesplashmusic();// long home key event ����ￄ1�7
					}
				}
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.listselect, menu);

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

		if (id == R.id.mainpage) {
			Intent intent = new Intent(listselectactivity.this,
					MainActivity.class);
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
				myapp.stopsplashmusic();
			} else {
				item.setChecked(true);
				myapp.setmusic(1, 1);
				myapp.startsplashmusic();

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

	public void onClickSend(View v) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_SUBJECT, "Roots: Play With Words");
		i.putExtra(Intent.EXTRA_TEXT   , "Check out Roots, a mobile game that teaches vocabulary "
				+ "through etymology! It's a great tool for students "
				+ "studying for the SAT and ESL students. Download it on the Play Store now! "
				+ "https://play.google.com/store/apps/details?id=com.rootsproject");
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(listselectactivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
