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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rootsproject.MainActivity;
import com.rootsproject.R;
import com.rootsproject.definition;
import com.rootsproject.list;
import com.rootsproject.listselectactivity;
import com.rootsproject.mypublicvalue;
import com.rootsproject.play;
import com.google.analytics.tracking.android.EasyTracker;

public class wordsl8 extends Activity {

	private Dialog alertdDialog;
	private TextView textView1, textView2, defTextView, wordTextView1,
			wordTextView2, wordTextView3, wordTextView4;
	private TextView textViewlevel, textViewword, textViewwr, textViewscore;
	private mypublicvalue myapp;
	private String[][] words;

	private String[] roots;

	private int wordnum; // �ڼ���word
	private int numroot;

	public int wcon; // ��ϰ���Ʒ�

	private boolean p1 = false; // �ٿ�help button�Ŀ��Ʒ�

	public  long sleeptime = 2000;
	private Timer timergreen;

	private boolean timergreencontrol = false;

	private Intent intent;

	private double clicknum, rightnum;
	private ImageButton wenhaoButton;

	private Matrix matrix = new Matrix();

	private CountDownTimer helpshape;

	private BroadcastReceiver receiver;
	private Animation mAnimationRight;
	CountDownTimer timer = new CountDownTimer(10000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub

			int k = (int) (millisUntilFinished / 1000);

			textViewwr.setText( k + "'s");

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

				myapp.playmusic(3); // ʧ������
			}

			Intent intent = new Intent(wordsl8.this, wordsl8.class);
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
		setContentView(R.layout.zword);
		
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

		defTextView = (TextView) this.findViewById(R.id.definition);
		wordTextView1 = (TextView) this.findViewById(R.id.word1);
		wordTextView2 = (TextView) this.findViewById(R.id.word2);
		wordTextView3 = (TextView) this.findViewById(R.id.word3);
		wordTextView4 = (TextView) this.findViewById(R.id.word4);

		myapp = (mypublicvalue) getApplication();
		myapp.startlevelmusic();
		textView1.setText(underlineclear(myapp.get(0)));
		textView2.setText(myapp.get(1));

		textViewlevel.setText(" Level: " + myapp.get(3)); // �趨��ʾlevel�Ŀؼ�

		clicknum = myapp.getscore(0);
		rightnum = myapp.getscore(1);

		wcon = myapp.getreviewwrongcontrol(); // ��ϰ���Ʒ�ֵ

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
		textViewscore.setText(
				(int) ((myapp.getscore(1) / myapp.getscore(0)) * 100) + "%");
		/*added by xiaoqian yu, 2014-12-23, start*/
		mAnimationRight = AnimationUtils.loadAnimation(
                wordsl8.this, 
                myapp.calculateViewAnimationID(myapp.setPulseTimeInterval(myapp.getscore(1), 
                		                                                  myapp.getscore(0))));
		textViewscore.startAnimation(mAnimationRight);
		myapp.playPulseSound(myapp.setPulseTimeInterval(myapp.getscore(1), 
                myapp.getscore(0)), -1);
		/*added by xiaoqian yu, 2014-12-23, over*/
		wordnum = Integer.parseInt(myapp.get(4));
		textViewword.setText("Word: " + wordnum + " / " + wordnum()); // �趨��ʾ����word�Ŀؼ�
		defTextView.setText(words[wordnum - 1][1]);

		this.ran();

		if (myapp.gethelpcontrol(4) == 0) {
			//comment this line, to Take out the shaking of the question mark
			//timerhelp.start();
		}
		/*added by xiaoqian yu, 2014-12-31, start*/
		wordTextView1.setEnabled(true);
		wordTextView2.setEnabled(true);
		wordTextView3.setEnabled(true);
		wordTextView4.setEnabled(true);
		/*added by xiaoqian yu, 2014-12-31, over*/
		if (Integer.parseInt(myapp.get(6)) < 2) {

			textViewwr.setText(10 + "'s");
			timer.start();

			wordTextView1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					wordTextView1.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String key = (String) wordTextView1.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (key.equals(words[wordnum - 1][0]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (key.equals(words[wordnum - 1][0])) { // ������

						wordTextView1.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						myapp.setscore(1, rightnum + 1);

						defrepeat(0);
						if (!words[wordnum][0].equals("")) {

							System.out.println("ѭ����һ��");
							myapp.set(4, Integer.toString(wordnum + 1));
							double h = Math.random();

							if (h < 0.5) {
								intent = new Intent(wordsl8.this,
										definitionl8.class);

							} else {
								intent = new Intent(wordsl8.this, wordsl8.class);

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

						if (words[wordnum][0].equals("")) { // ѭ��������

							System.out.println("ѭ������");
							intent = new Intent(wordsl8.this, scorel8.class);

							if (wcon == 0) {
								myapp.cleanwrongwords();
							}
							if (wcon == 1) {
								myapp.cleanCwrongwords();
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

					}

					if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

						wordTextView1.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						myapp.playmusic(0);
						stopshape();
						if (!timergreencontrol) {
						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);
						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);
						}
						defrepeat(1); // ���������˼�1
						Intent intent = new Intent(wordsl8.this, wordsl8.class);
						startActivity(intent);
						myapp.setscore(0, clicknum + 1);
						timer.cancel();					
						finish();
						}
					}

				}
			});

			wordTextView2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					wordTextView2.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String key = (String) wordTextView2.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (key.equals(words[wordnum - 1][0]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (key.equals(words[wordnum - 1][0])) { // ������

						wordTextView2.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						myapp.setscore(1, rightnum + 1);

						defrepeat(0);
						if (!words[wordnum][0].equals("")) {

							System.out.println("ѭ����һ��");
							myapp.set(4, Integer.toString(wordnum + 1));
							double h = Math.random();

							if (h < 0.5) {
								intent = new Intent(wordsl8.this,
										definitionl8.class);

							} else {
								intent = new Intent(wordsl8.this, wordsl8.class);

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

						if (words[wordnum][0].equals("")) { // ѭ��������

							System.out.println("ѭ������");
							intent = new Intent(wordsl8.this, scorel8.class);

							if (wcon == 0) {
								myapp.cleanwrongwords();
							}
							if (wcon == 1) {
								myapp.cleanCwrongwords();
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

					}

					if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

						wordTextView2.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						myapp.playmusic(0);
						stopshape();
						if (!timergreencontrol) {
						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);
						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);
						}
						defrepeat(1); // ���������˼�1
						Intent intent = new Intent(wordsl8.this, wordsl8.class);
						startActivity(intent);
						myapp.setscore(0, clicknum + 1);
						timer.cancel();					
						finish();
						}
					}

				}
			});
			wordTextView3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					wordTextView3.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String key = (String) wordTextView3.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (key.equals(words[wordnum - 1][0]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (key.equals(words[wordnum - 1][0])) { // ������

						wordTextView3.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						myapp.setscore(1, rightnum + 1);

						defrepeat(0);
						if (!words[wordnum][0].equals("")) {

							System.out.println("ѭ����һ��");
							myapp.set(4, Integer.toString(wordnum + 1));
							double h = Math.random();

							if (h < 0.5) {
								intent = new Intent(wordsl8.this,
										definitionl8.class);

							} else {
								intent = new Intent(wordsl8.this, wordsl8.class);

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

						if (words[wordnum][0].equals("")) { // ѭ��������

							System.out.println("ѭ������");
							intent = new Intent(wordsl8.this, scorel8.class);

							if (wcon == 0) {
								myapp.cleanwrongwords();
							}
							if (wcon == 1) {
								myapp.cleanCwrongwords();
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

					}

					if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

						wordTextView3.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						myapp.playmusic(0);
						stopshape();
						if (!timergreencontrol) {
						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);
						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);
						}
						defrepeat(1); // ���������˼�1
						Intent intent = new Intent(wordsl8.this, wordsl8.class);
						startActivity(intent);
						myapp.setscore(0, clicknum + 1);
						timer.cancel();					
						finish();
						}
					}

				}
			});

			wordTextView4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					wordTextView4.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String key = (String) wordTextView4.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (key.equals(words[wordnum - 1][0]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (key.equals(words[wordnum - 1][0])) { // ������

						wordTextView4.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						myapp.setscore(1, rightnum + 1);

						defrepeat(0);
						if (!words[wordnum][0].equals("")) {

							System.out.println("ѭ����һ��");
							myapp.set(4, Integer.toString(wordnum + 1));
							double h = Math.random();

							if (h < 0.5) {
								intent = new Intent(wordsl8.this,
										definitionl8.class);

							} else {
								intent = new Intent(wordsl8.this, wordsl8.class);

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

						if (words[wordnum][0].equals("")) { // ѭ��������

							System.out.println("ѭ������");
							intent = new Intent(wordsl8.this, scorel8.class);

							if (wcon == 0) {
								myapp.cleanwrongwords();
							}
							if (wcon == 1) {
								myapp.cleanCwrongwords();
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

					}

					if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

						wordTextView4.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						myapp.playmusic(0);
						stopshape();
						if (!timergreencontrol) {
						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);
						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);
						}
						defrepeat(1); // ���������˼�1
						Intent intent = new Intent(wordsl8.this, wordsl8.class);
						startActivity(intent);
						myapp.setscore(0, clicknum + 1);
						timer.cancel();					
						finish();
						}
					}

				}
			});
		}

		if (Integer.parseInt(myapp.get(6)) == 2) {
			myapp.greentoast();
			wordTextView1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					wordTextView1.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String key = (String) wordTextView1.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (key.equals(words[wordnum - 1][0]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][0])) { // ������
							defrepeat(0);
							wordTextView1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(wordsl8.this,
											definitionl8.class);
									startActivity(intent);

								} else {
									Intent intent = new Intent(wordsl8.this,
											wordsl8.class);
									startActivity(intent);
								}
								finish();
							}

							if (words[wordnum][0].equals("")) { // ѭ��������

								Intent intent = new Intent(wordsl8.this,
										scorel8.class);
								myapp.cleanwrongwords();
								startActivity(intent);
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

							wordTextView1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}
					}

					if (wcon == 1) {

						if (key.equals(words[wordnum - 1][0])) { // ������
							defrepeat(0);
							wordTextView1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(wordsl8.this,
											definitionl8.class);
									startActivity(intent);

								} else {
									Intent intent = new Intent(wordsl8.this,
											wordsl8.class);
									startActivity(intent);
								}
								finish();

							}

							if (words[wordnum][0].equals("")) { // ѭ��������

								Intent intent = new Intent(wordsl8.this,
										scorel8.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

							wordTextView1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

				}
			});

			wordTextView2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					wordTextView2.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String key = (String) wordTextView2.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (key.equals(words[wordnum - 1][0]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][0])) { // ������
							defrepeat(0);
							wordTextView2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(wordsl8.this,
											definitionl8.class);
									startActivity(intent);

								} else {
									Intent intent = new Intent(wordsl8.this,
											wordsl8.class);
									startActivity(intent);
								}
								finish();
							}

							if (words[wordnum][0].equals("")) { // ѭ��������

								Intent intent = new Intent(wordsl8.this,
										scorel8.class);
								myapp.cleanwrongwords();
								startActivity(intent);
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

							wordTextView2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}
					}

					if (wcon == 1) {

						if (key.equals(words[wordnum - 1][0])) { // ������
							defrepeat(0);
							wordTextView2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(wordsl8.this,
											definitionl8.class);
									startActivity(intent);

								} else {
									Intent intent = new Intent(wordsl8.this,
											wordsl8.class);
									startActivity(intent);
								}
								finish();

							}

							if (words[wordnum][0].equals("")) { // ѭ��������

								Intent intent = new Intent(wordsl8.this,
										scorel8.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

							wordTextView2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

				}
			});
			wordTextView3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					wordTextView3.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String key = (String) wordTextView3.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (key.equals(words[wordnum - 1][0]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][0])) { // ������
							defrepeat(0);
							wordTextView3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(wordsl8.this,
											definitionl8.class);
									startActivity(intent);

								} else {
									Intent intent = new Intent(wordsl8.this,
											wordsl8.class);
									startActivity(intent);
								}
								finish();
							}

							if (words[wordnum][0].equals("")) { // ѭ��������

								Intent intent = new Intent(wordsl8.this,
										scorel8.class);
								myapp.cleanwrongwords();
								startActivity(intent);
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

							wordTextView3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}
					}

					if (wcon == 1) {

						if (key.equals(words[wordnum - 1][0])) { // ������
							defrepeat(0);
							wordTextView3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(wordsl8.this,
											definitionl8.class);
									startActivity(intent);

								} else {
									Intent intent = new Intent(wordsl8.this,
											wordsl8.class);
									startActivity(intent);
								}
								finish();

							}

							if (words[wordnum][0].equals("")) { // ѭ��������

								Intent intent = new Intent(wordsl8.this,
										scorel8.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

							wordTextView3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

				}
			});

			wordTextView4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					wordTextView4.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String key = (String) wordTextView4.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (key.equals(words[wordnum - 1][0]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][0])) { // ������
							defrepeat(0);
							wordTextView4
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(wordsl8.this,
											definitionl8.class);
									startActivity(intent);

								} else {
									Intent intent = new Intent(wordsl8.this,
											wordsl8.class);
									startActivity(intent);
								}
								finish();
							}

							if (words[wordnum][0].equals("")) { // ѭ��������

								Intent intent = new Intent(wordsl8.this,
										scorel8.class);
								myapp.cleanwrongwords();
								startActivity(intent);
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

							wordTextView4.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}
					}

					if (wcon == 1) {

						if (key.equals(words[wordnum - 1][0])) { // ������
							defrepeat(0);
							wordTextView4
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								double h = Math.random();

								if (h < 0.5) {
									Intent intent = new Intent(wordsl8.this,
											definitionl8.class);
									startActivity(intent);

								} else {
									Intent intent = new Intent(wordsl8.this,
											wordsl8.class);
									startActivity(intent);
								}
								finish();

							}

							if (words[wordnum][0].equals("")) { // ѭ��������

								Intent intent = new Intent(wordsl8.this,
										scorel8.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // ѭ����������

							wordTextView4.setBackgroundResource(R.drawable.red);
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

				if (myapp.gethelpcontrol(4) == 0) {
					myapp.sethelpcontrol(4, 1);
				}

				alertdDialog = new AlertDialog.Builder(wordsl8.this)
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
		numroot = 0; // ��ֹ �����ֲ���20 ���ֵ����

		this.getroots();

		/* if (wcon == 0) { */
		for (int i = 0; i < 20; i++) {

			if (!roots[i].equals("")) { // ��ѭ������
				numroot = i + 1;
			}
		}

		double h = Math.random() * 4; // ���Ϊ0��1֮�䣬 ������4����ť������ѡ4

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
		int a1, a2, a3, a4, a5, a6;

		a1 = 0;
		a2 = 0;
		a3 = 0;
		a4 = 0;
		/*
		 * a5 = 0; a6 = 0;
		 */

		for (int i = 0; i < numroot; i++) {

			if (words[wordnum - 1][0].equals(roots[i])) {
				a1 = i;
				break;
			}
		}

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

		h = Math.random() * numroot;
		a4 = (int) h;
		while (a1 == a4 || a2 == a4 || a3 == a4) {
			h = Math.random() * numroot;
			a4 = (int) h;
		}

		if (key == 1) {
			wordTextView1.setText(words[wordnum - 1][0]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				wordTextView1.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);myapp.setscore(1, rightnum+1);;
				// //playmusic(1);
				// defrepeat(0); ��Ϊ�Զ���ת���Բ�����Ҫ������0��

			}
			wordTextView2.setText(roots[a2]);
			wordTextView3.setText(roots[a3]);
			wordTextView4.setText(roots[a4]);
		}

		if (key == 2) {
			wordTextView2.setText(words[wordnum - 1][0]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				wordTextView2.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);myapp.setscore(1, rightnum+1);;
				// //playmusic(1);
				// defrepeat(0);
			}

			wordTextView1.setText(roots[a3]);
			wordTextView3.setText(roots[a2]);
			wordTextView4.setText(roots[a4]);
		}

		if (key == 3) {
			wordTextView3.setText(words[wordnum - 1][0]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				wordTextView3.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);myapp.setscore(1, rightnum+1);;
				// //playmusic(1);
				// defrepeat(0);
			}

			wordTextView1.setText(roots[a4]);
			wordTextView2.setText(roots[a3]);
			wordTextView4.setText(roots[a2]);
		}
		if (key == 4) {
			wordTextView4.setText(words[wordnum - 1][0]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				wordTextView4.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);myapp.setscore(1, rightnum+1);;
				// //playmusic(1);
				// defrepeat(0);
			}

			wordTextView1.setText(roots[a2]);
			wordTextView2.setText(roots[a4]);
			wordTextView3.setText(roots[a3]);
		}

	}

	private void defrepeat(int key) { // ������ ���2�εĿ��Ʒ���

		if (key == 0) {
			myapp.set(6, Integer.toString(0));
			timergreencontrol = true;
		}

		if (key == 1) {
			myapp.set(6, Integer.toString((Integer.parseInt(myapp.get(6))) + 1));

		}

	}

	private String[] getroots() {

		String[][] words;

		words = myapp.getwords();

		roots = new String[60];

		for (int i = 0; i < 60; i++) {
			roots[i] = "";

		}

		int k = 0;

		for (int i = 0; i < 20; i++) {

			if (!words[i][0].equals("")) {
				roots[k] = words[i][0];
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
		System.out.println("Start");
		EasyTracker.getInstance(this).activityStart(this);

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
		System.out.println("Stop");
		EasyTracker.getInstance(this).activityStop(this);

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
						stopshape(); // home key�����
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
						// long home key�����
					}
				
					if (reason.equals(SYSTEM_HOME_KEY)) {

						timer.cancel();
						stopshape(); // home key�����
						if (timergreencontrol) {
							timergreen.cancel();
						}

					} else if (reason.equals(SYSTEM_RECENT_APPS)) {
						timer.cancel();
						stopshape();
						if (timergreencontrol) {
							timergreen.cancel();
						}
						// long home key�����
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

									Intent intent = new Intent(wordsl8.this,
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
			Intent intent = new Intent(wordsl8.this, play.class);
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
			Intent intent = new Intent(wordsl8.this, list.class);
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
			Intent intent = new Intent(wordsl8.this, listselectactivity.class);
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
			Intent intent = new Intent(wordsl8.this, MainActivity.class);
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
			//textViewscore.setBackgroundColor(Color.GREEN);
			textViewscore.setBackgroundResource(R.drawable.txtvwotlngreen);
		}
		if (key<=64) {
			textViewscore.setTextColor(Color.WHITE);
			//textViewscore.setBackgroundColor(Color.RED);
			textViewscore.setBackgroundResource(R.drawable.txtvwotlnred);
		}
		if (key>64&&key<86) 
			
	 {
			textViewscore.setTextColor(Color.WHITE);
			//textViewscore.setBackgroundColor(Color.YELLOW);
			textViewscore.setBackgroundResource(R.drawable.txtvwotlnyellow);
		}
	}
	
}
