package review;

import java.util.ArrayList;
import java.util.List;

import com.rootsproject.MainActivity;
import com.rootsproject.R;
import com.rootsproject.definition;
import com.rootsproject.list;
import com.rootsproject.listselectactivity;
import com.rootsproject.mypublicvalue;

import Database.managedb;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class reviwlevel extends AppCompatActivity {

	private TextView textView2;
	private mypublicvalue myapp;
	private ListView levelListView;
	private SpecialAdapter adapter;
	private List<String> list = new ArrayList<String>();
	private BroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myapp = (mypublicvalue) getApplication();

		android.support.v7.app.ActionBar ab = getSupportActionBar();
		ab.setTitle(underlineclear(myapp.get(0)));
		setContentView(R.layout.zplay);

		receiver = new HomeKeyEventBroadCastReceiver();
		getApplicationContext().registerReceiver(receiver,
				new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
		textView2 = (TextView) this.findViewById(R.id.textview2);

		managedb db = new managedb(getBaseContext());
		myapp.setwords(db.getwords());

		textView2.setText(myapp.get(1));

		levelListView = (ListView) this.findViewById(R.id.levellist);

		this.addlistinformation();

		adapter = new SpecialAdapter(this,
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

				managedb db = new managedb(getBaseContext());

				// myapp.setwords(db.getwords()); /// ��Ϊ��listѡ������Ѿ�����words

				if (db.levelexist(Integer.toString(position + 1))) {

					myapp.playmusic(1);

					myapp.set(3, Integer.toString(position + 1)); // ����level�ȼ�
					Intent intent = new Intent(reviwlevel.this,
							reviewscore.class);
					startActivity(intent);
					finish();
				}

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

	public String underlineclear(String key) {
		String flag = key.replace("_", " ");
		return flag;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent intent = new Intent(reviwlevel.this, list.class);
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
						myapp.pausesplashmusic(); // long home key�����
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
			Intent intent = new Intent(reviwlevel.this, list.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.listpage) {
			Intent intent = new Intent(reviwlevel.this,
					listselectactivity.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.coursepage) {
			Intent intent = new Intent(reviwlevel.this, MainActivity.class);
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

	private class SpecialAdapter extends ArrayAdapter {

		private int[] colors = new int[] { 0xFF54FF9F, 0x300000FF };
		managedb db = new managedb(getBaseContext());

		public SpecialAdapter(Context context, int resource, List objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);

			if (db.levelexist(Integer.toString(position + 1))) {

				System.out.println("really:  " + (position + 1));

				view.setBackgroundColor(0xFF54FF9F);
			} else {
				view.setBackgroundColor(0x300000FF);
			}

			return view;
		}
	}
}
