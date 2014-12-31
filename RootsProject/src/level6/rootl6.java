package level6;

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

public class rootl6 extends Activity {

	private Dialog alertdDialog;
	private TextView textView1, textView2, rootTextView;
	private TextView rootdef1, rootdef2, rootdef3, rootdef4, rootdef5,
			rootdef6;
	private TextView textViewlevel, textViewword, textViewwr, textViewscore;
	private mypublicvalue myapp;
	private String[][] words;
	private int wordnum;
	private int rootnum;
	private String[] roots;
	private String[][] rootword;
	private int numroot;
	private TextView worddefview;
	public int wcon, con, lv1;
	private double clicknum, rightnum;
	private ImageButton wenhaoButton;
	private boolean p1 = false; // �ٿ�help button�Ŀ��Ʒ�

	public  long sleeptime = 2000;
	private Timer timergreen;

	private boolean timergreencontrol = false;

	private Intent intent;

	private Matrix matrix = new Matrix();
	private Animation mAnimationRight;
	private BroadcastReceiver receiver;
	private CountDownTimer helpshape;
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
			
			myapp.judgeAndCalculateConstantErrorCount();
			myapp.toastImage();
			
			if (Integer.parseInt(myapp.get(6)) == 1) {

				myapp.playmusic(3); // ʧ������
			}

			Intent intent = new Intent(rootl6.this, rootl6.class);
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
		EasyTracker.getInstance(this).activityStart(this);

		setContentView(R.layout.zroot);
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

		rootdef1 = (TextView) this.findViewById(R.id.rootdef1);
		rootdef2 = (TextView) this.findViewById(R.id.rootdef2);
		rootdef3 = (TextView) this.findViewById(R.id.rootdef3);
		rootdef4 = (TextView) this.findViewById(R.id.rootdef4);
		rootdef5 = (TextView) this.findViewById(R.id.rootdef5);
		rootdef6 = (TextView) this.findViewById(R.id.rootdef6);
		worddefview = (TextView) this.findViewById(R.id.worddefview);
		myapp = (mypublicvalue) getApplication();
		myapp.startlevelmusic();
		textView1.setText(underlineclear(myapp.get(0)));
		textView2.setText(myapp.get(1));

		textViewlevel.setText(" Level: " + myapp.get(3)); // �趨��ʾlevel�Ŀؼ�

		wordnum = Integer.parseInt(myapp.get(4));

		clicknum = myapp.getscore(0);
		rightnum = myapp.getscore(1);

		wcon = myapp.getreviewwrongcontrol(); // ��ϰ���Ʒ�ֵ
		con = myapp.getrepeatcontrol();

		lv1 = Integer.parseInt(myapp.get(8));

		if (wcon == 0) { // ��׼����� words ȡ��

			if (lv1 == 0) {

				myapp.setrootword(getwordroot());
				myapp.cleanrootwords();
				words = myapp.getrootword();
				myapp.set(8, Integer.toString(1));
				textViewscore.setText( 0 * 100 + "%");

			}

			if (lv1 == 1) {
				
				words = myapp.getrootword();

			}

		}

		if (wcon == 1) { // ��������£� ȡ�ô��
			/* lwords = myapp.getwords(); */
			words = myapp.getCwrongwords();
			textViewwr.setText("Wrong Review");
			textViewwr.setBackgroundColor(Color.RED);
			textViewwr.setTextColor(Color.WHITE);

			

		}
		changecolorscore((int) ((myapp.getscore(1) / myapp.getscore(0)) * 100));
		textViewscore.setText(
				(int) ((myapp.getscore(1) / myapp.getscore(0)) * 100)
				+ "%");
		/*added by xiaoqian yu, 2014-12-23, start*/
		mAnimationRight = AnimationUtils.loadAnimation(
                rootl6.this, 
                myapp.calculateViewAnimationID(myapp.setPulseTimeInterval(myapp.getscore(1), 
                		                                                  myapp.getscore(0))));
		textViewscore.startAnimation(mAnimationRight);
		myapp.playPulseSound(myapp.setPulseTimeInterval(myapp.getscore(1), 
                myapp.getscore(0)), -1);
		/*added by xiaoqian yu, 2014-12-23, over*/
		textViewword.setText("Word: " + wordnum + " / " + wordnum()); // �趨��ʾ����word�Ŀؼ�
		worddefview.setVisibility(View.GONE);

		rootTextView = (TextView) this.findViewById(R.id.roottestview);

		/*
		 * rootnum = Integer.parseInt(myapp.get(5)); // step
		 */
		rootTextView.setText(words[wordnum - 1][0]);

		System.out.println("wcon" + wcon);

		this.ran();

		if (myapp.gethelpcontrol(3) == 0) {
			//comment this line, to Take out the shaking of the question mark
			//timerhelp.start();
		}
		/*added by xiaoqian yu, 2014-12-31, start*/
		rootdef1.setEnabled(true);
		rootdef2.setEnabled(true);
		rootdef3.setEnabled(true);
		rootdef4.setEnabled(true);
		rootdef5.setEnabled(true);
		rootdef6.setEnabled(true);
		/*added by xiaoqian yu, 2014-12-31, over*/
		if (Integer.parseInt(myapp.get(6)) < 2) {

			textViewwr.setText( 15 + "'s");
			timer.start();

			rootdef1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef1.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef1.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (k.equals(words[wordnum - 1][1])) { // ѡ����

						defrepeat(0);
						rootdef1.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						myapp.setscore(1, rightnum + 1);
						// ��ɫ

						if (!words[wordnum][0].equals("")) {

							myapp.set(4, Integer.toString(wordnum + 1));

							intent = new Intent(rootl6.this, rootl6.class);

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

						if (words[wordnum][0].equals("")) {

							myapp.cleanwrongwords();

							intent = new Intent(rootl6.this, scorel6.class);
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

					if (!k.equals(words[wordnum - 1][1])) { // ѡ����

						
						rootdef1.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						myapp.playmusic(0);
						stopshape();
						if (!timergreencontrol) {
						defrepeat(1);
						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);
						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);
						}
						Intent intent = new Intent(rootl6.this, rootl6.class);
						startActivity(intent);
						myapp.setscore(0, clicknum + 1);
						timer.cancel();						
						finish();
						}
					}

				}

			});

			rootdef2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef2.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef2.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (k.equals(words[wordnum - 1][1])) { // ѡ����

						defrepeat(0);
						rootdef2.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						myapp.setscore(1, rightnum + 1);
						// ��ɫ

						if (!words[wordnum][0].equals("")) {

							myapp.set(4, Integer.toString(wordnum + 1));

							intent = new Intent(rootl6.this, rootl6.class);

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

						if (words[wordnum][0].equals("")) {

							myapp.cleanwrongwords();

							intent = new Intent(rootl6.this, scorel6.class);
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

					if (!k.equals(words[wordnum - 1][1])) { // ѡ����

						
						rootdef2.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						myapp.playmusic(0);
						stopshape();
						if (!timergreencontrol) {
						defrepeat(1);
						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);
						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);
						}
						Intent intent = new Intent(rootl6.this, rootl6.class);
						startActivity(intent);
						myapp.setscore(0, clicknum + 1);
						timer.cancel();						
						finish();
						}
					}

				}

			});

			rootdef3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef3.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef3.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (k.equals(words[wordnum - 1][1])) { // ѡ����

						defrepeat(0);
						rootdef3.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						myapp.setscore(1, rightnum + 1);
						// ��ɫ

						if (!words[wordnum][0].equals("")) {

							myapp.set(4, Integer.toString(wordnum + 1));

							intent = new Intent(rootl6.this, rootl6.class);

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

						if (words[wordnum][0].equals("")) {

							myapp.cleanwrongwords();

							intent = new Intent(rootl6.this, scorel6.class);
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

					if (!k.equals(words[wordnum - 1][1])) { // ѡ����

						
						rootdef3.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						myapp.playmusic(0);
						stopshape();
						if (!timergreencontrol) {
						defrepeat(1);
						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);
						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);
						}
						Intent intent = new Intent(rootl6.this, rootl6.class);
						startActivity(intent);
						myapp.setscore(0, clicknum + 1);
						timer.cancel();						
						finish();
						}
					}

				}

			});

			rootdef4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef4.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef4.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (k.equals(words[wordnum - 1][1])) { // ѡ����

						defrepeat(0);
						rootdef4.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						myapp.setscore(1, rightnum + 1);
						// ��ɫ

						if (!words[wordnum][0].equals("")) {

							myapp.set(4, Integer.toString(wordnum + 1));

							intent = new Intent(rootl6.this, rootl6.class);

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

						if (words[wordnum][0].equals("")) {

							myapp.cleanwrongwords();

							intent = new Intent(rootl6.this, scorel6.class);
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

					if (!k.equals(words[wordnum - 1][1])) { // ѡ����

						
						rootdef4.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						myapp.playmusic(0);
						stopshape();
						if (!timergreencontrol) {
						defrepeat(1);
						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);
						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);
						}
						Intent intent = new Intent(rootl6.this, rootl6.class);
						startActivity(intent);
						myapp.setscore(0, clicknum + 1);
						timer.cancel();						
						finish();
						}
					}

				}

			});

			rootdef5.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef5.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef5.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (k.equals(words[wordnum - 1][1])) { // ѡ����

						defrepeat(0);
						rootdef5.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						myapp.setscore(1, rightnum + 1);
						// ��ɫ

						if (!words[wordnum][0].equals("")) {

							myapp.set(4, Integer.toString(wordnum + 1));

							intent = new Intent(rootl6.this, rootl6.class);

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

						if (words[wordnum][0].equals("")) {

							myapp.cleanwrongwords();

							intent = new Intent(rootl6.this, scorel6.class);
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

					if (!k.equals(words[wordnum - 1][1])) { // ѡ����

						
						rootdef5.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						myapp.playmusic(0);
						stopshape();
						if (!timergreencontrol) {
						defrepeat(1);
						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);
						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);
						}
						Intent intent = new Intent(rootl6.this, rootl6.class);
						startActivity(intent);
						myapp.setscore(0, clicknum + 1);
						timer.cancel();						
						finish();
						}
					}

				}

			});

			rootdef6.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef6.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef6.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (k.equals(words[wordnum - 1][1])) { // ѡ����

						defrepeat(0);
						rootdef6.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						myapp.setscore(1, rightnum + 1);
						// ��ɫ

						if (!words[wordnum][0].equals("")) {

							myapp.set(4, Integer.toString(wordnum + 1));

							intent = new Intent(rootl6.this, rootl6.class);

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

						if (words[wordnum][0].equals("")) {

							myapp.cleanwrongwords();

							intent = new Intent(rootl6.this, scorel6.class);
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

					if (!k.equals(words[wordnum - 1][1])) { // ѡ����

						
						rootdef6.setBackgroundResource(R.drawable.red);
						myapp.Vibrate();
						myapp.playmusic(0);
						stopshape();
						if (!timergreencontrol) {
						defrepeat(1);
						if (wcon == 0) {
							myapp.addwrongword(words[wordnum - 1]);
						}
						if (wcon == 1) {
							myapp.addwrongwords1(words[wordnum - 1]);
						}
						Intent intent = new Intent(rootl6.this, rootl6.class);
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
			rootdef1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef1.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef1.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef1.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef1.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanCwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

							// playmusic(0);

						}

					}

				}

			});

			rootdef2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef2.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef2.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef2.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef2.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanCwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

							// playmusic(0);

						}

					}

				}

			});

			rootdef3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef3.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef3.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef3.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef3.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanCwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

							// playmusic(0);

						}

					}

				}

			});

			rootdef4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef4.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef4.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef4.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef4.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef4.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanCwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef4.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

							// playmusic(0);

						}

					}

				}

			});

			rootdef5.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef5.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef5.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef5.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef5.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef5.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanCwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef5.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

							// playmusic(0);

						}

					}

				}

			});

			rootdef6.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/*added by xiaoqian yu, 2014-12-31, start*/
					/*prevent multi click*/
					rootdef6.setEnabled(false);
					/*added by xiaoqian yu, 2014-12-31, over*/
					String k = (String) rootdef6.getText();
					/*added by xiaoqian yu, 2014-12-22, start*/
					if (k.equals(words[wordnum - 1][1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef6.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef6.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][1])) { // ѡ����
							defrepeat(0);

							rootdef6.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));

								Intent intent = new Intent(rootl6.this,
										rootl6.class);

								startActivity(intent);
								// playmusic(1);

								finish();
							}

							if (words[wordnum][0].equals("")) {

								myapp.cleanCwrongwords();

								Intent intent = new Intent(rootl6.this,
										scorel6.class);
								startActivity(intent);
								// playmusic(1);

								finish();

							}

						}

						if (!k.equals(words[wordnum - 1][1])) { // ѡ����

							rootdef6.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

							// playmusic(0);

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

				if (myapp.gethelpcontrol(3) == 0) {
					myapp.sethelpcontrol(3, 1);
				}
				alertdDialog = new AlertDialog.Builder(rootl6.this)
						.setTitle("Instruction")
						.setMessage(getString(R.string.roothelp))
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
		/*
		 * adapter = new ArrayAdapter<>(this,
		 * android.R.layout.simple_list_item_single_choice, list);
		 */

	}

	private String[][] getwordroot() {

		String[][] words;

		words = myapp.getwords();

		rootword = new String[80][10];

		for (int i = 0; i < rootword.length; i++) {
			for (int j = 0; j < rootword[0].length; j++) {
				rootword[i][j] = "";
			}

		}

		int k = 0;

		for (int i = 0; i < 20; i++) {

			if (!words[i][3].equals("")) {
				rootword[k][0] = words[i][2];
				rootword[k][1] = words[i][3];
				k++;
			}

		}
		for (int i = 0; i < 20; i++) {

			if (!words[i][5].equals("")) {
				rootword[k][0] = words[i][4];
				rootword[k][1] = words[i][5];
				k++;
			}

		}

		for (int i = 0; i < 20; i++) {

			if (!words[i][7].equals("")) {
				rootword[k][0] = words[i][6];
				rootword[k][1] = words[i][7];
				k++;
			}

		}

		for (int i = 0; i < 20; i++) {

			if (!words[i][9].equals("")) {
				rootword[k][0] = words[i][8];
				rootword[k][1] = words[i][9];
				k++;
			}

		}

		return rootword;
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

			if (!words[i][3].equals("")) {
				roots[k] = words[i][3];
				k++;
			}

		}
		for (int i = 0; i < 20; i++) {

			if (!words[i][5].equals("")) {
				roots[k] = words[i][5];
				k++;
			}

		}

		for (int i = 0; i < 20; i++) {

			if (!words[i][7].equals("")) {
				roots[k] = words[i][7];
				k++;
			}

		}

		for (int i = 0; i < 20; i++) {

			if (!words[i][9].equals("")) {
				roots[k] = words[i][9];
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

	private void method(int key) {

		int a1, a2, a3, a4, a5, a6;
		double h;
		a1 = 0;
		a2 = 0;
		a3 = 0;
		a4 = 0;
		a5 = 0;
		a6 = 0;

		System.out.println("���ٸ�root+" + numroot);

		for (int i = 0; i < numroot; i++) {

			if (words[wordnum - 1][rootnum + 1].equals(roots[i])) {
				a1 = i;
				break;
			}
		}

		System.out.println("����ѭ����");

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
				|| roots[a3].equals(roots[a5]) || roots[a4].equals(roots[a5])) {
			h = Math.random() * numroot;
			a5 = (int) h;
		}

		h = Math.random() * numroot;
		a6 = (int) h;
		while (roots[a1].equals(roots[a6]) || roots[a2].equals(roots[a6])
				|| roots[a3].equals(roots[a6]) || roots[a4].equals(roots[a6])
				|| roots[a5].equals(roots[a6])) {
			h = Math.random() * numroot;
			a6 = (int) h;
		}

		if (key == 1) {
			rootdef1.setText(words[wordnum - 1][rootnum + 1]);
			rootdef2.setText(roots[a2]);
			rootdef3.setText(roots[a3]);
			rootdef4.setText(roots[a4]);
			rootdef5.setText(roots[a5]);
			rootdef6.setText(roots[a6]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				rootdef1.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				// defrepeat(0);
			}
		}

		if (key == 2) {
			rootdef2.setText(words[wordnum - 1][rootnum + 1]);
			rootdef3.setText(roots[a3]);
			rootdef4.setText(roots[a4]);
			rootdef5.setText(roots[a5]);
			rootdef6.setText(roots[a6]);
			rootdef1.setText(roots[a2]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				rootdef2.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				// defrepeat(0);
			}
		}

		if (key == 3) {
			rootdef3.setText(words[wordnum - 1][rootnum + 1]);
			rootdef4.setText(roots[a4]);
			rootdef5.setText(roots[a5]);
			rootdef6.setText(roots[a6]);
			rootdef1.setText(roots[a2]);
			rootdef2.setText(roots[a3]);
			if (Integer.parseInt(myapp.get(6)) == 2) {
				rootdef3.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				// defrepeat(0);
			}
		}
		if (key == 4) {
			rootdef4.setText(words[wordnum - 1][rootnum + 1]);
			rootdef5.setText(roots[a5]);
			rootdef6.setText(roots[a6]);
			rootdef1.setText(roots[a2]);
			rootdef2.setText(roots[a3]);
			rootdef3.setText(roots[a4]);
			if (Integer.parseInt(myapp.get(6)) == 2) {
				rootdef4.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				// defrepeat(0);
			}
		}

		if (key == 5) {
			rootdef5.setText(words[wordnum - 1][rootnum + 1]);
			rootdef6.setText(roots[a6]);
			rootdef1.setText(roots[a2]);
			rootdef2.setText(roots[a3]);
			rootdef3.setText(roots[a4]);
			rootdef4.setText(roots[a5]);
			if (Integer.parseInt(myapp.get(6)) == 2) {
				rootdef5.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				// defrepeat(0);
			}
		}

		if (key == 6) {
			rootdef6.setText(words[wordnum - 1][rootnum + 1]);
			rootdef1.setText(roots[a2]);
			rootdef2.setText(roots[a3]);
			rootdef3.setText(roots[a4]);
			rootdef4.setText(roots[a5]);
			rootdef5.setText(roots[a6]);
			if (Integer.parseInt(myapp.get(6)) == 2) {
				rootdef6.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				// defrepeat(0);
			}
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

									Intent intent = new Intent(rootl6.this,
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
			Intent intent = new Intent(rootl6.this, play.class);
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
			Intent intent = new Intent(rootl6.this, list.class);
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
			Intent intent = new Intent(rootl6.this, listselectactivity.class);
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
			Intent intent = new Intent(rootl6.this, MainActivity.class);
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
