package level2;

import java.util.Random;
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

public class wordsl2 extends Activity {

	private Dialog alertdDialog;
	private TextView textView1, textView2, defTextView, wordTextView1,
			wordTextView2, wordTextView3, wordTextView4;
	private TextView textViewlevel, textViewword, textViewwr, textViewscore;
	private mypublicvalue myapp;
	private String[][] words;

	private String[] roots;

	private int wordnum; // 第几个word
	private int numroot;

	public int con; // TT 循环控制阀
	public int wcon; // 复习控制阀
	private BroadcastReceiver receiver; // home key
	private int lv1;
	private ImageButton wenhaoButton;
	private double clicknum, rightnum;
	private double defwordclicknum, defwordrightnum;

	private boolean p1 = false; // 操控help button的控制阀
	private Timer timergreen;
	public long sleeptime;
	private Intent intent;
	private boolean timergreencontrol = false;
	private Matrix matrix = new Matrix();

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
		EasyTracker.getInstance(this).activityStart(this);
		
		setContentView(R.layout.zword);
		System.out.println("Wordsl2.class 启动");
		receiver = new HomeKeyEventBroadCastReceiver();
		getApplicationContext().registerReceiver(receiver,
				new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
		sleeptime= Long.parseLong(this.getString(R.string.sleeptime));
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

		textViewlevel.setText(" Level: " + myapp.get(3)); // 设定显示level的控件

		clicknum = myapp.getscore(0);
		rightnum = myapp.getscore(1);

		defwordclicknum = myapp.getdefwordscore(0);
		defwordrightnum = myapp.getdefwordscore(1);

		wcon = myapp.getreviewwrongcontrol(); // 复习控制阀值

		con = myapp.getrepeatcontrol(); // TT循环控制阀

		lv1 = Integer.parseInt(myapp.get(8));

		if (wcon == 0) { // 标准情况下 words 取用

			if (lv1 == 0) {
				words = myapp.getwords();
				words = get10ranwords(words);
				myapp.setwordslv2(words);

				myapp.set(4, Integer.toString(11));
				myapp.set(8, Integer.toString(1)); // 第一次调用 完毕，置1 表示第二次不在调用
													// 随机get10ranwords（）方法

			}

			if (lv1 == 1) {
			
				words = myapp.getwordslv2();

			}

			textViewwr.setText("");
			textViewwr.setBackgroundColor(Color.TRANSPARENT);

			if (con == 0) {
				textViewwr.setText("Part1");

			}
			if (con == 1) {
				textViewwr.setText("Part2");
				//textViewwr.setBackgroundColor(Color.GREEN);

			}

		}

		if (wcon == 1) { // 错误情况下， 取用错词
			/* lwords = myapp.getwords(); */
			words = myapp.getCwrongwords();
			textViewwr.setText("Wrong Reivew");
			textViewwr.setBackgroundColor(Color.RED);
		

		}
		changecolorscore((int) ((myapp.getscore(1) / myapp.getscore(0)) * 100));
		textViewscore.setText(
				 (int) ((myapp.getscore(1) / myapp.getscore(0)) * 100)
				+ "%");
		wordnum = Integer.parseInt(myapp.get(4));

		if (wcon == 0) {
			textViewword.setText("Word: " + (wordnum - 10) + " / " + wordnum()); // 设定显示几号word的控件
		}
		if (wcon == 1) {
			textViewword.setText("Word: " + wordnum + " / " + wordnum()); // 设定显示几号word的控件
		}

		defTextView.setText(words[wordnum - 1][1]);

		this.ran();

		if (myapp.gethelpcontrol(4) == 0) {
			timerhelp.start();
		}
		if (Integer.parseInt(myapp.get(6)) < 2) {
			// timer.start();

			wordTextView1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) wordTextView1.getText();
					// timer.cancel();
					if (wcon == 1) { // 错词循环下

						if (key.equals(words[wordnum - 1][0])) { // 做对了

							wordTextView1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setdefwordscore(1, defwordrightnum + 1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("错词循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));
								intent = new Intent(wordsl2.this,
										idroortsl2.class);

								myapp.setscore(0, clicknum + 1);
								myapp.setdefwordscore(0, defwordclicknum + 1);
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

								System.out.println("错词循环结束");
								intent = new Intent(wordsl2.this, scorel2.class);

								myapp.cleanCwrongwords();
								myapp.setscore(0, clicknum + 1);
								myapp.setdefwordscore(0, defwordclicknum + 1);
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

						if (!key.equals(words[wordnum - 1][0])) { // 错词循环下做错了

							wordTextView1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);
							defrepeat(1); // 这是做错了加1
							Intent intent = new Intent(wordsl2.this,
									wordsl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setdefwordscore(0, defwordclicknum + 1);							
							finish();
							}
						}

					}

					if (wcon == 0) { // 标准循环下

						if (key.equals(words[wordnum - 1][0])) {// 标准循环下 做对了

							defrepeat(0);
							wordTextView1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setdefwordscore(1, defwordrightnum + 1);

							if (con == 1) { // PART2

								if (wordnum % 5 != 0) { // 没有走完5个单词

									myapp.set(4, Integer.toString(wordnum + 1));
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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

								if (wordnum % 5 == 0) { // 走完5个单词了
									myapp.set(4, Integer.toString(wordnum + 1));
									// myapp.setrepreatcontrol(0); // TT循环到头，
									// 清空控制阀

									if (words[wordnum][0].equals("")) { // 判断结束
										// 整个list的前两个sequence操作结束
										// 进入复习 wrong
										// words 流程。
										myapp.cleanwrongwords(); // 快速算法排序
																	// 清理wrongwords

										myapp.set(4, Integer.toString(1)); // 进入review
																			// wrong
																			// 循环
																			// wordnumber
																			// 给1

										// myapp.setreviewwrongcontrol(1); //
										// 设定进入reviewwrong
										// wcon赋值为1

										intent = new Intent(wordsl2.this,
												scorel2.class);

										myapp.setscore(0, clicknum + 1);
										myapp.setdefwordscore(0,
												defwordclicknum + 1);
										stopshape();
										timergreen = new Timer();
										timergreen.schedule(new TimerTask() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												startActivity(intent);
												finish();
											}
										}, sleeptime);

									}

								}
							}

							if (con == 0) { // part1部分

								if (wordnum % 5 != 0) {
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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
								if (wordnum % 5 == 0) {
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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
						if (!key.equals(words[wordnum - 1][0])) { // 标准循环下做错了

							
							wordTextView1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							
							if (!timergreencontrol) {
							defrepeat(1);
							myapp.addwrongword(words[wordnum - 1]);
							Intent intent = new Intent(wordsl2.this,
									wordsl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setdefwordscore(0, defwordclicknum + 1);							
							finish();
							}
						}

					}
				}
			});

			wordTextView2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) wordTextView2.getText();
					// timer.cancel();
					if (wcon == 1) { // 错词循环下

						if (key.equals(words[wordnum - 1][0])) { // 做对了

							wordTextView2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setdefwordscore(1, defwordrightnum + 1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("错词循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));
								intent = new Intent(wordsl2.this,
										idroortsl2.class);

								myapp.setscore(0, clicknum + 1);
								myapp.setdefwordscore(0, defwordclicknum + 1);
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

								System.out.println("错词循环结束");
								intent = new Intent(wordsl2.this, scorel2.class);

								myapp.cleanCwrongwords();
								myapp.setscore(0, clicknum + 1);
								myapp.setdefwordscore(0, defwordclicknum + 1);
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

						if (!key.equals(words[wordnum - 1][0])) { // 错词循环下做错了

							wordTextView2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);
							defrepeat(1); // 这是做错了加1
							Intent intent = new Intent(wordsl2.this,
									wordsl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setdefwordscore(0, defwordclicknum + 1);							
							finish();
							}
						}

					}

					if (wcon == 0) { // 标准循环下

						if (key.equals(words[wordnum - 1][0])) {// 标准循环下 做对了

							defrepeat(0);
							wordTextView2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setdefwordscore(1, defwordrightnum + 1);

							if (con == 1) { // PART2

								if (wordnum % 5 != 0) { // 没有走完5个单词

									myapp.set(4, Integer.toString(wordnum + 1));
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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

								if (wordnum % 5 == 0) { // 走完5个单词了
									myapp.set(4, Integer.toString(wordnum + 1));
									// myapp.setrepreatcontrol(0); // TT循环到头，
									// 清空控制阀

									if (words[wordnum][0].equals("")) { // 判断结束
										// 整个list的前两个sequence操作结束
										// 进入复习 wrong
										// words 流程。
										myapp.cleanwrongwords(); // 快速算法排序
																	// 清理wrongwords

										myapp.set(4, Integer.toString(1)); // 进入review
																			// wrong
																			// 循环
																			// wordnumber
																			// 给1

										// myapp.setreviewwrongcontrol(1); //
										// 设定进入reviewwrong
										// wcon赋值为1

										intent = new Intent(wordsl2.this,
												scorel2.class);

										myapp.setscore(0, clicknum + 1);
										myapp.setdefwordscore(0,
												defwordclicknum + 1);
										stopshape();
										timergreen = new Timer();
										timergreen.schedule(new TimerTask() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												startActivity(intent);
												finish();
											}
										}, sleeptime);

									}

								}
							}

							if (con == 0) { // part1部分

								if (wordnum % 5 != 0) {
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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
								if (wordnum % 5 == 0) {
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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
						if (!key.equals(words[wordnum - 1][0])) { // 标准循环下做错了

							
							wordTextView2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							
							if (!timergreencontrol) {
							defrepeat(1);
							myapp.addwrongword(words[wordnum - 1]);
							Intent intent = new Intent(wordsl2.this,
									wordsl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setdefwordscore(0, defwordclicknum + 1);							
							finish();
							}
						}

					}
				}
			});
			wordTextView3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) wordTextView3.getText();
					// timer.cancel();
					if (wcon == 1) { // 错词循环下

						if (key.equals(words[wordnum - 1][0])) { // 做对了

							wordTextView3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setdefwordscore(1, defwordrightnum + 1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("错词循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));
								intent = new Intent(wordsl2.this,
										idroortsl2.class);

								myapp.setscore(0, clicknum + 1);
								myapp.setdefwordscore(0, defwordclicknum + 1);
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

								System.out.println("错词循环结束");
								intent = new Intent(wordsl2.this, scorel2.class);

								myapp.cleanCwrongwords();
								myapp.setscore(0, clicknum + 1);
								myapp.setdefwordscore(0, defwordclicknum + 1);
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

						if (!key.equals(words[wordnum - 1][0])) { // 错词循环下做错了

							wordTextView3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);
							defrepeat(1); // 这是做错了加1
							Intent intent = new Intent(wordsl2.this,
									wordsl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setdefwordscore(0, defwordclicknum + 1);							
							finish();
							}
						}

					}

					if (wcon == 0) { // 标准循环下

						if (key.equals(words[wordnum - 1][0])) {// 标准循环下 做对了

							defrepeat(0);
							wordTextView3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setdefwordscore(1, defwordrightnum + 1);

							if (con == 1) { // PART2

								if (wordnum % 5 != 0) { // 没有走完5个单词

									myapp.set(4, Integer.toString(wordnum + 1));
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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

								if (wordnum % 5 == 0) { // 走完5个单词了
									myapp.set(4, Integer.toString(wordnum + 1));
									// myapp.setrepreatcontrol(0); // TT循环到头，
									// 清空控制阀

									if (words[wordnum][0].equals("")) { // 判断结束
										// 整个list的前两个sequence操作结束
										// 进入复习 wrong
										// words 流程。
										myapp.cleanwrongwords(); // 快速算法排序
																	// 清理wrongwords

										myapp.set(4, Integer.toString(1)); // 进入review
																			// wrong
																			// 循环
																			// wordnumber
																			// 给1

										// myapp.setreviewwrongcontrol(1); //
										// 设定进入reviewwrong
										// wcon赋值为1

										intent = new Intent(wordsl2.this,
												scorel2.class);

										myapp.setscore(0, clicknum + 1);
										myapp.setdefwordscore(0,
												defwordclicknum + 1);
										stopshape();
										timergreen = new Timer();
										timergreen.schedule(new TimerTask() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												startActivity(intent);
												finish();
											}
										}, sleeptime);

									}

								}
							}

							if (con == 0) { // part1部分

								if (wordnum % 5 != 0) {
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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
								if (wordnum % 5 == 0) {
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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
						if (!key.equals(words[wordnum - 1][0])) { // 标准循环下做错了

							
							wordTextView3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							
							if (!timergreencontrol) {
							defrepeat(1);
							myapp.addwrongword(words[wordnum - 1]);
							Intent intent = new Intent(wordsl2.this,
									wordsl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setdefwordscore(0, defwordclicknum + 1);							
							finish();
							}
						}

					}
				}
			});

			wordTextView4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) wordTextView4.getText();
					// timer.cancel();
					if (wcon == 1) { // 错词循环下

						if (key.equals(words[wordnum - 1][0])) { // 做对了

							wordTextView4
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setdefwordscore(1, defwordrightnum + 1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("错词循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));
								intent = new Intent(wordsl2.this,
										idroortsl2.class);

								myapp.setscore(0, clicknum + 1);
								myapp.setdefwordscore(0, defwordclicknum + 1);
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

								System.out.println("错词循环结束");
								intent = new Intent(wordsl2.this, scorel2.class);

								myapp.cleanCwrongwords();
								myapp.setscore(0, clicknum + 1);
								myapp.setdefwordscore(0, defwordclicknum + 1);
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

						if (!key.equals(words[wordnum - 1][0])) { // 错词循环下做错了

							wordTextView4.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							
							if (!timergreencontrol) {
							myapp.addwrongwords1(words[wordnum - 1]);
							defrepeat(1); // 这是做错了加1
							Intent intent = new Intent(wordsl2.this,
									wordsl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setdefwordscore(0, defwordclicknum + 1);							
							finish();
							}
						}

					}

					if (wcon == 0) { // 标准循环下

						if (key.equals(words[wordnum - 1][0])) {// 标准循环下 做对了

							defrepeat(0);
							wordTextView4
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);
							myapp.setscore(1, rightnum + 1);
							myapp.setdefwordscore(1, defwordrightnum + 1);

							if (con == 1) { // PART2

								if (wordnum % 5 != 0) { // 没有走完5个单词

									myapp.set(4, Integer.toString(wordnum + 1));
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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

								if (wordnum % 5 == 0) { // 走完5个单词了
									myapp.set(4, Integer.toString(wordnum + 1));
									// myapp.setrepreatcontrol(0); // TT循环到头，
									// 清空控制阀

									if (words[wordnum][0].equals("")) { // 判断结束
										// 整个list的前两个sequence操作结束
										// 进入复习 wrong
										// words 流程。
										myapp.cleanwrongwords(); // 快速算法排序
																	// 清理wrongwords

										myapp.set(4, Integer.toString(1)); // 进入review
																			// wrong
																			// 循环
																			// wordnumber
																			// 给1

										// myapp.setreviewwrongcontrol(1); //
										// 设定进入reviewwrong
										// wcon赋值为1

										intent = new Intent(wordsl2.this,
												scorel2.class);

										myapp.setscore(0, clicknum + 1);
										myapp.setdefwordscore(0,
												defwordclicknum + 1);
										stopshape();
										timergreen = new Timer();
										timergreen.schedule(new TimerTask() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												startActivity(intent);
												finish();
											}
										}, sleeptime);

									}

								}
							}

							if (con == 0) { // part1部分

								if (wordnum % 5 != 0) {
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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
								if (wordnum % 5 == 0) {
									intent = new Intent(wordsl2.this,
											idroortsl2.class);

									myapp.setscore(0, clicknum + 1);
									myapp.setdefwordscore(0,
											defwordclicknum + 1);
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
						if (!key.equals(words[wordnum - 1][0])) { // 标准循环下做错了

							
							wordTextView4.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
							stopshape();
							
							if (!timergreencontrol) {
							defrepeat(1);
							myapp.addwrongword(words[wordnum - 1]);
							Intent intent = new Intent(wordsl2.this,
									wordsl2.class);
							startActivity(intent);
							myapp.setscore(0, clicknum + 1);
							myapp.setdefwordscore(0, defwordclicknum + 1);							
							finish();
							}
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
					String key = (String) wordTextView1.getText();

					if (wcon == 1) { // 错词循环下

						if (key.equals(words[wordnum - 1][0])) { // 做对了
							defrepeat(0);
							wordTextView1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(wordsl2.this,
										idroortsl2.class);
								startActivity(intent);
								stopshape();
								finish();

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								Intent intent = new Intent(wordsl2.this,
										scorel2.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								stopshape();
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // 错词循环下做错了

							wordTextView1.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
						}

					}

					if (wcon == 0) { // 标准循环下

						if (key.equals(words[wordnum - 1][0])) {// 标准循环下 做对了
							defrepeat(0);
							wordTextView1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (con == 1) { // 标准循环+ TT循环下

								if (wordnum % 5 != 0) { // 没有走完5个单词

									myapp.set(4, Integer.toString(wordnum + 1));
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}

								if (wordnum % 5 == 0) { // 走完5个单词了
									myapp.set(4, Integer.toString(wordnum + 1));

									if (words[wordnum][0].equals("")) { // 判断结束
										myapp.cleanwrongwords();
										myapp.set(4, Integer.toString(1));
										Intent intent = new Intent(
												wordsl2.this, scorel2.class);
										startActivity(intent);

									}

								}
							}

							if (con == 0) { // part1部分

								if (wordnum % 5 != 0) {
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}
								if (wordnum % 5 == 0) {
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}

							}

						}
						if (!key.equals(words[wordnum - 1][0])) { // 标准循环下做错了

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
					String key = (String) wordTextView2.getText();

					if (wcon == 1) { // 错词循环下

						if (key.equals(words[wordnum - 1][0])) { // 做对了
							defrepeat(0);
							wordTextView2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(wordsl2.this,
										idroortsl2.class);
								startActivity(intent);
								stopshape();
								finish();

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								Intent intent = new Intent(wordsl2.this,
										scorel2.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								stopshape();
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // 错词循环下做错了

							wordTextView2.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
						}

					}

					if (wcon == 0) { // 标准循环下

						if (key.equals(words[wordnum - 1][0])) {// 标准循环下 做对了
							defrepeat(0);
							wordTextView2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (con == 1) { // 标准循环+ TT循环下

								if (wordnum % 5 != 0) { // 没有走完5个单词

									myapp.set(4, Integer.toString(wordnum + 1));
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}

								if (wordnum % 5 == 0) { // 走完5个单词了
									myapp.set(4, Integer.toString(wordnum + 1));

									if (words[wordnum][0].equals("")) { // 判断结束
										myapp.cleanwrongwords();
										myapp.set(4, Integer.toString(1));
										Intent intent = new Intent(
												wordsl2.this, scorel2.class);
										startActivity(intent);

									}

								}
							}

							if (con == 0) { // part1部分

								if (wordnum % 5 != 0) {
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}
								if (wordnum % 5 == 0) {
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}

							}

						}
						if (!key.equals(words[wordnum - 1][0])) { // 标准循环下做错了

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
					String key = (String) wordTextView3.getText();

					if (wcon == 1) { // 错词循环下

						if (key.equals(words[wordnum - 1][0])) { // 做对了
							defrepeat(0);
							wordTextView3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(wordsl2.this,
										idroortsl2.class);
								startActivity(intent);
								stopshape();
								finish();

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								Intent intent = new Intent(wordsl2.this,
										scorel2.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								stopshape();
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // 错词循环下做错了

							wordTextView3.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
						}

					}

					if (wcon == 0) { // 标准循环下

						if (key.equals(words[wordnum - 1][0])) {// 标准循环下 做对了
							defrepeat(0);
							wordTextView3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (con == 1) { // 标准循环+ TT循环下

								if (wordnum % 5 != 0) { // 没有走完5个单词

									myapp.set(4, Integer.toString(wordnum + 1));
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}

								if (wordnum % 5 == 0) { // 走完5个单词了
									myapp.set(4, Integer.toString(wordnum + 1));

									if (words[wordnum][0].equals("")) { // 判断结束
										myapp.cleanwrongwords();
										myapp.set(4, Integer.toString(1));
										Intent intent = new Intent(
												wordsl2.this, scorel2.class);
										startActivity(intent);

									}

								}
							}

							if (con == 0) { // part1部分

								if (wordnum % 5 != 0) {
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}
								if (wordnum % 5 == 0) {
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}

							}

						}
						if (!key.equals(words[wordnum - 1][0])) { // 标准循环下做错了

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
					String key = (String) wordTextView4.getText();

					if (wcon == 1) { // 错词循环下

						if (key.equals(words[wordnum - 1][0])) { // 做对了
							defrepeat(0);
							wordTextView4
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (!words[wordnum][0].equals("")) {

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(wordsl2.this,
										idroortsl2.class);
								startActivity(intent);
								stopshape();
								finish();

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								Intent intent = new Intent(wordsl2.this,
										scorel2.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								stopshape();
								finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // 错词循环下做错了

							wordTextView4.setBackgroundResource(R.drawable.red);
							myapp.Vibrate();
							myapp.playmusic(0);
						}

					}

					if (wcon == 0) { // 标准循环下

						if (key.equals(words[wordnum - 1][0])) {// 标准循环下 做对了
							defrepeat(0);
							wordTextView4
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);

							if (con == 1) { // 标准循环+ TT循环下

								if (wordnum % 5 != 0) { // 没有走完5个单词

									myapp.set(4, Integer.toString(wordnum + 1));
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}

								if (wordnum % 5 == 0) { // 走完5个单词了
									myapp.set(4, Integer.toString(wordnum + 1));

									if (words[wordnum][0].equals("")) { // 判断结束
										myapp.cleanwrongwords();
										myapp.set(4, Integer.toString(1));
										Intent intent = new Intent(
												wordsl2.this, scorel2.class);
										startActivity(intent);

									}

								}
							}

							if (con == 0) { // part1部分

								if (wordnum % 5 != 0) {
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}
								if (wordnum % 5 == 0) {
									Intent intent = new Intent(wordsl2.this,
											idroortsl2.class);
									startActivity(intent);
									stopshape();
									finish();

								}

							}

						}
						if (!key.equals(words[wordnum - 1][0])) { // 标准循环下做错了

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
				// timer.cancel();
				stopshape();
				if (myapp.gethelpcontrol(4) == 0) {
					myapp.sethelpcontrol(4, 1);
				}

				alertdDialog = new AlertDialog.Builder(wordsl2.this)
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
										// textViewwr.setBackgroundColor(Color.TRANSPARENT);
										// timer.start();

									}
								}).create();

				alertdDialog.show();

			}
		});
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

		double h = Math.random() * 4; // 随机为0到1之间， 这里有4个按钮，所以选4

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
				// defrepeat(0); 因为自动跳转所以不再需要这里置0；

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

	private void defrepeat(int key) { // 这里是 输错2次的控制方法

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

	private String[][] get10ranwords(String[][] words) {

		String[][] word = words;

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < word[0].length; j++) {

				word[i][0] = "";

			}
		}

		Random random = new Random();
		for (int i = 10; i < 20; i++) {
			int p = random.nextInt(10) + 10;
			String[] k = word[i];
			word[i] = word[p];
			word[p] = k;
		}

		random = null;

		return word;
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
						myapp.pauselevelmusic(); // home key处理点

					} else if (reason.equals(SYSTEM_RECENT_APPS)) {
						myapp.pauselevelmusic();// long home key处理点
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
					.setMessage("Do you want to exit?s")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("Confirm",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									myapp.stoplevelmusic();
									myapp.empty();

									Intent intent = new Intent(wordsl2.this,
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
			Intent intent = new Intent(wordsl2.this, play.class);
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
			Intent intent = new Intent(wordsl2.this, list.class);
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
			Intent intent = new Intent(wordsl2.this, listselectactivity.class);
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
			Intent intent = new Intent(wordsl2.this, MainActivity.class);
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
