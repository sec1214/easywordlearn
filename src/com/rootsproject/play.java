package com.rootsproject;

import java.util.ArrayList;
import java.util.List;

import level2.tutorial2;
import level3.tutorial3;
import level4.tutorial4;
import level5.tutorial5;
import level6.rootl6;
import level6.tutorial6;
import level7.missrootl7;
import level7.tutorial7;
import level8.definitionl8;
import level8.tutorial8;
import level8.wordsl8;

import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import Database.managedb;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rootsproject.R;

public class play extends AppCompatActivity {

	private TextView textView2;
	private mypublicvalue myapp;
	private ListView levelListView;
	private ArrayAdapter<String> adapter;
	private List<String> list = new ArrayList<String>();
	private BroadcastReceiver receiver; // home key

	private CallbackManager callbackManager;
	private ShareDialog shareDialog;
	private ShareButton share;
	private LikeView like;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myapp = (mypublicvalue) getApplication();

		android.support.v7.app.ActionBar ab = getSupportActionBar();
		ab.setTitle(underlineclear(myapp.get(0)));
		ab.setSubtitle(myapp.get(1));

		setContentView(R.layout.zplay);

		receiver = new HomeKeyEventBroadCastReceiver();
		getApplicationContext().registerReceiver(receiver,
				new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

		myapp.startsplashmusic();

		callbackManager = CallbackManager.Factory.create();
		shareDialog = new ShareDialog(this);

		// configure Share Button
		ShareLinkContent content = new ShareLinkContent.Builder()
				.setContentUrl(Uri.parse("http://roots.nyc/"))
				.setContentTitle("Roots: Play with words")
				.setImageUrl(Uri.parse("http://roots.nyc/wp-content/uploads/2015/09/RootsPlayWords_TransWoutline1.png"))
				.setContentDescription("Check out Roots, a mobile game that teaches vocabulary "
						+ "through etymology! It's a great tool for students "
						+ "studying for the SAT and ESL students. \n"
						+ "Get it now on the Google Play Store! \n"
						+ "https://play.google.com/store/apps/details?id=com.rootsproject")
				.build();

		share = (ShareButton) findViewById(R.id.share);
		share.setShareContent(content);

		like = (LikeView) findViewById(R.id.likeView);
		like.setObjectIdAndType("https://www.facebook.com/rootsmobileapp", LikeView.ObjectType.UNKNOWN);

		managedb db = new managedb(getBaseContext()); // store 20 words in
														// public value, because
														// in some condition,
														// other activity will
														// turn to this activity
														// so store many times 
		myapp.setwords(db.getwords());

		levelListView = (ListView) this.findViewById(R.id.levellist);

		this.addlistinformation(); // assign level name strings to list.

		adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, list);

	//	levelListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		levelListView.setAdapter(adapter);

		levelListView.setOnItemClickListener(new OnItemClickListener() {

			// managedb db = new managedb(getBaseContext()); ///
			// ��Ϊ��listѡ������Ѿ�����words

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				myapp.playmusic(1);

				myapp.set(3, Integer.toString(position + 1)); // store level to public value ����level�ȼ�

				myapp.stopsplashmusic();

				// myapp.setwords(db.getwords()); /// ��Ϊ��listѡ������Ѿ�����words

				myapp.startlevelmusic();
				
				/*combo count returns to zero*/
				myapp.setComboBeginningState();
				
				if (position == 0) {  // level 1
					Intent intent = new Intent(play.this, tutorial.class);
					startActivity(intent);
				}
				if (position == 1) {  // level 2
					Intent intent = new Intent(play.this, tutorial2.class);
					startActivity(intent);

				}

				if (position == 2) { // choose level3

					Intent intent = new Intent(play.this, tutorial3.class);
					startActivity(intent);
					
				}

				if (position == 3) { // choose level4

					Intent intent = new Intent(play.this, tutorial4.class);
					startActivity(intent);

				}

				if (position == 4) { // choose level5

					Intent intent = new Intent(play.this, tutorial5.class);
					startActivity(intent);

				}
				if (position == 5) { // choose level6

					Intent intent = new Intent(play.this, tutorial6.class);
					startActivity(intent);

				}
				if (position == 6) { // choose level7

					Intent intent = new Intent(play.this, tutorial7.class);
					startActivity(intent);

				}
				if (position == 7) { // choose level8

					Intent intent = new Intent(play.this, tutorial8.class);
					startActivity(intent);

				}

				finish();
			}
		});

	}

	private void addlistinformation() {  // assign level name 

		list.add(this.getString(R.string.level1));
		list.add(this.getString(R.string.level2));

		list.add(this.getString(R.string.level3));

		list.add(this.getString(R.string.level4));
		list.add(this.getString(R.string.level5));

		list.add(this.getString(R.string.level6));

		list.add(this.getString(R.string.level7));

		list.add(this.getString(R.string.level8));

	}

	public String underlineclear(String key) {
		String flag = key.replace("_", " ");
		return flag;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent intent = new Intent(play.this, list.class);
			startActivity(intent);
			finish();

		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		myapp.startsplashmusic();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getApplicationContext().unregisterReceiver(receiver);

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
						myapp.pausesplashmusic(); // home key�����

					} else if (reason.equals(SYSTEM_RECENT_APPS)) {
						myapp.pausesplashmusic();// long home key�����
					}
				}
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play, menu);

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

		if (id == R.id.PlayStudyReview) {
			Intent intent = new Intent(play.this, list.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.listpage) {
			Intent intent = new Intent(play.this, listselectactivity.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.coursepage) {
			Intent intent = new Intent(play.this, MainActivity.class);
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
			Toast.makeText(play.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
