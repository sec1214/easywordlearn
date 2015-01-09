package com.rootsproject;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LikeView;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.rootsproject.R;

import review.reviwlevel;
import Database.managedb;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class list extends Activity {

	private mypublicvalue myapp;
	private TextView textView1, textView2; // display course name and list name
	private Button playButton, studyButton, reviewButton;
	/* added by yi wan, 2015-01-03, start */
	private UiLifecycleHelper uiHelper;
	private ImageButton share;
	private LikeView like;
	/* added by yi wan, 2015-01-03, over */
	private BroadcastReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zlist);

		receiver = new HomeKeyEventBroadCastReceiver();
		getApplicationContext().registerReceiver(receiver,
				new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

		textView1 = (TextView) this.findViewById(R.id.textview1); // course name
		textView2 = (TextView) this.findViewById(R.id.textview2); // list name

		myapp = (mypublicvalue) getApplication();

		myapp.startsplashmusic();
		managedb db = new managedb(getBaseContext());
		myapp.setwords(db.getwords()); // according the course name and list
										// name, now we can ensure how can
										// getwords and store them in to public
										// value

		textView1.setText(underlineclear(myapp.get(0)));
		textView2.setText(myapp.get(1));

		playButton = (Button) this.findViewById(R.id.button1);
		studyButton = (Button) this.findViewById(R.id.button2);
		reviewButton = (Button) this.findViewById(R.id.button3);
		/* added by yi wan, 2015-01-03, start */
		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
		// configure Share Button
		share = (ImageButton) findViewById(R.id.share);
		share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickShare();
			}
		});
		like = (LikeView) findViewById(R.id.likeView);
		like.setObjectId("https://www.facebook.com/rootsmobileapp");
		/* added by yi wan, 2015-01-03, over */
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
		});

	}
	/* added by yi wan, 2015-01-03, start */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Log.i("Activity", "Success!");
					}
				});
	}
	/* added by yi wan, 2015-01-03, over */
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
	/* added by yi wan, 2015-01-03, start */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
	/* added by yi wan, 2015-01-03, over */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/* added by yi wan, 2015-01-03, start */
		uiHelper.onResume();
		/* added by yi wan, 2015-01-03, over */

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		/* added by yi wan, 2015-01-03, start */
		uiHelper.onStop();
		/* added by yi wan, 2015-01-03, over */

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		/* added by yi wan, 2015-01-03, start */
		uiHelper.onPause();
		/* added by yi wan, 2015-01-03, over */

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getApplicationContext().unregisterReceiver(receiver);
		/* added by yi wan, 2015-01-03, start */
		uiHelper.onDestroy();
		/* added by yi wan, 2015-01-03, over */
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
	/* added by yi wan, 2015-01-03, start */
	private void onClickShare() {
		String description = "Check out Roots, a mobile game that teaches vocabulary "
				+ "through etymology! It's a great tool for students "
				+ "studying for the SAT and ESL students.";
		if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			// Publish the post using the Share Dialog
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					this).setDescription(description)
					.setLink("http://www.roots.nyc").build();
			uiHelper.trackPendingDialogCall(shareDialog.present());
		}
	}
	/* added by yi wan, 2015-01-03, over */


}
