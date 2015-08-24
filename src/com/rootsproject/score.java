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

import level2.definitionl2;
import level2.wordsl2;
import Database.managedb;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rootsproject.R;

public class score extends AppCompatActivity {

	private mypublicvalue myapp;
	private Dialog alertdDialog;
	private TextView score, review;
	private ListView correct, incorret; // listview to show correct and
										// incorrect wordlist.
	private ArrayAdapter<String> adaptercorrect;
	private ArrayAdapter<String> adapterincorrect;
	private String[][] wrongwords;
	private String[][] words;
	private boolean[] f = new boolean[21];
	private LinearLayout defwordline, idrootline, rootline; // accoring to
															// different level
															// ,we can close
															// relevent score
															// record.
	private TextView textView2, textViewlevel;
	private int wcon; // review control.
	private TextView main;
	private int scorenum, defwordscorenum, rootscorenum;

	private TextView defwordscore, rootscore, idrootscore;

	private CallbackManager callbackManager;
	private ShareDialog shareDialog;
	private ShareButton post;
	private LikeView like;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myapp = (mypublicvalue) getApplication();

		android.support.v7.app.ActionBar ab = getSupportActionBar();
		ab.setTitle(underlineclear(myapp.get(0)));

		setContentView(R.layout.zscore);
		textView2 = (TextView) this.findViewById(R.id.textview2);
		textViewlevel = (TextView) this.findViewById(R.id.leveltext);
		idrootline = (LinearLayout) this.findViewById(R.id.idrootline);
		idrootline.setVisibility(View.GONE);

		defwordscore = (TextView) this.findViewById(R.id.defwordscore);
		rootscore = (TextView) this.findViewById(R.id.rootscore);
		idrootscore = (TextView) this.findViewById(R.id.idrootscore);
		score = (TextView) this.findViewById(R.id.score);
		review = (TextView) this.findViewById(R.id.reviewbutton);
		main = (TextView) this.findViewById(R.id.mainbutton);
		main.setText("Skip to Next Level");

		myapp.resetConstantCorrectCount();
		main.setOnClickListener(new View.OnClickListener() { // main button to
																// next level

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * Intent intent = new Intent(score.this, list.class);
				 * myapp.empty(); startActivity(intent); finish();
				 */
				/* added by xiaoqian yu, 2014-12-28, start */
				/* combo count returns to zero */
				myapp.empty();
				managedb db = new managedb(getBaseContext());
				myapp.setComboBeginningState();
				myapp.setwords(db.getwords());
				myapp.set(3, Integer.toString(1 + 1));
				/* jump to next level, level2 */
				double h = Math.random();
				if (h < 0.5) {
					Intent intent = new Intent(score.this, wordsl2.class);
					startActivity(intent);
					// finish();
				} else {
					Intent intent = new Intent(score.this, definitionl2.class);
					startActivity(intent);
				}
				finish();
				/* added by xiaoqian yu, 2014-12-28, over */
			}
		});

		correct = (ListView) this.findViewById(R.id.Listlistviewcorrect);
		incorret = (ListView) this.findViewById(R.id.Listlistviewincorrect);

		List<String> listcorrect = new ArrayList<String>();
		List<String> listincorrect = new ArrayList<String>();

		myapp = (mypublicvalue) getApplication();
		myapp.stoplevelmusic();
		textView2.setText(myapp.get(1)); // list
		textViewlevel.setText(" Level: " + myapp.get(3));// level
		wcon = myapp.getreviewwrongcontrol(); // review control

		/* added by xiaoqian yu, 2014-12-21, start */
		EasyTracker easyTracker = EasyTracker.getInstance(score.this);
		String eventLabel = myapp.get(0) + "_" + myapp.get(1) + "_level"
				+ myapp.get(3);
		long eventValue = (long) ((myapp.getscore(1) / myapp.getscore(0)) * 100);
		// MapBuilder.createEvent().build() returns a Map of event fields and
		// values
		// that are set and sent with the hit.
		easyTracker.send(MapBuilder.createEvent("score_output", // Event
				// category
				// (required)
				"final_percentage_output", // Event action (required)
				eventLabel, // Event label
				eventValue) // Event value
				.build());

		callbackManager = CallbackManager.Factory.create();

		scorenum = (int) ((myapp.getscore(1) / myapp.getscore(0)) * 100);

		defwordscorenum = (int) ((myapp.getdefwordscore(1) / myapp
				.getdefwordscore(0)) * 100);

		rootscorenum = (int) ((myapp.getrootscore(1) / myapp.getrootscore(0)) * 100);

		ShareLinkContent content = new ShareLinkContent.Builder()
				.setContentUrl(Uri.parse("http://roots.nyc/"))
				.setContentTitle("Roots: Play with words")
				.setImageUrl(Uri.parse("http://roots.nyc/wp-content/uploads/2014/10/RootsPlayWords_TransWoutline1.png"))
				.setContentDescription("I scored " + scorenum + "% on Roots: "
						+ myapp.get(0).replace("_", " ") + ", " + myapp.get(1)
						+ ", Level " + myapp.get(3) + ". \n"
						+ "Word Definitions: " + defwordscorenum + "%\n"
						+ "Root Definitions: " + rootscorenum + "%\n"
						+ "Get it now on the Google Play Store! \n"
						+ "https://play.google.com/store/apps/details?id=com.rootsproject")
				.build();

		post = (ShareButton) findViewById(R.id.post);
		post.setShareContent(content);

		like = (LikeView) findViewById(R.id.likeView);
		like.setObjectIdAndType("https://www.facebook.com/rootsmobileapp", LikeView.ObjectType.UNKNOWN);

		if (wcon == 0) {

			words = myapp.getwordslv1();
			wrongwords = myapp.getCwrongwords();
			bool(); // this method is used to define when words is "". the f is
					// false.
		}

		if (wcon == 1) {
			words = myapp.getCwrongwords();
			wrongwords = myapp.getLwrongwords();
			bool();

		}

		dbmg(); // store every score into databse
		//
		// changecolorscore((int) ((myapp.getscore(1) / myapp.getscore(0)) *
		// 100));
		score.setText((int) ((myapp.getscore(1) / myapp.getscore(0)) * 100)
				+ "%");

		defwordscore.setText((int) ((myapp.getdefwordscore(1) / myapp
				.getdefwordscore(0)) * 100) + "%");
		rootscore
				.setText((int) ((myapp.getrootscore(1) / myapp.getrootscore(0)) * 100)
						+ "%");

		for (int i = 0; i < wrongwords.length; i++) {

			if (!wrongwords[i][0].equals("")) {
				listincorrect.add(wrongwords[i][0]);
			}

		}

		for (int i = 0; i < words.length; i++) { // this is used to select right
													// words.

			for (int j = 0; j < wrongwords.length; j++) {

				if (words[i][0].equals(wrongwords[j][0])) {

					f[i] = false;

					break;
				}

			}

		}

		for (int i = 0; i < words.length; i++) {
			if (f[i]) {
				listcorrect.add(words[i][0]);
			}
		}

		adapterincorrect = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, listincorrect);

		incorret.setAdapter(adapterincorrect);

		adaptercorrect = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, listcorrect);

		correct.setAdapter(adaptercorrect);

		if (wcon == 1) {

			correct.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(score.this, scoreword.class);

					String value = correct.getItemAtPosition(position) // postion
																		// 1��λ
																		// ��0��ʼ��
							.toString();

					for (int i = 0; i < words.length; i++) {
						if (value.equals(words[i][0])) {

							intent.putExtra("name", words[i][0]); // deliever
																	// string by
																	// intent
																	// method.

							String string = null;

							if (!words[i][0].equals("")) {

								string = "Definition: " + words[i][1] + ".\n"
										+ "\n";

								if (!words[i][2].equals("")) {

									string = string + words[i][2] + ": "
											+ words[i][3] + ".\n" + "\n";
								}
								if (!words[i][4].equals("")) {

									string = string + words[i][4] + ": "
											+ words[i][5] + ".\n" + "\n";
								}
								if (!words[i][6].equals("")) {

									string = string + words[i][6] + ": "
											+ words[i][7] + ".\n" + "\n";
								}
								if (!words[i][8].equals("")) {

									string = string + words[i][8] + ": "
											+ words[i][9] + ".\n" + "\n";
								}

								intent.putExtra("string", string);

							}
							break;
						}
					}
					startActivity(intent);

				}
			});

			incorret.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(score.this, scoreword.class);

					String value = incorret.getItemAtPosition(position) // postion
																		// 1��λ
																		// ��0��ʼ��
							.toString();

					for (int i = 0; i < wrongwords.length; i++) {
						if (value.equals(wrongwords[i][0])) {

							intent.putExtra("name", wrongwords[i][0]);

							String string = null;

							if (!wrongwords[i][0].equals("")) {

								string = "Definition: " + wrongwords[i][1]
										+ ".\n" + "\n";

								if (!wrongwords[i][2].equals("")) {

									string = string + wrongwords[i][2] + ": "
											+ wrongwords[i][3] + ".\n" + "\n";
								}
								if (!wrongwords[i][4].equals("")) {

									string = string + wrongwords[i][4] + ": "
											+ wrongwords[i][5] + ".\n" + "\n";
								}
								if (!wrongwords[i][6].equals("")) {

									string = string + wrongwords[i][6] + ": "
											+ wrongwords[i][7] + ".\n" + "\n";
								}
								if (!wrongwords[i][8].equals("")) {

									string = string + wrongwords[i][8] + ": "
											+ wrongwords[i][9] + ".\n" + "\n";
								}

								intent.putExtra("string", string);

							}
							break;
						}
					}
					startActivity(intent);

				}
			});

			review.setText("Next Level"); // go to next level
			review.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					myapp.empty();
					managedb db = new managedb(getBaseContext());
					myapp.setwords(db.getwords());

					myapp.set(3, Integer.toString(2));

					double h = Math.random();

					if (h < 0.5) {
						Intent intent = new Intent(score.this, wordsl2.class);

						startActivity(intent);

						// finish();
					} else {
						Intent intent = new Intent(score.this,
								definitionl2.class);
						startActivity(intent);
					}
					finish();
				}
			});

		}

		if (wcon == 0) {

			correct.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(score.this, scoreword.class);

					String value = correct.getItemAtPosition(position) // postion
																		// 1��λ
																		// ��0��ʼ��
							.toString();

					for (int i = 0; i < words.length; i++) {
						if (value.equals(words[i][0])) {

							intent.putExtra("name", words[i][0]);

							String string = null;

							if (!words[i][0].equals("")) {

								string = "Definition: " + words[i][1] + ".\n"
										+ "\n";

								if (!words[i][2].equals("")) {

									string = string + words[i][2] + ": "
											+ words[i][3] + ".\n" + "\n";
								}
								if (!words[i][4].equals("")) {

									string = string + words[i][4] + ": "
											+ words[i][5] + ".\n" + "\n";
								}
								if (!words[i][6].equals("")) {

									string = string + words[i][6] + ": "
											+ words[i][7] + ".\n" + "\n";
								}
								if (!words[i][8].equals("")) {

									string = string + words[i][8] + ": "
											+ words[i][9] + ".\n" + "\n";
								}

								intent.putExtra("string", string);

							}
							break;
						}
					}
					startActivity(intent);

				}
			});

			incorret.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(score.this, scoreword.class);

					String value = incorret.getItemAtPosition(position) // postion
																		// 1��λ
																		// ��0��ʼ��
							.toString();

					for (int i = 0; i < wrongwords.length; i++) {
						if (value.equals(wrongwords[i][0])) {

							intent.putExtra("name", wrongwords[i][0]);

							String string = null;

							if (!wrongwords[i][0].equals("")) {

								string = "Definition: " + wrongwords[i][1]
										+ ".\n" + "\n";

								if (!wrongwords[i][2].equals("")) {

									string = string + wrongwords[i][2] + ": "
											+ wrongwords[i][3] + ".\n" + "\n";
								}
								if (!wrongwords[i][4].equals("")) {

									string = string + wrongwords[i][4] + ": "
											+ wrongwords[i][5] + ".\n" + "\n";
								}
								if (!wrongwords[i][6].equals("")) {

									string = string + wrongwords[i][6] + ": "
											+ wrongwords[i][7] + ".\n" + "\n";
								}
								if (!wrongwords[i][8].equals("")) {

									string = string + wrongwords[i][8] + ": "
											+ wrongwords[i][9] + ".\n" + "\n";
								}

								intent.putExtra("string", string);

							}
							break;
						}
					}
					startActivity(intent);

				}
			});

			if (scorenum == 100) { // it means if socre is 100. there is not
									// review condition.

				review.setText("Next Level");
				review.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						myapp.empty();
						managedb db = new managedb(getBaseContext());
						myapp.setwords(db.getwords());

						myapp.set(3, Integer.toString(2));

						double h = Math.random();

						if (h < 0.5) {
							Intent intent = new Intent(score.this,
									wordsl2.class);

							startActivity(intent);

							// finish();
						} else {
							Intent intent = new Intent(score.this,
									definitionl2.class);
							startActivity(intent);
						}
						finish();
					}
				});

			}

			if (scorenum < 100) {

				myapp.empty1();

				review.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Intent intent = new Intent(score.this, root.class);
						myapp.setreviewwrongcontrol(1); // �趨����wrongѭ��

						startActivity(intent);
						finish();
					}
				});

			}

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		callbackManager.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void bool() {
		for (int i = 0; i < f.length; i++) {
			f[i] = true;
		}
		for (int i = 0; i < words.length; i++) {
			if (words[i][0].equals("")) {
				f[i] = false;
			}
		}

	}

	public void dbmg() {

		managedb db = new managedb(getBaseContext());

		if (db.coursexist(myapp.get(0))) { // judge weather exist the table in
											// database .�ж����ݿ���ڷￄ1�7
			System.out.println("���ݿ���");
		} else {

			System.out.println("���ݿ���");
			db.coursereivewcreate(myapp.get(0)); // if not exist, create it.
													// ��������
			System.out.println("�����ɹ�");
		}

		if (wcon == 0) { // delete the order record.
			db.deletewrongworddb(); // ɾ��ԭ���Ĵ�ￄ1�7
			System.out.println("ɾ���ɹ�");
		}
		db.insertscore(scorenum, defwordscorenum, rootscorenum, 0, 0); // store
																		// every
																		// score.
		db.insertdb(wrongwords, "0"); // store worngwords in databse
										// �ڶ�������ΪĬ�ϲ������ù�
										// д�����ݿ�
		System.out.println("����ɹￄ1�7");

		db.cleantdata(); // ��ɨ���ݿ� clean wrongwords when it is ""��
		System.out.println("��ɨ���ݿ�");

	}

	public String underlineclear(String key) {
		String flag = key.replace("_", " ");
		return flag;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			myapp.empty();

		Intent intent = new Intent(score.this,
				play.class);
		startActivity(intent);
		finish();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);

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
		if (id == R.id.level) {

			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(score.this, play.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.PlayStudyReview) {
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(score.this, list.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.listpage) {
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(score.this, listselectactivity.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.coursepage) {
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(score.this, MainActivity.class);
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
				myapp.stoplevelmusic();

			} else {
				item.setChecked(true);
				myapp.setmusic(1, 1);
				myapp.startlevelmusic();

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

	public void changecolorscore(int key) {
		if (key >= 86) {
			score.setTextColor(Color.GREEN);

		}
		if (key <= 64) {
			score.setTextColor(Color.RED);

		}
		if (key > 64 && key < 86)

		{
			score.setTextColor(Color.YELLOW);

		}
	}
}