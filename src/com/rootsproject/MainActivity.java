package com.rootsproject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.sbstrm.appirater.Appirater;

import Database.managedb;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.AbsListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rootsproject.R;
import com.appsee.Appsee;

public class MainActivity extends AppCompatActivity {

	private MenuItem musicsound; // control the splashmusic on and off
	private MenuItem buttonsound; // control the button sound.

	private ListView listView;
	private ArrayAdapter<String> adapter; // course list.
	private mypublicvalue myapp; // public value and function, store in this
									// java(mypublicvalue.java).
	private Dialog alertdDialog; // press the back key, show the dialog.
	private LinearLayout splash; // splash screen

	private boolean exit = true;

	private ShareButton share;
	private LikeView like;

	private static final int STOPSPLASH = 0;
	// time in milliseconds
	private static final long SPLASHTIME = 1000;

	private Handler splashHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) { // this handlemessage is
													// responsible for
													// splashscreen display
													// delay
			switch (msg.what) {
			case STOPSPLASH:
				SystemClock.sleep(1000);
				splash.setVisibility(View.GONE); // after display the splash,
													// set the Linearlayout
													// hide.
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Appsee.start("c2c77102cf2a485ea9b4e81e2a2b9101");
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		super.onCreate(savedInstanceState);
		myapp = (mypublicvalue) getApplication();


		android.support.v7.app.ActionBar ab = getSupportActionBar();
		ab.setTitle(R.string.course);

		FacebookSdk.sdkInitialize(this.getApplicationContext());

		// setTitle("");

		setContentView(R.layout.zmain);
		
		//print the key hash, output to logCat
		printHashKey();

		splash = (LinearLayout) findViewById(R.id.splashscreen);

		myapp.startsplashmusic();

		if (!myapp.getsplashscreen()) { // this getsplashscreen ensure that
										// splashscreen only display once.
			Appirater.appLaunched(this);
			Message msg = new Message();
			msg.what = STOPSPLASH;
			splashHandler.sendMessageDelayed(msg, SPLASHTIME);

			myapp.setsplashscreen(); // match the getsplashscreen.
		} else {
			splash.setVisibility(View.GONE);
		}

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
		like.setLikeViewStyle(LikeView.Style.BOX_COUNT);

		List<String> list = new managedb(this).listtablename(null);

		List<String> listtablename = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			String p = list.get(i);
			p = p.replace("_", " "); // becasue databse table name can't have
										// space, but we need to show space
										// between words, so !!
			listtablename.add(p);

		}

		System.out.println(list); // �����������ݿ��еı�����

		// System.out.println("test");

		listView = (ListView) this.findViewById(R.id.listview);

		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
				listtablename);

		// listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int postion, long arg3) {
				// TODO Auto-generated method stub

				/* sp.play(music, 1, 1, 0, 0, 1); */

				String value = listView.getItemAtPosition(postion) // postion
																	// ��һλ��0��ʼ��
						.toString();

				value = value.replace(" ", "_");

				myapp.set(0, value); // store table name to public value. 0
										// �����ݵ��Ǳ���

				exit = false;

				myapp.playmusic(1); // button sound

				Toast.makeText(MainActivity.this, myapp.get(0), Toast.LENGTH_SHORT).show(); // Toast
																			// show
																			// choiced
																			// table
																			// name
																			// ��ʾ����Ŀγ�Ҳ���Ǳ��ￄ1�7

				/* added by xiaoqian yu, 2014-12-21, start */
				EasyTracker easyTracker = EasyTracker
						.getInstance(MainActivity.this);

				// MapBuilder.createEvent().build() returns a Map of event
				// fields and values
				// that are set and sent with the hit.
				easyTracker.send(MapBuilder.createEvent("Category_Selection", // Event
																				// category
																				// (required)
						"button_press_to_choose_category", // Event action
															// (required)
						myapp.get(0), // Event label
						null) // Event value
						.build());
				/* added by xiaoqian yu, 2014-12-21, end */

				Intent intent = new Intent(MainActivity.this,
						listselectactivity.class);

				// myapp.Vibrate(); //��

				startActivity(intent);

				finish();

			}
		});

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
		exit = true;
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
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("Stop");

		if (exit) {
			//System.exit(0); Removed for compatibility with Facebook button.
		}

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

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) { // Back key event
			alertdDialog = new AlertDialog.Builder(this)
					.setTitle("EXIT")
					.setMessage("Do you want to exit?")
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
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									alertdDialog.cancel();
								}
							}).create();

			alertdDialog.show();

		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) { // Menu design.

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		musicsound = menu.add(101, 1, 1, "Music");
		buttonsound = menu.add(101, 2, 2, "Sound Effects");
		MenuItem deletereview = menu.add(101, 3, 3, "Delete Review");
		MenuItem setdefault = menu.add(101, 4, 4, "Set Default");

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
	public boolean onPrepareOptionsMenu(Menu menu) { // this method will be call
														// before press menu.
														// �˵���ʾǰ ����
		// TODO Auto-generated method stub
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

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.Exit) { // press exit button

			System.exit(0);

			return true;
		}

		if (id == 1) { // press musicsound

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

		if (id == 2) { // press buttonsound

			if (item.isChecked()) {
				item.setChecked(false);
				myapp.setmusic(0, 0);
			} else {
				item.setChecked(true);
				myapp.setmusic(0, 1);
			}
			return true;
		}

		if (id == 3) { // press review

			alertdDialog = new AlertDialog.Builder(this)
					.setTitle("Delete Review Record")
					.setMessage("Do you want to delete review record?")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("Confirm",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									myapp.deletereviewrecord();

								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									alertdDialog.cancel();
									// timer.start();
								}
							}).create();

			alertdDialog.show();

			return true;
		}

		if (id == 4) { // press set default

			alertdDialog = new AlertDialog.Builder(this)
					.setTitle("Set Default")
					.setMessage("Do you want to set default?")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("Confirm",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									myapp.setdefault();
									myapp.stopsplashmusic();
									myapp.startsplashmusic();

								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									alertdDialog.cancel();
									// timer.start();
								}
							}).create();

			alertdDialog.show();
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
			Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void printHashKey() {

	       try {
	            PackageInfo info = getPackageManager().getPackageInfo("com.rootsproject",
	                    PackageManager.GET_SIGNATURES);
	            for (Signature signature : info.signatures) {
	                MessageDigest md = MessageDigest.getInstance("SHA");
	                md.update(signature.toByteArray());
	                Log.d("TEMPTAGHASH KEY:",
	                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	        } catch (NameNotFoundException e) {

	        } catch (NoSuchAlgorithmException e) {

	        }

	    }

}
