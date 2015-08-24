package level2;

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
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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

public class rootl2 extends AppCompatActivity {

	private Dialog alertdDialog;
	private TextView textView2, rootTextView;
	private TextView rootdef1, rootdef2, rootdef3, rootdef4, rootdef5,
			rootdef6;
	private TextView textViewlevel, textViewword, textViewwr, textViewscore;
	private mypublicvalue myapp;
	private String[][] words;
	private int wordnum;
	private int rootnum;
	private String[] roots;
	private int numroot;
	private ImageButton wenhaoButton;
	public int wcon, con, lv1;
	private double clicknum, rightnum;
	private BroadcastReceiver receiver; // home key
	private double rootclicknum, rootrightnum;
	private TextView worddefview;

	private boolean p1 = false; // �ٿ�help button�Ŀ��Ʒ�

	public  long sleeptime;
	private Timer timergreen;
	private Intent intent;
	private boolean timergreencontrol = false;
	private Matrix matrix = new Matrix();
	private Animation mAnimationRight;
	private CountDownTimer helpshape;

	CountDownTimer timerhelp = new CountDownTimer(5000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFinish() {

			p1 = true;

			helpshape = new CountDownTimer(500, 100) {

				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub

					int k = (int) (millisUntilFinished / 100);

					Bitmap bitmap = ((BitmapDrawable) (getResources()
							.getDrawable(R.drawable.wenhaored))).getBitmap();

					if (k / 2 == 1) {
						matrix.setRotate(10f);
					}
					if (k / 2 == 0) {
						matrix.setRotate(-10f);
					}

					bitmap = Bitmap
							.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
									bitmap.getHeight(), matrix, true);
					wenhaoButton.setImageBitmap(bitmap);

				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					helpshape.start();

				}
			};

			helpshape.start();

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myapp = (mypublicvalue) getApplication();

		android.support.v7.app.ActionBar ab = getSupportActionBar();
		ab.setTitle(underlineclear(myapp.get(0)));

		EasyTracker.getInstance(this).activityStart(this);
		setContentView(R.layout.zroot);
		receiver = new HomeKeyEventBroadCastReceiver();
		getApplicationContext().registerReceiver(receiver,
				new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

		sleeptime= Long.parseLong(this.getString(R.string.sleeptime));
		wenhaoButton = (ImageButton) this.findViewById(R.id.wenhaobutton);

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

		textView2.setText(myapp.get(1));

		textViewlevel.setText(" Level: " + myapp.get(3)); // �趨��ʾlevel�Ŀؼ�

		wordnum = Integer.parseInt(myapp.get(4));

		clicknum = myapp.getscore(0);
		rightnum = myapp.getscore(1);

		rootclicknum = myapp.getrootscore(0);
		rootrightnum = myapp.getrootscore(1);

		wcon = myapp.getreviewwrongcontrol(); // ��ϰ���Ʒ�ֵ
		con = myapp.getrepeatcontrol();

		lv1 = Integer.parseInt(myapp.get(8));

		if (wcon == 0) { // ��׼����� words ȡ��

			if (lv1 == 0) {
				words = myapp.getwords();

			}

			if (lv1 == 1) {

				words = myapp.getwordslv2();

			}
			textViewwr.setText("");
			textViewwr.setBackgroundColor(Color.TRANSPARENT);

			if (con == 0) {
				textViewwr.setText("Part 1");

			}
			if (con == 1) {
				textViewwr.setText("Part 2");
				textViewwr.setTextColor(Color.BLACK);
				textViewwr.setBackgroundColor(Color.WHITE);
			}

		}

		if (wcon == 1) { // ��������£� ȡ�ô��

			words = myapp.getCwrongwords();

			textViewwr.setText("Wrong Review");
			textViewwr.setTextColor(Color.WHITE);
			textViewwr.setBackgroundColor(Color.RED);
			textViewwr.setTextSize(20);

		}

		if (wcon == 0) {
			textViewword.setText("Word: " + (wordnum - 10) + " / " + wordnum()); // �趨��ʾ����word�Ŀؼ�
		}
		if (wcon == 1) {
			textViewword.setText("Word: " + wordnum + " / " + wordnum()); // �趨��ʾ����word�Ŀؼ�
		}
		changecolorscore((int) ((myapp.getscore(1) / myapp.getscore(0)) * 100));
		textViewscore.setText(
				+ (int) ((myapp.getscore(1) / myapp.getscore(0)) * 100)
				+ "%");
		/*added by xiaoqian yu, 2014-12-23, start*/
		mAnimationRight = AnimationUtils.loadAnimation(
                rootl2.this, 
                myapp.calculateViewAnimationID(myapp.setPulseTimeInterval(myapp.getscore(1), 
                		                                                  myapp.getscore(0))));
		textViewscore.startAnimation(mAnimationRight);
		myapp.playPulseSound(myapp.setPulseTimeInterval(myapp.getscore(1), 
                myapp.getscore(0)), -1);
		/*added by xiaoqian yu, 2014-12-23, over*/
		worddefview.setText(words[wordnum - 1][1]);
		rootTextView = (TextView) this.findViewById(R.id.roottestview);

		rootnum = Integer.parseInt(myapp.get(5)); // step

		rootTextView.setText(getchangeword());

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
			// timer.start();

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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef1.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);
							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);

								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								System.out.println("wcon" + wcon);
								System.out.println("����worddef����");

								double h = Math.random();

								if (h < 0.5) {

									intent = new Intent(rootl2.this,
											wordsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

								} else {
									intent = new Intent(rootl2.this,
											definitionl2.class);
									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							
							rootdef1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);						
							defrepeat(1);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef1.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);
								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							
							rootdef1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							defrepeat(1);
							myapp.addwrongword(words[wordnum - 1]);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef2.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);
							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);

								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								System.out.println("wcon" + wcon);
								System.out.println("����worddef����");

								double h = Math.random();

								if (h < 0.5) {

									intent = new Intent(rootl2.this,
											wordsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

								} else {
									intent = new Intent(rootl2.this,
											definitionl2.class);
									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							
							rootdef2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);						
							defrepeat(1);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef2.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);
								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							
							rootdef2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							defrepeat(1);
							myapp.addwrongword(words[wordnum - 1]);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef3.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);
							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);

								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								System.out.println("wcon" + wcon);
								System.out.println("����worddef����");

								double h = Math.random();

								if (h < 0.5) {

									intent = new Intent(rootl2.this,
											wordsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

								} else {
									intent = new Intent(rootl2.this,
											definitionl2.class);
									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							
							rootdef3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);						
							defrepeat(1);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef3.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);
								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							
							rootdef3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							defrepeat(1);
							myapp.addwrongword(words[wordnum - 1]);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef4.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);
							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);

								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								System.out.println("wcon" + wcon);
								System.out.println("����worddef����");

								double h = Math.random();

								if (h < 0.5) {

									intent = new Intent(rootl2.this,
											wordsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

								} else {
									intent = new Intent(rootl2.this,
											definitionl2.class);
									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							
							rootdef4.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);						
							defrepeat(1);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef4.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);
								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							
							rootdef4.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							defrepeat(1);
							myapp.addwrongword(words[wordnum - 1]);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef5.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);
							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);

								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								System.out.println("wcon" + wcon);
								System.out.println("����worddef����");

								double h = Math.random();

								if (h < 0.5) {

									intent = new Intent(rootl2.this,
											wordsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

								} else {
									intent = new Intent(rootl2.this,
											definitionl2.class);
									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							
							rootdef5.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);						
							defrepeat(1);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef5.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);
								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							
							rootdef5.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							defrepeat(1);
							myapp.addwrongword(words[wordnum - 1]);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef6.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);
							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);

								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								System.out.println("wcon" + wcon);
								System.out.println("����worddef����");

								double h = Math.random();

								if (h < 0.5) {

									intent = new Intent(rootl2.this,
											wordsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

								} else {
									intent = new Intent(rootl2.this,
											definitionl2.class);
									myapp.setscore(0, clicknum + 1);
									myapp.setrootscore(0, rootclicknum + 1);
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

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							
							rootdef6.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);						
							defrepeat(1);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							defrepeat(0);
							rootdef6.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setrootscore(1, rootrightnum + 1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								intent = new Intent(rootl2.this, rootl2.class);
								myapp.setscore(0, clicknum + 1);
								myapp.setrootscore(0, rootclicknum + 1);
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

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							
							rootdef6.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							if (!timergreencontrol) {
							defrepeat(1);
							myapp.addwrongword(words[wordnum - 1]);
							Intent intent = new Intent(rootl2.this,
									rootl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setrootscore(0, rootclicknum + 1);							
							finish();
							}
						}

					}
				}
			});
		}

		if (Integer.parseInt(myapp.get(6)) >= 2) {
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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����
							defrepeat(0);
							rootdef1.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(rootl2.this,
											wordsl2.class);
									startActivity(intent);

									stopshape();
									finish();

								} else {
									Intent intent = new Intent(rootl2.this,
											definitionl2.class);
									startActivity(intent);

									stopshape();
									finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							rootdef1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // choose right
							defrepeat(0);
							rootdef1.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								// playmusic(1);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								// ����ѡ�����root��û��root�˴����ߵ�ͷ��

								if (wordnum % 5 == 0) { // �����ߵ�5������

									if (myapp.getrepeatcontrol() == 1) { // ��part2��

										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);

											startActivity(intent);

											stopshape();
											finish();

										}

									}

									if (myapp.getrepeatcontrol() == 0) { // in part1

										myapp.set(4,
												Integer.toString(wordnum + 1));
										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����
										myapp.setrepreatcontrol(1);
										Intent intent = new Intent(rootl2.this,
												idroortsl2.class);
										startActivity(intent);

										// playmusic(1);

										stopshape();
										finish();

									}

								}
								if (wordnum % 5 != 0) {

									myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

									if (myapp.getrepeatcontrol() == 1) { // ��part2��
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}

									if (myapp.getrepeatcontrol() == 0) { // ��part1
																			// ��

										myapp.set(4,
												Integer.toString(wordnum + 1));
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							rootdef1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����
							defrepeat(0);
							rootdef2.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(rootl2.this,
											wordsl2.class);
									startActivity(intent);

									stopshape();
									finish();

								} else {
									Intent intent = new Intent(rootl2.this,
											definitionl2.class);
									startActivity(intent);

									stopshape();
									finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							rootdef2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) {
							defrepeat(0);
							rootdef2.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								// playmusic(1);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								// ����ѡ�����root��û��root�˴����ߵ�ͷ��

								if (wordnum % 5 == 0) { // �����ߵ�5������

									if (myapp.getrepeatcontrol() == 1) { // ��part2��

										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);

											startActivity(intent);

											stopshape();
											finish();

										}

									}

									if (myapp.getrepeatcontrol() == 0) { // �жϽ���part2

										myapp.set(4,
												Integer.toString(wordnum + 1));
										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����
										myapp.setrepreatcontrol(1);
										Intent intent = new Intent(rootl2.this,
												idroortsl2.class);
										startActivity(intent);

										// playmusic(1);

										stopshape();
										finish();

									}

								}
								if (wordnum % 5 != 0) {

									myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

									if (myapp.getrepeatcontrol() == 1) { // ��part2��
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}

									if (myapp.getrepeatcontrol() == 0) { // ��part1
																			// ��

										myapp.set(4,
												Integer.toString(wordnum + 1));
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							rootdef2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����
							defrepeat(0);
							rootdef3.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(rootl2.this,
											wordsl2.class);
									startActivity(intent);

									stopshape();
									finish();

								} else {
									Intent intent = new Intent(rootl2.this,
											definitionl2.class);
									startActivity(intent);

									stopshape();
									finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							rootdef3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) {
							defrepeat(0);
							rootdef3.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								// playmusic(1);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								// ����ѡ�����root��û��root�˴����ߵ�ͷ��

								if (wordnum % 5 == 0) { // �����ߵ�5������

									if (myapp.getrepeatcontrol() == 1) { // ��part2��

										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);

											startActivity(intent);

											stopshape();
											finish();

										}

									}

									if (myapp.getrepeatcontrol() == 0) { // �жϽ���part2

										myapp.set(4,
												Integer.toString(wordnum + 1));
										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����
										myapp.setrepreatcontrol(1);
										Intent intent = new Intent(rootl2.this,
												idroortsl2.class);
										startActivity(intent);

										// playmusic(1);

										stopshape();
										finish();

									}

								}
								if (wordnum % 5 != 0) {

									myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

									if (myapp.getrepeatcontrol() == 1) { // ��part2��
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}

									if (myapp.getrepeatcontrol() == 0) { // ��part1
																			// ��

										myapp.set(4,
												Integer.toString(wordnum + 1));
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							rootdef3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����
							defrepeat(0);
							rootdef4.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(rootl2.this,
											wordsl2.class);
									startActivity(intent);

									stopshape();
									finish();

								} else {
									Intent intent = new Intent(rootl2.this,
											definitionl2.class);
									startActivity(intent);

									stopshape();
									finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							rootdef4.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) {
							defrepeat(0);
							rootdef4.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								// playmusic(1);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								// ����ѡ�����root��û��root�˴����ߵ�ͷ��

								if (wordnum % 5 == 0) { // �����ߵ�5������

									if (myapp.getrepeatcontrol() == 1) { // ��part2��

										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);

											startActivity(intent);

											stopshape();
											finish();

										}

									}

									if (myapp.getrepeatcontrol() == 0) { // �жϽ���part2

										myapp.set(4,
												Integer.toString(wordnum + 1));
										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����
										myapp.setrepreatcontrol(1);
										Intent intent = new Intent(rootl2.this,
												idroortsl2.class);
										startActivity(intent);

										// playmusic(1);

										stopshape();
										finish();

									}

								}
								if (wordnum % 5 != 0) {

									myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

									if (myapp.getrepeatcontrol() == 1) { // ��part2��
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}

									if (myapp.getrepeatcontrol() == 0) { // ��part1
																			// ��

										myapp.set(4,
												Integer.toString(wordnum + 1));
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							rootdef4.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����
							defrepeat(0);
							rootdef5.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(rootl2.this,
											wordsl2.class);
									startActivity(intent);

									stopshape();
									finish();

								} else {
									Intent intent = new Intent(rootl2.this,
											definitionl2.class);
									startActivity(intent);

									stopshape();
									finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							rootdef5.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) {
							defrepeat(0);
							rootdef5.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								// playmusic(1);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								// ����ѡ�����root��û��root�˴����ߵ�ͷ��

								if (wordnum % 5 == 0) { // �����ߵ�5������

									if (myapp.getrepeatcontrol() == 1) { // ��part2��

										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);

											startActivity(intent);

											stopshape();
											finish();

										}

									}

									if (myapp.getrepeatcontrol() == 0) { // �жϽ���part2

										myapp.set(4,
												Integer.toString(wordnum + 1));
										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����
										myapp.setrepreatcontrol(1);
										Intent intent = new Intent(rootl2.this,
												idroortsl2.class);
										startActivity(intent);

										// playmusic(1);

										stopshape();
										finish();

									}

								}
								if (wordnum % 5 != 0) {

									myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

									if (myapp.getrepeatcontrol() == 1) { // ��part2��
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}

									if (myapp.getrepeatcontrol() == 0) { // ��part1
																			// ��

										myapp.set(4,
												Integer.toString(wordnum + 1));
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							rootdef5.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

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
					if (k.equals(words[wordnum - 1][rootnum + 1]))
					{
						myapp.judgeAndCalculateConstantCorrectCount();
						rootdef1.setEnabled(false);
						rootdef2.setEnabled(false);
						rootdef3.setEnabled(false);
						rootdef4.setEnabled(false);
						rootdef5.setEnabled(false);
						rootdef6.setEnabled(false);
					}
					else
					{
						myapp.judgeAndCalculateConstantErrorCount();
					}
					/*added by xiaoqian yu, 2014-12-22, over*/
					// timer.cancel();
					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����
							defrepeat(0);
							rootdef6.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							// ��ɫ

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(rootl2.this,
											wordsl2.class);
									startActivity(intent);

									stopshape();
									finish();

								} else {
									Intent intent = new Intent(rootl2.this,
											definitionl2.class);
									startActivity(intent);

									stopshape();
									finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) { // ѡ����

							rootdef6.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) {
							defrepeat(0);
							rootdef6.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(rootl2.this,
										rootl2.class);

								startActivity(intent);

								// playmusic(1);

								stopshape();
								finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								// ����ѡ�����root��û��root�˴����ߵ�ͷ��

								if (wordnum % 5 == 0) { // �����ߵ�5������

									if (myapp.getrepeatcontrol() == 1) { // ��part2��

										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);

											startActivity(intent);

											stopshape();
											finish();

										}

									}

									if (myapp.getrepeatcontrol() == 0) { // �жϽ���part2

										myapp.set(4,
												Integer.toString(wordnum + 1));
										myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����
										myapp.setrepreatcontrol(1);
										Intent intent = new Intent(rootl2.this,
												idroortsl2.class);
										startActivity(intent);

										// playmusic(1);

										stopshape();
										finish();

									}

								}
								if (wordnum % 5 != 0) {

									myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

									if (myapp.getrepeatcontrol() == 1) { // ��part2��
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}

									if (myapp.getrepeatcontrol() == 0) { // ��part1
																			// ��

										myapp.set(4,
												Integer.toString(wordnum + 1));
										double h = Math.random();

										if (h < 0.5) {
											Intent intent = new Intent(
													rootl2.this, wordsl2.class);
											startActivity(intent);

											// playmusic(1);

											stopshape();
											finish();
										} else {
											Intent intent = new Intent(
													rootl2.this,
													definitionl2.class);
											startActivity(intent);

											stopshape();
											finish();
										}

									}
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							rootdef6.setBackgroundResource(R.drawable.red);
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
				stopshape();
				if (myapp.gethelpcontrol(3) == 0) {
					myapp.sethelpcontrol(3, 1);
				}

				alertdDialog = new AlertDialog.Builder(rootl2.this)
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

	public void repeattt5() {
		// ����ѡ�����root��û��root�˴����ߵ�ͷ��

		if (wordnum % 5 == 0) { // �����ߵ�5������

			if (myapp.getrepeatcontrol() == 1) { // ��part2��

				myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

				double h = Math.random();

				if (h < 0.5) {
					intent = new Intent(rootl2.this, wordsl2.class);

					myapp.setscore(0, clicknum + 1);
					myapp.setrootscore(0, rootclicknum + 1);
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
				} else {
					intent = new Intent(rootl2.this, definitionl2.class);

					myapp.setscore(0, clicknum + 1);
					myapp.setrootscore(0, rootclicknum + 1);
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

			if (myapp.getrepeatcontrol() == 0) { // ��part1��

				myapp.set(4, Integer.toString(wordnum + 1));
				myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����
				myapp.setrepreatcontrol(1);
				intent = new Intent(rootl2.this, idroortsl2.class);

				myapp.setscore(0, clicknum + 1);
				myapp.setrootscore(0, rootclicknum + 1);
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
		if (wordnum % 5 != 0) {

			myapp.set(5, Integer.toString(2)); // rootû���ˣ���Ҫ����

			if (myapp.getrepeatcontrol() == 1) { // ��part2��
				double h = Math.random();

				if (h < 0.5) {
					intent = new Intent(rootl2.this, wordsl2.class);

					myapp.setscore(0, clicknum + 1);
					myapp.setrootscore(0, rootclicknum + 1);
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
				} else {
					intent = new Intent(rootl2.this, definitionl2.class);

					myapp.setscore(0, clicknum + 1);
					myapp.setrootscore(0, rootclicknum + 1);
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

			if (myapp.getrepeatcontrol() == 0) { // ��part1 ��

				myapp.set(4, Integer.toString(wordnum + 1));
				double h = Math.random();

				if (h < 0.5) {
					intent = new Intent(rootl2.this, wordsl2.class);

					myapp.setscore(0, clicknum + 1);
					myapp.setrootscore(0, rootclicknum + 1);
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
				} else {
					intent = new Intent(rootl2.this, definitionl2.class);

					myapp.setscore(0, clicknum + 1);
					myapp.setrootscore(0, rootclicknum + 1);
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
		}

	}

	private SpannableStringBuilder getchangeword() { // �������Զ��������Ӧ��ȱʧroot���ַ���
		// TODO Auto-generated method stub
		// ��ʼroot�ĵص�
		String word = words[wordnum - 1][0];

		String changeword = words[wordnum - 1][rootnum];

		int start = word.indexOf(changeword);

		int end = start + changeword.length();

		SpannableStringBuilder style = new SpannableStringBuilder(word);

		style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.root)), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		return style;
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
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		myapp.startlevelmusic();

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
		EasyTracker.getInstance(this).activityStop(this);

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
						myapp.pauselevelmusic(); // home key�����

					} else if (reason.equals(SYSTEM_RECENT_APPS)) {
						myapp.pauselevelmusic();// long home key�����
					}
				}
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// timer.cancel();
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

									Intent intent = new Intent(rootl2.this,
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
									// TODO Auto-generated method stub

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

															startActivity(intent);
															finish();
														}
													}, sleeptime);
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
			stopshape();
			if (timergreencontrol) {
				timergreen.cancel();
			}
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(rootl2.this, play.class);
			startActivity(intent);
			finish();
		}

		
		if (id == R.id.PlayStudyReview) {
			stopshape();
			if (timergreencontrol) {
				timergreen.cancel();
			}
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(rootl2.this, list.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.listpage) {
			stopshape();
			if (timergreencontrol) {
				timergreen.cancel();
			}
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(rootl2.this, listselectactivity.class);
			startActivity(intent);
			finish();
		}

		if (id == R.id.coursepage) {
			stopshape();
			if (timergreencontrol) {
				timergreen.cancel();
			}
			myapp.stoplevelmusic();
			myapp.empty();
			Intent intent = new Intent(rootl2.this, MainActivity.class);
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