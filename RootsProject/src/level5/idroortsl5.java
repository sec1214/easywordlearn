package level5;

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
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rootsproject.MainActivity;
import com.rootsproject.R;
import com.rootsproject.list;
import com.rootsproject.listselectactivity;
import com.rootsproject.mypublicvalue;
import com.rootsproject.play;
import com.google.analytics.tracking.android.EasyTracker;

public class idroortsl5 extends Activity {

	public  long sleeptime = 2000;
	private Dialog alertdDialog;
	private TextView textView1, textView2, wordTextView;
	private TextView root1, root2, root3, root4, root5, root6;
	private TextView textViewlevel, textViewword, textViewwr, textViewscore;
	private String[][] words;
	private int wordnum;
	private int rootnum; // 统计这里有多少个root 每个word，跟root（）和word（）里头不一样
	private String[] roots;
	private int numroot;
	public int wcon, con; // 错词循环， 不需要TT循环con
	private int clicknumtouch = 0; // 敲击选择root的数量

	private ImageButton wenhaoButton;

	private boolean b1 = false;
	private boolean b2 = false;
	private boolean b3 = false;
	private boolean b4 = false;
	private boolean b5 = false;
	private boolean b6 = false;

	
	private double clicknum, rightnum;
	private TextView worddefview;
	private mypublicvalue myapp;
	private boolean p1 = false; // 操控help button的控制阀

	private CountDownTimer helpshape;

	private BroadcastReceiver receiver;

	private Timer timergreen;
	private boolean timergreencontrol = false;
	private Intent intent;

	private Matrix matrix = new Matrix();

	CountDownTimer timer = new CountDownTimer(10000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub

			int k = (int) (millisUntilFinished / 1000);

			textViewwr.setText(  k + "'s");

			if (k > 5) {
				myapp.playmusic(4);
			}

			if (k == 5) {
				textViewwr.setTextColor(Color.WHITE);
				textViewwr.setBackgroundColor(Color.RED);
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
			Intent intent = new Intent(idroortsl5.this, idroortsl5.class);
			startActivity(intent);
			defrepeat(1);
			myapp.setscore(0, ++clicknum);
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
		EasyTracker.getInstance(this).activityStart(this);

		setContentView(R.layout.zidroots);
		sleeptime= Long.parseLong(this.getString(R.string.sleeptime));
		receiver = new HomeKeyEventBroadCastReceiver();
		getApplicationContext().registerReceiver(receiver,
				new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

		wenhaoButton = (ImageButton) this.findViewById(R.id.wenhaobutton);

		textView1 = (TextView) this.findViewById(R.id.textview1);
		textView2 = (TextView) this.findViewById(R.id.textview2);

		textViewlevel = (TextView) this.findViewById(R.id.leveltext);
		textViewword = (TextView) this.findViewById(R.id.wordtext);
		textViewwr = (TextView) this.findViewById(R.id.wrtext);

		textViewscore = (TextView) this.findViewById(R.id.scoretext);

		root1 = (TextView) this.findViewById(R.id.wordroot1);
		root2 = (TextView) this.findViewById(R.id.wordroot2);
		root3 = (TextView) this.findViewById(R.id.wordroot3);
		root4 = (TextView) this.findViewById(R.id.wordroot4);
		root5 = (TextView) this.findViewById(R.id.wordroot5);
		root6 = (TextView) this.findViewById(R.id.wordroot6);
		worddefview = (TextView) this.findViewById(R.id.worddefview);
		myapp = (mypublicvalue) getApplication();
		myapp.startlevelmusic();
		textView1.setText(underlineclear(myapp.get(0)));
		textView2.setText(myapp.get(1));

		textViewlevel.setText(" Level: " + myapp.get(3)); // 设定显示level的控件

		wordnum = Integer.parseInt(myapp.get(4));

		clicknum = myapp.getscore(0);
		rightnum = myapp.getscore(1);

		wcon = myapp.getreviewwrongcontrol(); // 复习控制阀值
		con = myapp.getrepeatcontrol();

		System.out.println("wcon" + wcon);
		System.out.println("con" + con);

		if (wcon == 0) {
			words = myapp.getwords();
		}
		if (wcon == 1) {
			words = myapp.getCwrongwords();
			textViewwr.setText("Wrong Review");
			textViewwr.setBackgroundColor(Color.RED);
			textViewwr.setTextColor(Color.WHITE);

		}
		changecolorscore((int) ((myapp.getscore(1) / myapp.getscore(0)) * 100));
		textViewword.setText("Word: " + wordnum + " / " + wordnum()); // 设定显示几号word的控件
		textViewscore.setText(
				 (int) ((myapp.getscore(1) / myapp.getscore(0)) * 100) + "%");
		worddefview.setText(words[wordnum - 1][1]);
		wordTextView = (TextView) this.findViewById(R.id.wordtextview);

		String word = words[wordnum - 1][0];
		wordTextView.setText(word);

		this.ran();

		System.out.println("idroots 这个word多少个root： " + rootnum);

		if (myapp.gethelpcontrol(2) == 0) {
			timerhelp.start();
		}

		if (Integer.parseInt(myapp.get(6)) < 2) {

			textViewwr.setText(   15 + "'s");
			timer.start();

			root1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root1.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root1.setBackgroundResource(R.drawable.green);
						b1=true;
						root1.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);
						myapp.setscore(1, ++rightnum);
						myapp.setscore(0, ++clicknum);
						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了

							defrepeat(0);
							System.out.println("过了idrootsl5");

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								intent = new Intent(idroortsl5.this,
										idroortsl5.class);
							
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

							if (words[wordnum][0].equals("")) {
								intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}

							
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

					} else {
						if(!b1){
						root1.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						stopshape();
						myapp.playmusic(0);
						if (!timergreencontrol) {

						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);

						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);

						}
					
						defrepeat(1);
						Intent intent = new Intent(idroortsl5.this,
								idroortsl5.class);
						startActivity(intent);
						myapp.setscore(0, ++clicknum);
						timer.cancel();						
						finish();
						}
					}
					}
				}
			});
			root2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root2.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root2.setBackgroundResource(R.drawable.green);
						b2=true;
						root2.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);
						myapp.setscore(1, ++rightnum);
						myapp.setscore(0, ++clicknum);
						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了

							defrepeat(0);
							System.out.println("过了idrootsl5");

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								intent = new Intent(idroortsl5.this,
										idroortsl5.class);
							
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

							if (words[wordnum][0].equals("")) {
								intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}

							
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

					} else {
						if(!b2){
						root2.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						stopshape();
						myapp.playmusic(0);
						if (!timergreencontrol) {

						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);

						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);

						}
					
						defrepeat(1);
						Intent intent = new Intent(idroortsl5.this,
								idroortsl5.class);
						startActivity(intent);
						myapp.setscore(0, ++clicknum);
						timer.cancel();						
						finish();
						}
					}
					}
				}
			});
			root3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root3.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root3.setBackgroundResource(R.drawable.green);
						b3=true;
						root3.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);
						myapp.setscore(1, ++rightnum);
						myapp.setscore(0, ++clicknum);
						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了

							defrepeat(0);
							System.out.println("过了idrootsl5");

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								intent = new Intent(idroortsl5.this,
										idroortsl5.class);
							
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

							if (words[wordnum][0].equals("")) {
								intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}

							
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

					} else {
						if(!b3){
						root3.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						stopshape();
						myapp.playmusic(0);
						if (!timergreencontrol) {

						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);

						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);

						}
					
						defrepeat(1);
						Intent intent = new Intent(idroortsl5.this,
								idroortsl5.class);
						startActivity(intent);
						myapp.setscore(0, ++clicknum);
						timer.cancel();						
						finish();
						}
					}
					}
				}
			});
			root4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root4.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root4.setBackgroundResource(R.drawable.green);
						b4=true;
						root4.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);
						myapp.setscore(1, ++rightnum);
						myapp.setscore(0, ++clicknum);
						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了

							defrepeat(0);
							System.out.println("过了idrootsl5");

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								intent = new Intent(idroortsl5.this,
										idroortsl5.class);
								
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

							if (words[wordnum][0].equals("")) {
								intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}

							
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

					} else {
						if(!b4){
						root4.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						stopshape();
						myapp.playmusic(0);
						if (!timergreencontrol) {

						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);

						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);

						}
					
						defrepeat(1);
						Intent intent = new Intent(idroortsl5.this,
								idroortsl5.class);
						startActivity(intent);
						myapp.setscore(0, ++clicknum);
						timer.cancel();						
						finish();
						}
					}
					}
				}
			});
			root5.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root5.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root5.setBackgroundResource(R.drawable.green);
						b5=true;
						root5.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);
						myapp.setscore(1, ++rightnum);
						myapp.setscore(0, ++clicknum);
						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了

							defrepeat(0);
							System.out.println("过了idrootsl5");

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								intent = new Intent(idroortsl5.this,
										idroortsl5.class);
							
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

							if (words[wordnum][0].equals("")) {
								intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}

							
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

					} else {
						if(!b5){
						root5.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						stopshape();
						myapp.playmusic(0);
						if (!timergreencontrol) {

						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);

						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);

						}
					
						defrepeat(1);
						Intent intent = new Intent(idroortsl5.this,
								idroortsl5.class);
						startActivity(intent);
						myapp.setscore(0, ++clicknum);
						timer.cancel();						
						finish();
						}
					}
					}
				}
			});
			root6.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root6.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root6.setBackgroundResource(R.drawable.green);
						b6=true;
						root6.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);
						myapp.setscore(1, ++rightnum);
						myapp.setscore(0, ++clicknum);
						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了

							defrepeat(0);
							System.out.println("过了idrootsl5");

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								intent = new Intent(idroortsl5.this,
										idroortsl5.class);
							
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

							if (words[wordnum][0].equals("")) {
								intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}

							
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

					} else {
						if(!b6){
						root6.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						stopshape();
						myapp.playmusic(0);
						if (!timergreencontrol) {

						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);

						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);

						}
					
						defrepeat(1);
						Intent intent = new Intent(idroortsl5.this,
								idroortsl5.class);
						startActivity(intent);
						myapp.setscore(0, ++clicknum);
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
			root1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root1.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root1.setBackgroundResource(R.drawable.green);
						b1=true;
						root1.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);

						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了
							defrepeat(0); // 计数器错误 清零

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(idroortsl5.this,
										idroortsl5.class);
								startActivity(intent);

								finish();
							}

							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}
								startActivity(intent);

								finish();
							}

						}

					} else {
						if (!b1) {
							root1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();

							myapp.playmusic(0);
						}
						

					}

				}
			});
			root2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root2.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root2.setBackgroundResource(R.drawable.green);
						b2=true;
						root2.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);

						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了
							defrepeat(0); // 计数器错误 清零

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(idroortsl5.this,
										idroortsl5.class);
								startActivity(intent);

								finish();
							}

							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}
								startActivity(intent);

								finish();
							}

						}

					} else {
						if (!b2) {
							root2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();

							myapp.playmusic(0);
						}
					

					}

				}
			});
			root3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root3.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root3.setBackgroundResource(R.drawable.green);
						b3=true;
						root3.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);

						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了
							defrepeat(0); // 计数器错误 清零

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(idroortsl5.this,
										idroortsl5.class);
								startActivity(intent);

								finish();
							}

							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}
								startActivity(intent);

								finish();
							}

						}

					} else {
						if (!b3) {
							root3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();

							myapp.playmusic(0);
						}
						

					}

				}
			});
			root4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root4.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root4.setBackgroundResource(R.drawable.green);
						b4=true;
						root4.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);

						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了
							defrepeat(0); // 计数器错误 清零

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(idroortsl5.this,
										idroortsl5.class);
								startActivity(intent);

								finish();
							}

							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}
								startActivity(intent);

								finish();
							}

						}

					} else {
						if (!b4) {
							root4.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();

							myapp.playmusic(0);	
						}
					

					}

				}
			});
			root5.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root5.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root5.setBackgroundResource(R.drawable.green);
						b5=true;
						root5.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);

						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了
							defrepeat(0); // 计数器错误 清零

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(idroortsl5.this,
										idroortsl5.class);
								startActivity(intent);

								finish();
							}

							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}
								startActivity(intent);

								finish();
							}

						}

					} else {
						if (!b5) {
							root5.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();

							myapp.playmusic(0);

						}
						
					}

				}
			});
			root6.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String k = (String) root6.getText();
					if (k.equals(words[wordnum - 1][2]) // 做对了
							|| k.equals(words[wordnum - 1][4])
							|| k.equals(words[wordnum - 1][6])
							|| k.equals(words[wordnum - 1][8]))

					{
						root6.setBackgroundResource(R.drawable.green);
						b6=true;
						root6.setText(getchangeroot(k));
						wordTextView.setText(getchangeword(k));
						myapp.playmusic(1);

						clicknumtouch++;

						if (clicknumtouch == rootnum) { // 都做对了
							defrepeat(0); // 计数器错误 清零

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(idroortsl5.this,
										idroortsl5.class);
								startActivity(intent);

								finish();
							}

							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(idroortsl5.this,
										scorel5.class);
								if (wcon == 0) {
									myapp.cleanwrongwords();
								}
								if (wcon == 1) {
									myapp.cleanCwrongwords();
								}
								startActivity(intent);

								finish();
							}

						}

					} else {
						if (!b6) {
							root6.setBackgroundResource(R.drawable.red);
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
				if (myapp.gethelpcontrol(2) == 0) {
					myapp.sethelpcontrol(2, 1);
				}

				alertdDialog = new AlertDialog.Builder(idroortsl5.this)
						.setTitle("Instruction")
						.setMessage(getString(R.string.idroothelp))
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
										if (Integer.parseInt(myapp.get(6)) < 2) {
											timer.start();
										}

									}
								}).create();

				alertdDialog.show();

			}
		});
	}

	private void ran() {
		// TODO Auto-generated method stub
		numroot = 0; // 防止 最后出现不够20 出现的情况

		this.getroots();

		for (int i = 0; i < 80; i++) {

			if (!roots[i].equals("")) {
				numroot = i + 1;
			}

		}

		double h = Math.random() * 6;
		int selection = (int) h;

		if (selection == 0) {
			method(1);

		}
		if (selection == 1) {

			method(2);

		}
		if (selection == 2) {

			method(3);

		}
		if (selection == 3) {

			method(4);

		}
		if (selection == 4) {

			method(5);

		}
		if (selection == 5) {

			method(6);
		}

	}

	private void method(int key) {

		rootnum = 0;

		int a1, a2, a3, a4, a5, a6;
		double h;
		a1 = 0;
		a2 = 0;
		a3 = 0;
		a4 = 0;
		a5 = 0;
		a6 = 0;

		System.out.println("多少个root+" + numroot);

		for (int i = 0; i < numroot; i++) {

			if (words[wordnum - 1][2].equals(roots[i])) { // 确定4个root
				a1 = i;
				break;
			}
		}

		for (int i = 0; i < numroot; i++) {

			if (words[wordnum - 1][4].equals(roots[i])) {
				a2 = i;
				break;
			}
		}

		for (int i = 0; i < numroot; i++) {

			if (words[wordnum - 1][6].equals(roots[i])) {
				a3 = i;
				break;
			}
		}

		for (int i = 0; i < numroot; i++) {

			if (words[wordnum - 1][8].equals(roots[i])) {
				a4 = i;
				break;
			}
		}

		if (a2 == 0) {
			rootnum = 1;
		}
		if (a2 != 0 && a3 == 0) {
			rootnum = 2;
		}
		if (a2 != 0 && a3 != 0 && a4 == 0) {
			rootnum = 3;
		}
		if (a2 != 0 && a3 != 0 && a4 != 0 && a5 == 0) {
			rootnum = 4;
		}

		if (rootnum == 1) { // 证明只有一个root

			System.out.println("rootnum   " + 1);

			h = Math.random() * numroot;
			a2 = (int) h;
			while (roots[a1].equals(roots[a2])) {
				h = Math.random() * numroot;
				a2 = (int) h;
			}

			h = Math.random() * numroot;
			a3 = (int) h;
			while (roots[a1].equals(roots[a3]) || roots[a2].equals(roots[a3])) {
				h = Math.random() * numroot;
				a3 = (int) h;
			}

			h = Math.random() * numroot;
			a4 = (int) h;
			while (roots[a1].equals(roots[a4]) || roots[a2].equals(roots[a4])
					|| roots[a3].equals(roots[a4])) {
				h = Math.random() * numroot;
				a4 = (int) h;
			}

			h = Math.random() * numroot;
			a5 = (int) h;
			while (roots[a1].equals(roots[a5]) || roots[a2].equals(roots[a5])
					|| roots[a3].equals(roots[a5])
					|| roots[a4].equals(roots[a5])) {
				h = Math.random() * numroot;
				a5 = (int) h;
			}

			h = Math.random() * numroot;
			a6 = (int) h;
			while (roots[a1].equals(roots[a6]) || roots[a2].equals(roots[a6])
					|| roots[a3].equals(roots[a6])
					|| roots[a4].equals(roots[a6])
					|| roots[a5].equals(roots[a6])) {
				h = Math.random() * numroot;
				a6 = (int) h;
			}

			if (key == 1) {
				root1.setText(roots[a1]);
				root2.setText(roots[a2]);
				root3.setText(roots[a3]);
				root4.setText(roots[a4]);
				root5.setText(roots[a5]);
				root6.setText(roots[a6]);

				if (Integer.parseInt(myapp.get(6)) == 2) {
					root1.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 2) {
				root2.setText(roots[a1]);
				root3.setText(roots[a3]);
				root4.setText(roots[a4]);
				root5.setText(roots[a5]);
				root6.setText(roots[a6]);
				root1.setText(roots[a2]);

				if (Integer.parseInt(myapp.get(6)) == 2) {
					root2.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 3) {
				root3.setText(roots[a1]);
				root4.setText(roots[a4]);
				root5.setText(roots[a5]);
				root6.setText(roots[a6]);
				root1.setText(roots[a2]);
				root2.setText(roots[a3]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root3.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}
			if (key == 4) {
				root4.setText(roots[a1]);
				root5.setText(roots[a5]);
				root6.setText(roots[a6]);
				root1.setText(roots[a2]);
				root2.setText(roots[a3]);
				root3.setText(roots[a4]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root4.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 5) {
				root5.setText(roots[a1]);
				root6.setText(roots[a6]);
				root1.setText(roots[a2]);
				root2.setText(roots[a3]);
				root3.setText(roots[a4]);
				root4.setText(roots[a5]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root5.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 6) {
				root6.setText(roots[a1]);
				root1.setText(roots[a2]);
				root2.setText(roots[a3]);
				root3.setText(roots[a4]);
				root4.setText(roots[a5]);
				root5.setText(roots[a6]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root6.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}
		}

		if (rootnum == 2) { // 证明有2个root 分别是a1 和 a2

			System.out.println("rootnum   " + 2);

			h = Math.random() * numroot;
			a3 = (int) h;
			while (roots[a1].equals(roots[a3]) || roots[a2].equals(roots[a3])) {
				h = Math.random() * numroot;
				a3 = (int) h;
			}

			h = Math.random() * numroot;
			a4 = (int) h;
			while (roots[a1].equals(roots[a4]) || roots[a2].equals(roots[a4])
					|| roots[a3].equals(roots[a4])) {
				h = Math.random() * numroot;
				a4 = (int) h;
			}

			h = Math.random() * numroot;
			a5 = (int) h;
			while (roots[a1].equals(roots[a5]) || roots[a2].equals(roots[a5])
					|| roots[a3].equals(roots[a5])
					|| roots[a4].equals(roots[a5])) {
				h = Math.random() * numroot;
				a5 = (int) h;
			}

			h = Math.random() * numroot;
			a6 = (int) h;
			while (roots[a1].equals(roots[a6]) || roots[a2].equals(roots[a6])
					|| roots[a3].equals(roots[a6])
					|| roots[a4].equals(roots[a6])
					|| roots[a5].equals(roots[a6])) {
				h = Math.random() * numroot;
				a6 = (int) h;
			}

			if (key == 1) {
				root1.setText(roots[a1]);
				root2.setText(roots[a2]);
				root3.setText(roots[a3]);
				root4.setText(roots[a4]);
				root5.setText(roots[a5]);
				root6.setText(roots[a6]);

				if (Integer.parseInt(myapp.get(6)) == 2) {
					root1.setBackgroundResource(R.drawable.green); // a1
					root2.setBackgroundResource(R.drawable.green); // a2
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 2) {
				root2.setText(roots[a1]);
				root3.setText(roots[a3]);
				root4.setText(roots[a2]);
				root5.setText(roots[a5]);
				root6.setText(roots[a6]);
				root1.setText(roots[a4]);

				if (Integer.parseInt(myapp.get(6)) == 2) {
					root2.setBackgroundResource(R.drawable.green);
					root4.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 3) {
				root3.setText(roots[a1]);
				root4.setText(roots[a4]);
				root5.setText(roots[a5]);
				root6.setText(roots[a6]);
				root1.setText(roots[a2]);
				root2.setText(roots[a3]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root3.setBackgroundResource(R.drawable.green);
					root1.setBackgroundResource(R.drawable.green);

					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}
			if (key == 4) {
				root4.setText(roots[a1]);
				root5.setText(roots[a2]);
				root6.setText(roots[a6]);
				root1.setText(roots[a5]);
				root2.setText(roots[a3]);
				root3.setText(roots[a4]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root4.setBackgroundResource(R.drawable.green);
					root5.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 5) {
				root5.setText(roots[a1]);
				root6.setText(roots[a2]);
				root1.setText(roots[a6]);
				root2.setText(roots[a3]);
				root3.setText(roots[a4]);
				root4.setText(roots[a5]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root5.setBackgroundResource(R.drawable.green);
					root6.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 6) {
				root6.setText(roots[a1]);
				root1.setText(roots[a4]);
				root2.setText(roots[a3]);
				root3.setText(roots[a2]);
				root4.setText(roots[a5]);
				root5.setText(roots[a6]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root6.setBackgroundResource(R.drawable.green);
					root3.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

		}

		if (rootnum == 3) { // 证明有3个root

			System.out.println("rootnum   " + 3);

			h = Math.random() * numroot;
			a4 = (int) h;
			while (roots[a1].equals(roots[a4]) || roots[a2].equals(roots[a4])
					|| roots[a3].equals(roots[a4])) {
				h = Math.random() * numroot;
				a4 = (int) h;
			}

			h = Math.random() * numroot;
			a5 = (int) h;
			while (roots[a1].equals(roots[a5]) || roots[a2].equals(roots[a5])
					|| roots[a3].equals(roots[a5])
					|| roots[a4].equals(roots[a5])) {
				h = Math.random() * numroot;
				a5 = (int) h;
			}

			h = Math.random() * numroot;
			a6 = (int) h;
			while (roots[a1].equals(roots[a6]) || roots[a2].equals(roots[a6])
					|| roots[a3].equals(roots[a6])
					|| roots[a4].equals(roots[a6])
					|| roots[a5].equals(roots[a6])) {
				h = Math.random() * numroot;
				a6 = (int) h;
			}

			if (key == 1) {
				root1.setText(roots[a1]);
				root2.setText(roots[a2]);
				root3.setText(roots[a3]);
				root4.setText(roots[a4]);
				root5.setText(roots[a5]);
				root6.setText(roots[a6]);

				if (Integer.parseInt(myapp.get(6)) == 2) {
					root1.setBackgroundResource(R.drawable.green);
					root2.setBackgroundResource(R.drawable.green);
					root3.setBackgroundResource(R.drawable.green);

					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 2) {
				root2.setText(roots[a1]);
				root3.setText(roots[a4]);
				root4.setText(roots[a2]);
				root5.setText(roots[a5]);
				root6.setText(roots[a3]);
				root1.setText(roots[a6]);

				if (Integer.parseInt(myapp.get(6)) == 2) {
					root2.setBackgroundResource(R.drawable.green);
					root4.setBackgroundResource(R.drawable.green);
					root6.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 3) {
				root3.setText(roots[a1]);
				root4.setText(roots[a2]);
				root5.setText(roots[a3]);
				root6.setText(roots[a6]);
				root1.setText(roots[a4]);
				root2.setText(roots[a5]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root3.setBackgroundResource(R.drawable.green);
					root4.setBackgroundResource(R.drawable.green);
					root5.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}
			if (key == 4) {
				root4.setText(roots[a1]);
				root5.setText(roots[a5]);
				root6.setText(roots[a3]);
				root1.setText(roots[a6]);
				root2.setText(roots[a2]);
				root3.setText(roots[a4]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root4.setBackgroundResource(R.drawable.green);
					root2.setBackgroundResource(R.drawable.green);
					root6.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 5) {
				root5.setText(roots[a1]);
				root6.setText(roots[a6]);
				root1.setText(roots[a2]);
				root2.setText(roots[a5]);
				root3.setText(roots[a4]);
				root4.setText(roots[a3]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root5.setBackgroundResource(R.drawable.green);
					root1.setBackgroundResource(R.drawable.green);
					root4.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 6) {
				root6.setText(roots[a1]);
				root1.setText(roots[a4]);
				root2.setText(roots[a2]);
				root3.setText(roots[a3]);
				root4.setText(roots[a5]);
				root5.setText(roots[a6]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root6.setBackgroundResource(R.drawable.green);
					root2.setBackgroundResource(R.drawable.green);
					root3.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

		}

		if (rootnum == 4) { // 4个root

			System.out.println("rootnum   " + 4);

			h = Math.random() * numroot;
			a5 = (int) h;
			while (roots[a1].equals(roots[a5]) || roots[a2].equals(roots[a5])
					|| roots[a3].equals(roots[a5])
					|| roots[a4].equals(roots[a5])) {
				h = Math.random() * numroot;
				a5 = (int) h;
			}

			h = Math.random() * numroot;
			a6 = (int) h;
			while (roots[a1].equals(roots[a6]) || roots[a2].equals(roots[a6])
					|| roots[a3].equals(roots[a6])
					|| roots[a4].equals(roots[a6])
					|| roots[a5].equals(roots[a6])) {
				h = Math.random() * numroot;
				a6 = (int) h;
			}

			if (key == 1) {
				root1.setText(roots[a1]);
				root2.setText(roots[a5]);
				root3.setText(roots[a4]);
				root4.setText(roots[a2]);
				root5.setText(roots[a6]);
				root6.setText(roots[a3]);

				if (Integer.parseInt(myapp.get(6)) == 2) {
					root1.setBackgroundResource(R.drawable.green);
					root4.setBackgroundResource(R.drawable.green);
					root6.setBackgroundResource(R.drawable.green);
					root3.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 2) {
				root2.setText(roots[a1]);
				root3.setText(roots[a4]);
				root4.setText(roots[a5]);
				root5.setText(roots[a2]);
				root6.setText(roots[a6]);
				root1.setText(roots[a3]);

				if (Integer.parseInt(myapp.get(6)) == 2) {
					root2.setBackgroundResource(R.drawable.green);
					root5.setBackgroundResource(R.drawable.green);
					root1.setBackgroundResource(R.drawable.green);
					root3.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 3) {
				root3.setText(roots[a1]);
				root4.setText(roots[a2]);
				root5.setText(roots[a4]);
				root6.setText(roots[a5]);
				root1.setText(roots[a6]);
				root2.setText(roots[a3]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root3.setBackgroundResource(R.drawable.green);
					root4.setBackgroundResource(R.drawable.green);
					root2.setBackgroundResource(R.drawable.green);
					root5.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}
			if (key == 4) {
				root4.setText(roots[a1]);
				root5.setText(roots[a6]);
				root6.setText(roots[a2]);
				root1.setText(roots[a1]);
				root2.setText(roots[a3]);
				root3.setText(roots[a5]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root4.setBackgroundResource(R.drawable.green);
					root6.setBackgroundResource(R.drawable.green);
					root2.setBackgroundResource(R.drawable.green);
					root1.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 5) {
				root5.setText(roots[a1]);
				root6.setText(roots[a3]);
				root1.setText(roots[a6]);
				root2.setText(roots[a5]);
				root3.setText(roots[a4]);
				root4.setText(roots[a2]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root5.setBackgroundResource(R.drawable.green);
					root4.setBackgroundResource(R.drawable.green);
					root6.setBackgroundResource(R.drawable.green);
					root3.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

			if (key == 6) {
				root6.setText(roots[a1]);
				root1.setText(roots[a2]);
				root2.setText(roots[a6]);
				root3.setText(roots[a4]);
				root4.setText(roots[a5]);
				root5.setText(roots[a3]);
				if (Integer.parseInt(myapp.get(6)) == 2) {
					root6.setBackgroundResource(R.drawable.green);
					root1.setBackgroundResource(R.drawable.green);
					root5.setBackgroundResource(R.drawable.green);
					root3.setBackgroundResource(R.drawable.green);
					// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
					// //playmusic(1);
					// defrepeat(0);
				}
			}

		}

	}

	private String[] getroots() {
		String[][] words;

		words = myapp.getwords();

		roots = new String[80];

		for (int i = 0; i < 80; i++) {
			roots[i] = "";

		}

		int k = 0;

		for (int i = 0; i < 20; i++) {

			if (!words[i][2].equals("")) {
				roots[k] = words[i][2];
				k++;
			}

		}
		for (int i = 0; i < 20; i++) {

			if (!words[i][4].equals("")) {
				roots[k] = words[i][4];
				k++;
			}

		}

		for (int i = 0; i < 20; i++) {

			if (!words[i][6].equals("")) {
				roots[k] = words[i][6];
				k++;
			}

		}
		for (int i = 0; i < 20; i++) {

			if (!words[i][8].equals("")) {
				roots[k] = words[i][8];
				k++;
			}

		}

		return roots;

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

	private int wordnum() {
		int k = 0;
		for (int i = 0; i < words.length; i++) {
			if (!words[i][0].equals("")) {
				k++;
			}
		}

		return k;
	}

	private String getchangeroot(String key) {
		String k = "";
		for (int i = 2; i < 9; i = i + 2) {
			if (words[wordnum - 1][i].equals(key)) {
				k = words[wordnum - 1][i] + ": " + words[wordnum - 1][i + 1];
				break;
			}

		}

		return k;
	}

	private SpannableStringBuilder getchangeword(String key) { // 本方法自动创建相对应的缺失root的字符串
		// TODO Auto-generated method stub
		// 起始root的地点
		String word = words[wordnum - 1][0];

		String changeword = key;

		int start = word.indexOf(changeword);

		int end = start + changeword.length();

		SpannableStringBuilder style = new SpannableStringBuilder(word);

		style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.idroot)), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		style.setSpan(new RelativeSizeSpan(1.2f), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		return style;
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

									Intent intent = new Intent(idroortsl5.this,
											play.class);
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
			Intent intent = new Intent(idroortsl5.this, play.class);
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
			Intent intent = new Intent(idroortsl5.this, list.class);
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
			Intent intent = new Intent(idroortsl5.this, listselectactivity.class);
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
			Intent intent = new Intent(idroortsl5.this, MainActivity.class);
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
			textViewscore.setTextColor(Color.WHITE);
			textViewscore.setBackgroundColor(Color.GREEN);

			
		}
		if (key<=64) {
			textViewscore.setTextColor(Color.WHITE);
			textViewscore.setBackgroundColor(Color.RED);

		}
		if (key>64&&key<86) 
			
	 {
			textViewscore.setTextColor(Color.WHITE);
			textViewscore.setBackgroundColor(Color.YELLOW);

		}
	}
	
}
