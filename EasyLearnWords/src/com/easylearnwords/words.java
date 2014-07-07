package com.easylearnwords;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Database.managedb;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class words extends Activity {

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
	private int lv1;

	private double clicknum, rightnum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word);

		System.out.println("Word.class 启动");

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

		/* db = new managedb(this); */
		/* myapp.setwords(db.getwords()); */

		textView1.setText(myapp.get(0));
		textView2.setText(myapp.get(1));

		textViewlevel.setText(" Level: " + myapp.get(3)); // 设定显示level的控件

		wordnum = Integer.parseInt(myapp.get(4));
		clicknum = myapp.getscore(0);
		rightnum = myapp.getscore(1);
		textViewword.setText("Word: " + wordnum + "  "); // 设定显示几号word的控件

		wcon = myapp.getreviewwrongcontrol(); // 复习控制阀值

		con = myapp.getrepeatcontrol(); // TT循环控制阀

		lv1 = Integer.parseInt(myapp.get(8));

		if (wcon == 0) { // 标准情况下 words 取用

			if (lv1 == 0) {
				words = myapp.getwords();
				words = get10ranwords(words);
				myapp.setwordslv1(words);
				myapp.set(8, Integer.toString(1));

			}

			if (lv1 == 1) {
				textViewscore.setText("Score:"
						+ (int)((myapp.getscore(1) / myapp.getscore(0)) * 100) + "%");
				words = myapp.getwordslv1();

			}

			textViewwr.setText("");
			textViewwr.setBackgroundColor(Color.TRANSPARENT);

			if (con == 0) {
				textViewwr.setText("Part1");

			}
			if (con == 1) {
				textViewwr.setText("Part2");
				textViewwr.setBackgroundColor(Color.GREEN);

			}

		}

		if (wcon == 1) { // 错误情况下， 取用错词
			/* lwords = myapp.getwords(); */
			words = myapp.getCwrongwords();
			textViewwr.setText("Wrong Reivew");
			textViewwr.setBackgroundColor(Color.RED);
			textViewscore.setText("Score:"
					+ (int)((myapp.getscore(1) / myapp.getscore(0)) * 100) + "%");

		}

		defTextView.setText(words[wordnum - 1][1]);

		System.out.println("Word TT 循环控制阀 变量 con " + myapp.getrepeatcontrol());
		System.out.println("Word wrong 循环控制阀 变量 wcon "
				+ myapp.getreviewwrongcontrol());

		System.out.println("ran前一步");

		this.ran();

		if (Integer.parseInt(myapp.get(6)) < 2) {

			wordTextView1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) wordTextView1.getText();

					if (wcon == 1) { // 错词循环下

						if (key.equals(words[wordnum - 1][0])) { // 做对了

							wordTextView1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("错词循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(words.this,
										root.class);
								startActivity(intent);
								// playmusic(1);

								myapp.setscore(0, clicknum+1);finish();

							}

							if (words[wordnum][0].equals("")) { // 错词循环结束了

								System.out.println("错词循环结束");
								Intent intent = new Intent(words.this,
										score.class);
								
								myapp.cleanCwrongwords();

								startActivity(intent);
								// playmusic(1);

								myapp.setscore(0, clicknum+1);finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) { // 错词循环下做错了

							wordTextView1.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);
							myapp.addwrongwords1(words[wordnum-1]);
							defrepeat(1); // 这是做错了加1
							Intent intent = new Intent(words.this, words.class);
							startActivity(intent);

							// playmusic(0);
							myapp.setscore(0, clicknum+1);finish();

						}

					}

					if (wcon == 0) { // 标准循环下

						if (key.equals(words[wordnum - 1][0])) {// 标准循环下 做对了

							defrepeat(0);
							wordTextView1
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							if (con == 1) { // 标准循环+ TT循环下

								if (wordnum % 5 != 0) { // 没有走完5个单词

									myapp.set(4, Integer.toString(wordnum + 1));
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}

								if (wordnum % 5 == 0) { // 走完5个单词了
									myapp.set(4, Integer.toString(wordnum + 1));
									myapp.setrepreatcontrol(0); // TT循环到头， 清空控制阀

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

										//myapp.setreviewwrongcontrol(1); // 设定进入reviewwrong
																		// wcon赋值为1

										String[][] wrongwords = myapp
												.getCwrongwords();

										System.out.println(wrongwords[0][0]);

										System.out.println(wrongwords[1][0]);

										System.out.println(wrongwords[2][0]); // 便于测试使用本段

										System.out.println(wrongwords[3][0]);

										System.out.println(wrongwords[4][0]);

										Intent intent = new Intent(words.this,
												score.class);

										startActivity(intent);
										myapp.setscore(0, clicknum+1);finish();

									}

								}
							}

							if (con == 0) { // 标准循环 and 非TT循环 part1部分

								if (wordnum % 5 != 0) {
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}
								if (wordnum % 5 == 0) {
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}

							}

						}
						if (!key.equals(words[wordnum - 1][0])) { // 标准循环下做错了

							defrepeat(1);
							wordTextView1.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							myapp.addwrongword(words[wordnum - 1]);

							String[][] wrongwords = myapp.getwrongwords();
							System.out.println(wrongwords[0][0]);

							System.out.println(wrongwords[1][0]);

							System.out.println(wrongwords[2][0]);

							Intent intent = new Intent(words.this, words.class);
							startActivity(intent);

							// playmusic(0);
							myapp.setscore(0, clicknum+1);finish();

						}

					}
				}
			});

			wordTextView2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) wordTextView2.getText();

					if (wcon == 1) {

						if (key.equals(words[wordnum - 1][0])) {

							wordTextView2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("错词循环下一个");
								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(words.this,
										root.class);
								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();

							}

							if (words[wordnum][0].equals("")) {

								System.out.println("错词循环结束");
								Intent intent = new Intent(words.this,
										score.class);
								myapp.cleanCwrongwords();
								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();

							}

						}
						if (!key.equals(words[wordnum - 1][0])) {

							wordTextView2.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);
							myapp.addwrongwords1(words[wordnum-1]);
							defrepeat(1);
							Intent intent = new Intent(words.this, words.class);
							startActivity(intent);

							// playmusic(0);

							myapp.setscore(0, clicknum+1);finish();

						}

					}

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][0])) {

							defrepeat(0);
							wordTextView2
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							if (con == 1) {

								if (wordnum % 5 != 0) {

									myapp.set(4, Integer.toString(wordnum + 1));
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}

								if (wordnum % 5 == 0) {
									myapp.set(4, Integer.toString(wordnum + 1));
									myapp.setrepreatcontrol(0); // TT循环到头， 清空控制阀

									if (words[wordnum][0].equals("")) { // 判断结束
										// 整个list的前两个sequence操作
										// 进入复习 wrong
										// words 流程。
										myapp.cleanwrongwords();

										myapp.set(4, Integer.toString(1)); // 进入review
																			// wrong
																			// 循环
																			// wordnumber
																			// 给1

										//myapp.setreviewwrongcontrol(1); // 设定进入reviewwrong

										String[][] wrongwords = myapp
												.getCwrongwords();

										System.out.println(wrongwords[0][0]);

										System.out.println(wrongwords[1][0]);

										System.out.println(wrongwords[2][0]);

										System.out.println(wrongwords[3][0]);

										System.out.println(wrongwords[4][0]);
										Intent intent = new Intent(words.this,
												score.class);

										startActivity(intent);
										myapp.setscore(0, clicknum+1);finish();

									}

								}
							}

							if (con == 0) {

								if (wordnum % 5 != 0) {
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}
								if (wordnum % 5 == 0) {
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);
									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}

							}

						}
						if (!key.equals(words[wordnum - 1][0])) {

							defrepeat(1);
							wordTextView2.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							myapp.addwrongword(words[wordnum - 1]);

							String[][] wrongwords = myapp.getwrongwords();
							System.out.println(wrongwords[0][0]);

							System.out.println(wrongwords[1][0]);

							System.out.println(wrongwords[2][0]);

							Intent intent = new Intent(words.this, words.class);
							startActivity(intent);

							// playmusic(0);
							myapp.setscore(0, clicknum+1);finish();

						}

					}
				}
			});
			wordTextView3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) wordTextView3.getText();

					if (wcon == 1) {
						if (key.equals(words[wordnum - 1][0])) {

							wordTextView3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("走了root1");
								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(words.this,
										root.class);
								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();

							}

							if (words[wordnum][0].equals("")) {

								System.out.println("走了root2");
								Intent intent = new Intent(words.this,
										score.class);
								myapp.cleanCwrongwords();
								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) {

							wordTextView3.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);
							myapp.addwrongwords1(words[wordnum-1]);
							defrepeat(1);
							Intent intent = new Intent(words.this, words.class);
							startActivity(intent);

							// playmusic(0);
							myapp.setscore(0, clicknum+1);finish();

						}
					}

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][0])) {

							defrepeat(0);
							wordTextView3
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							if (con == 1) {

								if (wordnum % 5 != 0) {

									myapp.set(4, Integer.toString(wordnum + 1));
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}

								if (wordnum % 5 == 0) {
									myapp.set(4, Integer.toString(wordnum + 1));
									myapp.setrepreatcontrol(0); // TT循环到头， 清空控制阀

									if (words[wordnum][0].equals("")) { // 判断结束
										// 整个list的前两个sequence操作
										// 进入复习 wrong
										// words 流程。
										myapp.cleanwrongwords();

										myapp.set(4, Integer.toString(1)); // 进入review
																			// wrong
																			// 循环
																			// wordnumber
																			// 给1

										//myapp.setreviewwrongcontrol(1); // 设定进入reviewwrong

										String[][] wrongwords = myapp
												.getCwrongwords();
										System.out.println(wrongwords[0][0]);

										System.out.println(wrongwords[1][0]);

										System.out.println(wrongwords[2][0]);

										System.out.println(wrongwords[3][0]);

										System.out.println(wrongwords[4][0]);

										Intent intent = new Intent(words.this,
												score.class);

										startActivity(intent);
										myapp.setscore(0, clicknum+1);finish();

									}

								}
							}

							if (con == 0) {

								if (wordnum % 5 != 0) {
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}
								if (wordnum % 5 == 0) {
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}

							}

						}
						if (!key.equals(words[wordnum - 1][0])) {

							defrepeat(1);
							wordTextView3.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							myapp.addwrongword(words[wordnum - 1]);

							String[][] wrongwords = myapp.getwrongwords();
							System.out.println(wrongwords[0][0]);

							System.out.println(wrongwords[1][0]);

							System.out.println(wrongwords[2][0]);

							Intent intent = new Intent(words.this, words.class);
							startActivity(intent);

							// playmusic(0);
							myapp.setscore(0, clicknum+1);finish();

						}

					}
				}
			});

			wordTextView4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String key = (String) wordTextView4.getText();

					if (wcon == 1) {
						if (key.equals(words[wordnum - 1][0])) {

							wordTextView4
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							defrepeat(0);
							if (!words[wordnum][0].equals("")) {

								System.out.println("走了root1");
								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(words.this,
										root.class);
								startActivity(intent);
								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum][0].equals("")) {

								System.out.println("走了root2");
								Intent intent = new Intent(words.this,
										score.class);
								myapp.cleanCwrongwords();
								startActivity(intent);
								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();

							}

						}

						if (!key.equals(words[wordnum - 1][0])) {

							wordTextView4.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);
							myapp.addwrongwords1(words[wordnum-1]);
							defrepeat(1);
							Intent intent = new Intent(words.this, words.class);
							startActivity(intent);
							// playmusic(0);
							myapp.setscore(0, clicknum+1);finish();

						}

					}

					if (wcon == 0) {

						if (key.equals(words[wordnum - 1][0])) {

							defrepeat(0);
							wordTextView4
									.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							if (con == 1) {

								if (wordnum % 5 != 0) {

									myapp.set(4, Integer.toString(wordnum + 1));
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);
									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}

								if (wordnum % 5 == 0) {
									myapp.set(4, Integer.toString(wordnum + 1));
									myapp.setrepreatcontrol(0); // TT循环到头， 清空控制阀

									if (words[wordnum][0].equals("")) { // 判断结束
										// 整个list的前两个sequence操作
										// 进入复习 wrong
										// words 流程。
										myapp.cleanwrongwords();

										/*
										 * Toast.makeText( words.this,
										 * "OK, This list word over, go to review wrong words"
										 * , 1).show();
										 */

										myapp.set(4, Integer.toString(1)); // 进入review
																			// wrong
																			// 循环
																			// wordnumber
																			// 给1

										//myapp.setreviewwrongcontrol(1); // 设定进入reviewwrong

										String[][] wrongwords = myapp
												.getCwrongwords();

										System.out.println(wrongwords[0][0]);

										System.out.println(wrongwords[1][0]);

										System.out.println(wrongwords[2][0]);

										System.out.println(wrongwords[3][0]);

										System.out.println(wrongwords[4][0]);

										Intent intent = new Intent(words.this,
												score.class);

										startActivity(intent);
										myapp.setscore(0, clicknum+1);finish();

									}

								}
							}

							if (con == 0) {

								if (wordnum % 5 != 0) {
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);
									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}
								if (wordnum % 5 == 0) {
									Intent intent = new Intent(words.this,
											root.class);
									startActivity(intent);
									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								}

							}

						}
						if (!key.equals(words[wordnum - 1][0])) {

							defrepeat(1);
							wordTextView4.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							myapp.addwrongword(words[wordnum - 1]);

							String[][] wrongwords = myapp.getwrongwords();
							System.out.println(wrongwords[0][0]);

							System.out.println(wrongwords[1][0]);

							System.out.println(wrongwords[2][0]);

							Intent intent = new Intent(words.this, words.class);
							startActivity(intent);
							// playmusic(0);
							myapp.setscore(0, clicknum+1);finish();

						}

					}
				}
			});
		}

		if (Integer.parseInt(myapp.get(6)) == 2) {
			System.out.println("停顿3秒" + Integer.parseInt(myapp.get(6)));
			defrepeat(0); // 计数器错误 清零
			Toast.makeText(words.this, "Remember Green And Correct Choice", 1)
					.show();
			final Timer timer = new Timer();
			TimerTask timerTask = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (con == 0) {
						Intent intent = new Intent(words.this, root.class);
						startActivity(intent);
						timer.cancel();
						finish();
					}
					if (con == 1) {
						if (!words[wordnum][0].equals("")) {
							myapp.set(4, Integer.toString(wordnum + 1));
							Intent intent = new Intent(words.this, root.class);
							startActivity(intent);
							timer.cancel();
							finish();
						}

						if (words[wordnum][0].equals("")) {
							Intent intent = new Intent(words.this, score.class);
							myapp.cleanCwrongwords();
							startActivity(intent);
							timer.cancel();
							finish();
						}

					}
				}
			};
			timer.schedule(timerTask, 3000);

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

		h = Math.random() * numroot;
		a4 = (int) h;
		while (a1 == a4 || a2 == a4 || a3 == a4) {
			h = Math.random() * numroot;
			a4 = (int) h;
		}

		System.out.println(a1 + "+" + a2 + "+" + a3 + "+" + a4);

		if (key == 1) {
			wordTextView1.setText(words[wordnum - 1][0]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				wordTextView1.setBackgroundResource(R.drawable.green);
			

			}
			wordTextView2.setText(roots[a2]);
			wordTextView3.setText(roots[a3]);
			wordTextView4.setText(roots[a4]);
		}

		if (key == 2) {
			wordTextView2.setText(words[wordnum - 1][0]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				wordTextView2.setBackgroundResource(R.drawable.green);

			}

			wordTextView1.setText(roots[a3]);
			wordTextView3.setText(roots[a2]);
			wordTextView4.setText(roots[a4]);
		}

		if (key == 3) {
			wordTextView3.setText(words[wordnum - 1][0]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				wordTextView3.setBackgroundResource(R.drawable.green);

			}

			wordTextView1.setText(roots[a4]);
			wordTextView2.setText(roots[a3]);
			wordTextView4.setText(roots[a2]);
		}
		if (key == 4) {
			wordTextView4.setText(words[wordnum - 1][0]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				wordTextView4.setBackgroundResource(R.drawable.green);

			}

			wordTextView1.setText(roots[a2]);
			wordTextView2.setText(roots[a4]);
			wordTextView3.setText(roots[a3]);
		}

	}

	private void defrepeat(int key) { // 这里是 输错2次的控制方法

		if (key == 0) {
			myapp.set(6, Integer.toString(0));
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
		System.out.println("root定义");
		//

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

		for (int i = 10; i < word.length; i++) {
			for (int j = 0; j < word[0].length; j++) {

				word[i][0] = "";

			}
		}

		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int p = random.nextInt(10);
			String[] k = word[i];
			word[i] = word[p];
			word[p] = k;
		}

		random = null;

		return words;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			alertdDialog = new AlertDialog.Builder(this)
					.setTitle("EXIT LEVEL")
					.setMessage("Do you want to exit this level learning？")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("Confirm",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									myapp.empty();

									Intent intent = new Intent(words.this,
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
								}
							}).create();

			alertdDialog.show();

		}

		return super.onKeyDown(keyCode, event);
	}

}
