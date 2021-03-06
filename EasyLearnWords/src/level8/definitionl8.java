package level8;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.easylearnwords.MainActivity;
import com.easylearnwords.R;
import com.easylearnwords.list;
import com.easylearnwords.listselectactivity;
import com.easylearnwords.mypublicvalue;
import com.easylearnwords.play;
import com.google.analytics.tracking.android.EasyTracker;

public class definitionl8 extends Activity {

	private Dialog alertdDialog;
	private TextView textView1, textView2, wordtTextView, textViewdef1,
			textViewdef2, textViewdef3;
	private TextView textViewlevel, textViewword, textViewwr, textViewscore;
	private mypublicvalue myapp;
	private String[][] words;
	private int wordnum;

	public int wcon; // 复习控制阀
	private String[] roots;
	private int numroot;

	private boolean p1 = false; // 操控help button的控制阀

	private Timer timergreen;

	private boolean timergreencontrol = false;

	private Intent intent;

	public long sleeptime = 2000;

	private double clicknum, rightnum;
	private ImageButton wenhaoButton;

	private Matrix matrix = new Matrix();

	private CountDownTimer helpshape;

	private BroadcastReceiver receiver;

	CountDownTimer timer = new CountDownTimer(10000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub

			int k = (int) (millisUntilFinished / 1000);

			textViewwr.setText(k + "'s");

			if (k > 5) {
				myapp.playmusic(4);
			}

			if (k == 5) {
				textViewwr.setBackgroundColor(Color.RED);
				textViewwr.setTextColor(Color.WHITE);
			}
			if (k <= 5) {
				myapp.playmusic(2);
			}

		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			textViewwr.setText("Time over");
			if (Integer.parseInt(myapp.get(6)) == 1) {

				myapp.playmusic(3); // 失败音乐
			}

			Intent intent = new Intent(definitionl8.this, definitionl8.class);
			startActivity(intent);
			defrepeat(1);
			myapp.setscore(0, clicknum + 1);

			if (wcon == 0) {
				myapp.addwrongword(words[wordnum - 1]);
			}
			if (wcon == 1) {
				myapp.addwrongwords1(words[wordnum - 1]);
			}

			timer.cancel();
			stopshape();
			finish();
		}
	};
	CountDownTimer timerhelp = new CountDownTimer(5000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFinish() {

			p1 = true;

			helpshape = new CountDownTimer(2000, 90) {

				boolean key = true;

				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub

					Bitmap bitmap = ((BitmapDrawable) (getResources()
							.getDrawable(R.drawable.wenhaored))).getBitmap();

					if (key) {

						matrix.setRotate(10f);
						key = false;
					} else {

						matrix.setRotate(-10f);
						key = true;
					}

					bitmap = Bitmap
							.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
									bitmap.getHeight(), matrix, true);
					wenhaoButton.setImageBitmap(bitmap);

				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub

					helpshape.cancel();
					wenhaoButton.setImageResource(R.drawable.wenhao);
					timerhelp.start();
					// helpshape.start();

				}
			};

			helpshape.start();

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zdefinition);
		sleeptime= Long.parseLong(this.getString(R.string.sleeptime));
		receiver = new HomeKeyEventBroadCastReceiver();
		getApplicationContext().registerReceiver(receiver,
				new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
		wenhaoButton = (ImageButton) this.findViewById(R.id.wenhaobutton);
		textView1 = (TextView) this.findViewById(R.id.textview1);
		textView2 = (TextView) this.findViewById(R.id.textview2);
		wordtTextView = (TextView) this.findViewById(R.id.wordtestview);
		textViewdef1 = (TextView) this.findViewById(R.id.textviewdef1);
		textViewdef2 = (TextView) this.findViewById(R.id.textviewdef2);
		textViewdef3 = (TextView) this.findViewById(R.id.textviewdef3);
		textViewlevel = (TextView) this.findViewById(R.id.leveltext);
		textViewword = (TextView) this.findViewById(R.id.wordtext);
		textViewwr = (TextView) this.findViewById(R.id.wrtext);
		textViewscore = (TextView) this.findViewById(R.id.scoretext);

		myapp = (mypublicvalue) getApplication();
		myapp.startlevelmusic();
		textView1.setText(underlineclear(myapp.get(0)));
		textView2.setText(myapp.get(1));

		clicknum = myapp.getscore(0);
		rightnum = myapp.getscore(1);

		textViewlevel.setText(" Level: " + myapp.get(3)); // 设定显示level的控件

		changecolorscore((int) ((myapp.getscore(1) / myapp.getscore(0)) * 100));textViewword.setText("Word: " + wordnum + "  "); // 设定显示几号word的控件

		/* words = myapp.getwords(); */

		wcon = myapp.getreviewwrongcontrol(); // 复习控制阀值

		if (wcon == 0) {
			words = myapp.getwords();
		}
		if (wcon == 1) {
			words = myapp.getCwrongwords();
			textViewwr.setText("Wrong Reivew");
			textViewwr.setBackgroundColor(Color.RED);
		}

		changecolorscore((int) ((myapp.getscore(1) / myapp.getscore(0)) * 100));
		textViewscore.setText(
			  (int) ((myapp.getscore(1) / myapp.getscore(0)) * 100) + "%");

		wordnum = Integer.parseInt(myapp.get(4));

		
		textViewword.setText("Word: " + wordnum + " / " + wordnum()); // 设定显示几号word的控件
		String word = words[wordnum - 1][0];
		wordtTextView.setText(word);

		System.out.println("definition TT 循环控制阀 变量 con "
				+ myapp.getrepeatcontrol());
		System.out.println("definition wrong 循环控制阀 变量 wcon "
				+ myapp.getreviewwrongcontrol());

		System.out.println("ran前一步");

		this.ran();
		if (myapp.gethelpcontrol(1) == 0) {
			timerhelp.start();
		}

		if (Integer.parseInt(myapp.get(6)) < 2) {

			textViewwr.setText("Time: " + 10 + "'s");
			timer.start();

			textViewdef1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) textViewdef1.getText();

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了

							textViewdef1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									intent = new Intent(definitionl8.this,
											definitionl8.class);

								} else {
									intent = new Intent(definitionl8.this,
											wordsl8.class);

								}

								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								System.out.println("循环结束");
								intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanwrongwords();
								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongword(words[wordnum - 1]);
							defrepeat(1); // 这是做错了加1
							Intent intent = new Intent(definitionl8.this,
									definitionl8.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							timer.cancel();							
							finish();
							}
						}
					}

					if (wcon == 1) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了

							textViewdef1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									intent = new Intent(definitionl8.this,
											definitionl8.class);

								} else {
									intent = new Intent(definitionl8.this,
											wordsl8.class);

								}

								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								System.out.println("循环结束");
								intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanCwrongwords();

								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);
							defrepeat(1); // 这是做错了加1
							Intent intent = new Intent(definitionl8.this,
									definitionl8.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							timer.cancel();							
							finish();
							}
						}
					}

				}
			});

			textViewdef2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) textViewdef2.getText();

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了

							textViewdef2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									intent = new Intent(definitionl8.this,
											definitionl8.class);

								} else {
									intent = new Intent(definitionl8.this,
											wordsl8.class);

								}

								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								System.out.println("循环结束");
								intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanwrongwords();
								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongword(words[wordnum - 1]);
							defrepeat(1); // 这是做错了加1
							Intent intent = new Intent(definitionl8.this,
									definitionl8.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							timer.cancel();							
							finish();
							}
						}
					}

					if (wcon == 1) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了

							textViewdef2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									intent = new Intent(definitionl8.this,
											definitionl8.class);

								} else {
									intent = new Intent(definitionl8.this,
											wordsl8.class);

								}

								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								System.out.println("循环结束");
								intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanCwrongwords();

								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);
							defrepeat(1); // 这是做错了加1
							Intent intent = new Intent(definitionl8.this,
									definitionl8.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							timer.cancel();							
							finish();
							}
						}
					}

				}
			});
			textViewdef3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) textViewdef3.getText();

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了

							textViewdef3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									intent = new Intent(definitionl8.this,
											definitionl8.class);

								} else {
									intent = new Intent(definitionl8.this,
											wordsl8.class);

								}

								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								System.out.println("循环结束");
								intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanwrongwords();
								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongword(words[wordnum - 1]);
							defrepeat(1); // 这是做错了加1
							Intent intent = new Intent(definitionl8.this,
									definitionl8.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							timer.cancel();							
							finish();
							}
						}
					}

					if (wcon == 1) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了

							textViewdef3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									intent = new Intent(definitionl8.this,
											definitionl8.class);

								} else {
									intent = new Intent(definitionl8.this,
											wordsl8.class);

								}

								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								System.out.println("循环结束");
								intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanCwrongwords();

								myapp.setscore(0, clicknum + 1);
								timer.cancel();
								stopshape();
								timergreen = new Timer();
								timergreen.schedule(new TimerTask() {

									@Override
									public void run() {
										// TODO Auto-generated method stub

										startActivity(intent);
										finish();
									}
								}, sleeptime);

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);
							defrepeat(1); // 这是做错了加1
							Intent intent = new Intent(definitionl8.this,
									definitionl8.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							timer.cancel();							
							finish();
							}
						}
					}

				}
			});

		}

		if (Integer.parseInt(myapp.get(6)) == 2) {
			myapp.greentoast();
			textViewdef1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) textViewdef1.getText();

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了
							defrepeat(0);
							textViewdef1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(
											definitionl8.this,
											definitionl8.class);

									startActivity(intent);

									// finish();
								} else {
									Intent intent = new Intent(
											definitionl8.this, wordsl8.class);
									startActivity(intent);
								}

								finish();

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								Intent intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanwrongwords();
								startActivity(intent);

								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}
					}

					if (wcon == 1) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了
							defrepeat(0);
							textViewdef1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(
											definitionl8.this,
											definitionl8.class);

									startActivity(intent);

									// finish();
								} else {
									Intent intent = new Intent(
											definitionl8.this, wordsl8.class);
									startActivity(intent);
								}

								finish();

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								Intent intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}
					}

				}
			});

			textViewdef2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) textViewdef2.getText();

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了
							defrepeat(0);
							textViewdef2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(
											definitionl8.this,
											definitionl8.class);

									startActivity(intent);

									// finish();
								} else {
									Intent intent = new Intent(
											definitionl8.this, wordsl8.class);
									startActivity(intent);
								}

								finish();

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								Intent intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanwrongwords();
								startActivity(intent);

								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}
					}

					if (wcon == 1) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了
							defrepeat(0);
							textViewdef2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(
											definitionl8.this,
											definitionl8.class);

									startActivity(intent);

									// finish();
								} else {
									Intent intent = new Intent(
											definitionl8.this, wordsl8.class);
									startActivity(intent);
								}

								finish();

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								Intent intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}
					}

				}
			});
			textViewdef3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) textViewdef3.getText();

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了
							defrepeat(0);
							textViewdef3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(
											definitionl8.this,
											definitionl8.class);

									startActivity(intent);

									// finish();
								} else {
									Intent intent = new Intent(
											definitionl8.this, wordsl8.class);
									startActivity(intent);
								}

								finish();

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								Intent intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanwrongwords();
								startActivity(intent);

								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}
					}

					if (wcon == 1) {

						if (key.equals(words[wordnum - 1][1])) { // 做对了
							defrepeat(0);
							textViewdef3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(
											definitionl8.this,
											definitionl8.class);

									startActivity(intent);

									// finish();
								} else {
									Intent intent = new Intent(
											definitionl8.this, wordsl8.class);
									startActivity(intent);
								}

								finish();

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								Intent intent = new Intent(definitionl8.this,
										scorel8.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][1])) { // 循环下做错了

							textViewdef3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}
					}

				}
			});

		}
		wenhaoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timer.cancel();
				stopshape();

				if (myapp.gethelpcontrol(1) == 0) {
					myapp.sethelpcontrol(1, 1);
				}

				alertdDialog = new AlertDialog.Builder(definitionl8.this)
						.setTitle("Instruction")
						.setMessage(getString(R.string.defwordhelp))
						.setIcon(R.drawable.ic_launcher)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

										alertdDialog.cancel();
										textViewwr
												.setBackgroundColor(Color.TRANSPARENT);
										timer.start();

									}
								}).create();

				alertdDialog.show();

			}
		});

	}

	private void defrepeat(int key) {

		if (key == 0) {

			myapp.set(6, Integer.toString(0));
			timergreencontrol = true;
		}

		if (key == 1) {
			myapp.set(6, Integer.toString((Integer.parseInt(myapp.get(6))) + 1));

		}

	}

	private void ran() {
		numroot = 0; // 防止 最后出现不够20 出现的情况

		this.getroots();

		/* if (wcon == 0) { */
		for (int i = 0; i < 20; i++) {

			if (!roots[i].equals("")) { // 死循环问题
				numroot = i + 1;
			}
		}

		double h = Math.random() * 3; // 随机为0到1之间， 这里有4个按钮，所以选4

		// System.out.println((int) h);
		int k = (int) h;

		if (k == 0) {
			method(1);

		}
		if (k == 1) {
			method(2);

		}
		if (k == 2) {
			method(3);

		}
		if (k == 3) {
			method(4);
		}
	}

	private void method(int key) {

		double h;
		int a1, a2, a3;

		a1 = 0;
		a2 = 0;
		a3 = 0;

		for (int i = 0; i < numroot; i++) {

			if (words[wordnum - 1][1].equals(roots[i])) {
				a1 = i;
				break;
			}
		}
		System.out.println("word死循环" + numroot);

		h = Math.random() * numroot;
		a2 = (int) h;
		while (a1 == a2) {
			h = Math.random() * numroot;
			a2 = (int) h;
		}

		h = Math.random() * numroot;
		a3 = (int) h;
		while (a1 == a3 || a2 == a3) {
			h = Math.random() * numroot;
			a3 = (int) h;
		}

		if (key == 1) {
			textViewdef1.setText(words[wordnum - 1][1]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				textViewdef1.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				// defrepeat(0);

			}
			textViewdef2.setText(roots[a2]);
			textViewdef3.setText(roots[a3]);
			/* wordTextView4.setText(roots[a4]); */
		}

		if (key == 2) {
			textViewdef2.setText(words[wordnum - 1][1]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				textViewdef2.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				// defrepeat(0);
			}

			textViewdef1.setText(roots[a3]);
			textViewdef3.setText(roots[a2]);
			/* wordTextView4.setText(roots[a4]); */
		}

		if (key == 3) {
			textViewdef3.setText(words[wordnum - 1][1]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				textViewdef3.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				// defrepeat(0);
			}

			textViewdef1.setText(roots[a2]);
			textViewdef2.setText(roots[a3]);
		}

	}

	private String[] getroots() {

		String[][] words;

		words = myapp.getwords();

		roots = new String[60];

		for (int i = 0; i < 60; i++) {
			roots[i] = "";

		}
		System.out.println("root定义");
		//

		int k = 0;

		for (int i = 0; i < 20; i++) {

			if (!words[i][0].equals("")) {
				roots[k] = words[i][1];
				k++;
			}

		}

		return roots;
	}

	private int wordnum() {
		int k = 0;
		for (int i = 0; i < words.length; i++) {
			if (!words[i][0].equals("")) {
				k++;
			}
		}

		return k;
	}

	private void stopshape() {
		timerhelp.cancel();

		if (p1) {
			helpshape.cancel();
		}

		wenhaoButton.setImageResource(R.drawable.wenhao);

	}

	public String underlineclear(String key) {
		String flag = key.replace("_", " ");
		return flag;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
		System.out.println("Start");

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		myapp.startlevelmusic();
		if (Integer.parseInt(myapp.get(6)) < 2) {

			if (timergreencontrol) {
				timergreen = new Timer();
				timergreen.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						startActivity(intent);
						finish();
					}
				}, sleeptime);
			} else {
				timer.start();
			}

		}
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
		EasyTracker.getInstance(this).activityStop(this);
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
						myapp.pauselevelmusic();
						timer.cancel();
						stopshape(); // home key处理点
						if (timergreencontrol) {
							timergreen.cancel();
						}

					} else if (reason.equals(SYSTEM_RECENT_APPS)) {
						myapp.pauselevelmusic();
						timer.cancel();
						stopshape();
						if (timergreencontrol) {
							timergreen.cancel();
						}
						// long home key处理点
					}
				}
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			timer.cancel();
			stopshape();
			myapp.pauselevelmusic();
			if (timergreencontrol) {
				timergreen.cancel();
			}

			alertdDialog = new AlertDialog.Builder(this)
					.setTitle("EXIT LEVEL")
					.setMessage("Do you want to exit?")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("Confirm",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									myapp.stoplevelmusic();
									myapp.empty();

									Intent intent = new Intent(
											definitionl8.this, play.class);
									startActivity(intent);
									finish();

								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									alertdDialog.cancel();
									myapp.startlevelmusic();
									if (Integer.parseInt(myapp.get(6)) < 2) {
										if (timergreencontrol) {
											timergreen = new Timer();
											timergreen.schedule(
													new TimerTask() {

														@Override
														public void run() {
															// TODO
															// Auto-generated
															// method stub

															startActivity(intent);
															finish();
														}
													}, sleeptime);
										} else {
											timer.start();
										}

									}
								}
							}).create();

			alertdDialog.show();
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
			timer.cancel();
			stopshape();
						
			if (timergreencontrol) {
				timergreen.cancel();
			}
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(definitionl8.this, play.class);
			startActivity(intent);
			finish();
		}

		
		if (id == R.id.PlayStudyReview) {
			timer.cancel();
			stopshape();
						
			if (timergreencontrol) {
				timergreen.cancel();
			}
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(definitionl8.this, list.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.listpage) {
			timer.cancel();
			stopshape();
						
			if (timergreencontrol) {
				timergreen.cancel();
			}
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(definitionl8.this, listselectactivity.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.coursepage) {
			timer.cancel();
			stopshape();
						
			if (timergreencontrol) {
				timergreen.cancel();
			}
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(definitionl8.this, MainActivity.class);
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
	public void changecolorscore(int key){
		if (key>=86) {
			textViewscore.setTextColor(Color.GREEN);
			
		}
		if (key<=64) {
			textViewscore.setTextColor(Color.RED);
		}
		if (key>64&&key<86) 
			
	 {
			textViewscore.setTextColor(Color.YELLOW);
		}
	}
	
}
