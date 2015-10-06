package com.rootsproject;

import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.rootsproject.R;

import review.reviwlevel;
import Database.managedb;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class list extends AppCompatActivity {

	private mypublicvalue myapp;
	private Button playButton, studyButton, reviewButton;

	private ShareButton share;
	private LikeView like;

	private BroadcastReceiver receiver;
	private Dialog alertdDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myapp = (mypublicvalue) getApplication();

		android.support.v7.app.ActionBar ab = getSupportActionBar();
		ab.setTitle(underlineclear(myapp.get(0)));
		ab.setSubtitle(myapp.get(1));

		setContentView(R.layout.zlist);

		receiver = new HomeKeyEventBroadCastReceiver();
		getApplicationContext().registerReceiver(receiver,
				new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

		myapp.startsplashmusic();
		managedb db = new managedb(getBaseContext());
		myapp.setwords(db.getwords()); // according the course name and list
										// name, now we can ensure how can
										// getwords and store them in to public
										// value

		playButton = (Button) this.findViewById(R.id.button1);
		studyButton = (Button) this.findViewById(R.id.button2);
		reviewButton = (Button) this.findViewById(R.id.button3);

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

		studyButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/* added by xiaoqian yu, 2014-12-21, start */
				EasyTracker easyTracker = EasyTracker.getInstance(list.this);
				String category = "PlayStudyReviewSelection";
				String eventLabel = "study_button";
				// MapBuilder.createEvent().build() returns a Map of event
				// fields and values
				// that are set and sent with the hit.
				easyTracker.send(MapBuilder.createEvent(category, // Event
																	// category
																	// (required)
						"button_press", // Event action (required)
						eventLabel, // Event label
						null) // Event value
						.build());
				/* added by xiaoqian yu, 2014-12-21, end */
				studyButton.setBackgroundResource(R.drawable.green); // when
																		// press
																		// the
																		// button,
																		// its
																		// background
																		// will
																		// change
																		// color.
				myapp.playmusic(1); // button sound
				Intent intent = new Intent(list.this, studywordcolor.class);

				startActivity(intent);
				finish();
			}
		});

		playButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				EasyTracker easyTracker = EasyTracker.getInstance(list.this);
				String category = "PlayStudyReviewSelection";
				String eventLabel = "play_button";
				// MapBuilder.createEvent().build() returns a Map of event
				// fields and values
				// that are set and sent with the hit.
				easyTracker.send(MapBuilder.createEvent(category, // Event
																	// category
																	// (required)
						"button_press", // Event action (required)
						eventLabel, // Event label
						null) // Event value
						.build());

				playButton.setBackgroundResource(R.drawable.green);
				myapp.playmusic(1);
				Intent intent = new Intent(list.this, play.class);
				startActivity(intent);
				finish();
			}
		});

		reviewButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//if no play level completed, pop out a dialogue
				if(true == isNoLevelPlayCompleted()) {
					alertdDialog = new AlertDialog.Builder(list.this)
					.setTitle("Instruction")
					.setMessage(getString(R.string.no_level_play_complete_alert))
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									alertdDialog.cancel();
								}
							}).create();
					alertdDialog.show();
				}
				else {
					/* added by xiaoqian yu, 2014-12-21, start */
					EasyTracker easyTracker = EasyTracker.getInstance(list.this);
					String category = "PlayStudyReviewSelection";
					String eventLabel = "review_button";
					// MapBuilder.createEvent().build() returns a Map of event
					// fields and values
					// that are set and sent with the hit.
					easyTracker.send(MapBuilder.createEvent(category, // Event
																		// category
																		// (required)
							"button_press", // Event action (required)
							eventLabel, // Event label
							null) // Event value
							.build());
					/* added by xiaoqian yu, 2014-12-21, end */
					reviewButton.setBackgroundResource(R.drawable.green);
					myapp.playmusic(1);
					Intent intent = new Intent(list.this, reviwlevel.class);
	
					startActivity(intent);
					finish();
				}
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

			Intent intent = new Intent(list.this, listselectactivity.class);
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
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
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
		getMenuInflater().inflate(R.menu.list, menu);

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

		if (id == R.id.listselect) {
			Intent intent = new Intent(list.this, listselectactivity.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.mainpage) {
			Intent intent = new Intent(list.this, MainActivity.class);
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
			Toast.makeText(list.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}

	private boolean isNoLevelPlayCompleted() {
    	managedb db = new managedb(getBaseContext());
    	for(int i = 0; i <= 8-1; i++) {
    		if(db.levelexist(Integer.toString(i + 1))) {
    			return false;
    		}
    	}
    	return true;
    }
}
