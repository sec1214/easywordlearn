package com.easylearnwords;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class root extends Activity {

	private Dialog alertdDialog;
	private TextView textView1, textView2, rootTextView;
	private TextView rootdef1, rootdef2, rootdef3, rootdef4, rootdef5,
			rootdef6;
	private TextView textViewlevel, textViewword, textViewwr,textViewscore;
	private mypublicvalue myapp;
	private String[][] words;
	private int wordnum;
	private int rootnum;
	private String[] roots;
	private int numroot;

	public int wcon, con, lv1;
	private double clicknum, rightnum;

	/* private int rootnumber; */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.root);

		textView1 = (TextView) this.findViewById(R.id.textview1);
		textView2 = (TextView) this.findViewById(R.id.textview2);

		textViewlevel = (TextView) this.findViewById(R.id.leveltext);
		textViewword = (TextView) this.findViewById(R.id.wordtext);
		textViewwr = (TextView) this.findViewById(R.id.wrtext);
		textViewscore=(TextView)this.findViewById(R.id.scoretext);
		rootdef1 = (TextView) this.findViewById(R.id.rootdef1);
		rootdef2 = (TextView) this.findViewById(R.id.rootdef2);
		rootdef3 = (TextView) this.findViewById(R.id.rootdef3);
		rootdef4 = (TextView) this.findViewById(R.id.rootdef4);
		rootdef5 = (TextView) this.findViewById(R.id.rootdef5);
		rootdef6 = (TextView) this.findViewById(R.id.rootdef6);

		myapp = (mypublicvalue) getApplication();

		textView1.setText(myapp.get(0));
		textView2.setText(myapp.get(1));

		textViewlevel.setText(" Level: " + myapp.get(3)); // 设定显示level的控件

		wordnum = Integer.parseInt(myapp.get(4));

		clicknum = myapp.getscore(0);
		rightnum = myapp.getscore(1);
		
		textViewword.setText("Word: " + wordnum + "  "); // 设定显示几号word的控件

		wcon = myapp.getreviewwrongcontrol(); // 复习控制阀值
		con = myapp.getrepeatcontrol();
		lv1 = Integer.parseInt(myapp.get(8));

		if (wcon == 0) { // 标准情况下 words 取用

			if (lv1 == 0) {
				words = myapp.getwords();

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

			words = myapp.getCwrongwords();
			
			System.out.println("Cwrongwords:"+words[0][0]);
			
			
			textViewwr.setText("Wrong Reivew");
			textViewwr.setBackgroundColor(Color.RED);
			textViewscore.setText("Score:"
					+ (int)((myapp.getscore(1) / myapp.getscore(0)) * 100) + "%");

		}

		rootTextView = (TextView) this.findViewById(R.id.roottestview);

		rootnum = Integer.parseInt(myapp.get(5)); // step
		//String word = words[wordnum - 1][rootnum];
		rootTextView.setText(getchangeword());

		System.out.println(myapp.getrepeatcontrol() + "Root");

		this.ran();
		
		if (Integer.parseInt(myapp.get(6)) < 2) {
			rootdef1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String k = (String) rootdef1.getText();

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // 选对了

							defrepeat(0);
							rootdef1.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);
							// 变色

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);
								// playmusic(1);

								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // root没有了，需要重置

								System.out.println("wcon" + wcon);
								System.out.println("进入word步骤");

								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(root.this,
											words.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								} else {
									Intent intent = new Intent(root.this,
											definition.class);
									startActivity(intent);
									myapp.setscore(0, clicknum+1);finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {  //选错了

							defrepeat(1);
							rootdef1.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							 myapp.addwrongwords1(words[wordnum-1]);

							Intent intent = new Intent(root.this, root.class);
							startActivity(intent);

							// playmusic(0);
							myapp.setscore(0, clicknum+1);finish();

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(0);
							rootdef1.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(1);
							rootdef1.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							// myapp.addwrongword(words[wordnum-1]);
							// //进入复习循环不用增加wrongword了

							Intent intent = new Intent(root.this, root.class);
							startActivity(intent);

							// playmusic(0);
							myapp.setscore(0, clicknum+1);finish();

						}
					}
				}
			});

			rootdef2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String k = (String) rootdef2.getText();

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // 选对了

							defrepeat(0);
							rootdef2.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);
							// 变色

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // root没有了，需要重置

								System.out.println("wcon" + wcon);
								System.out.println("进入word步骤");

								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(root.this,
											words.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								} else {
									Intent intent = new Intent(root.this,
											definition.class);
									startActivity(intent);
									myapp.setscore(0, clicknum+1);finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(1);
							rootdef2.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							 myapp.addwrongwords1(words[wordnum-1]);

							Intent intent = new Intent(root.this, root.class);
							startActivity(intent);

							// playmusic(0);

							myapp.setscore(0, clicknum+1);finish();

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(0);
							rootdef2.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(1);
							rootdef2.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							myapp.addwrongword(words[wordnum - 1]);

							Intent intent = new Intent(root.this, root.class);
							startActivity(intent);

							// playmusic(0);
							myapp.setscore(0, clicknum+1);finish();

						}

					}
				}
			});

			rootdef3.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String k = (String) rootdef3.getText();

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // 选对了

							defrepeat(0);
							rootdef3.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);
							// playmusic(1); // 变色

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // root没有了，需要重置

								System.out.println("wcon" + wcon);
								System.out.println("进入word步骤");
								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(root.this,
											words.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								} else {
									Intent intent = new Intent(root.this,
											definition.class);
									startActivity(intent);
									myapp.setscore(0, clicknum+1);finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(1);
							rootdef3.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);
							// playmusic(0);
							;

							 myapp.addwrongwords1(words[wordnum-1]);

							Intent intent = new Intent(root.this, root.class);
							startActivity(intent);

							// playmusic(0);

							myapp.setscore(0, clicknum+1);finish();

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(0);
							rootdef3.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);
							// playmusic(1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(1);
							rootdef3.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);
							// playmusic(0);

							myapp.addwrongword(words[wordnum - 1]);

							Intent intent = new Intent(root.this, root.class);
							startActivity(intent);

							// playmusic(0);

							myapp.setscore(0, clicknum+1);finish();

						}

					}
				}
			});

			rootdef4.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String k = (String) rootdef4.getText();

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // 选对了

							defrepeat(0);
							rootdef4.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);
							// playmusic(1); // 变色

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // root没有了，需要重置

								System.out.println("wcon" + wcon);
								System.out.println("进入word步骤");

								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(root.this,
											words.class);
									startActivity(intent);
									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								} else {
									Intent intent = new Intent(root.this,
											definition.class);
									startActivity(intent);
									myapp.setscore(0, clicknum+1);finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(1);
							rootdef4.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);
							// playmusic(0);

							 myapp.addwrongwords1(words[wordnum-1]);

							Intent intent = new Intent(root.this, root.class);
							startActivity(intent);

							// playmusic(0);

							myapp.setscore(0, clicknum+1);finish();

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(0);
							rootdef4.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);
							// playmusic(1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);
								// playmusic(1);

								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(1);
							rootdef4.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);
							// playmusic(0);
							;

							myapp.addwrongword(words[wordnum - 1]);

							Intent intent = new Intent(root.this, root.class);
							startActivity(intent);

							// playmusic(0);

							myapp.setscore(0, clicknum+1);finish();

						}
					}

				}
			});

			rootdef5.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String k = (String) rootdef5.getText();

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // 选对了

							defrepeat(0);
							rootdef5.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);
							// playmusic(1); // 变色

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // root没有了，需要重置

								System.out.println("wcon" + wcon);
								System.out.println("进入word步骤");

								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(root.this,
											words.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								} else {
									Intent intent = new Intent(root.this,
											definition.class);
									startActivity(intent);
									myapp.setscore(0, clicknum+1);finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(1);
							rootdef5.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							 myapp.addwrongwords1(words[wordnum-1]);

							Intent intent = new Intent(root.this, root.class);
							startActivity(intent);

							// playmusic(0);

							myapp.setscore(0, clicknum+1);finish();

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(0);
							rootdef5.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(1);
							rootdef5.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							myapp.addwrongword(words[wordnum - 1]);

							Intent intent = new Intent(root.this, root.class);
							startActivity(intent);

							// playmusic(0);

							myapp.setscore(0, clicknum+1);finish();

						}

					}
				}
			});

			rootdef6.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String k = (String) rootdef6.getText();

					if (wcon == 1) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) { // 选对了

							defrepeat(0);
							rootdef6.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);
							// 变色

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(2)); // root没有了，需要重置

								System.out.println("wcon" + wcon);
								System.out.println("进入word步骤");

								double h = Math.random();

								if (h < 0.5) {

									Intent intent = new Intent(root.this,
											words.class);
									startActivity(intent);

									// playmusic(1);
									myapp.setscore(0, clicknum+1);finish();

								} else {
									Intent intent = new Intent(root.this,
											definition.class);
									startActivity(intent);
									myapp.setscore(0, clicknum+1);finish();
								}

							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(1);
							rootdef6.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							 myapp.addwrongwords1(words[wordnum-1]);

							Intent intent = new Intent(root.this, root.class);
							startActivity(intent);

							// playmusic(0);

							myapp.setscore(0, clicknum+1);finish();

						}

					}

					if (wcon == 0) {

						if (k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(0);
							rootdef6.setBackgroundResource(R.drawable.green);
							myapp.playmusic(1);myapp.setscore(1, rightnum+1);

							if (!words[wordnum - 1][rootnum + 2].equals("")) {

								myapp.set(5, Integer.toString(rootnum + 2));

								Intent intent = new Intent(root.this,
										root.class);

								startActivity(intent);

								// playmusic(1);
								myapp.setscore(0, clicknum+1);finish();
							}

							if (words[wordnum - 1][rootnum + 2].equals("")) {
								repeattt5();
							}

						}

						if (!k.equals(words[wordnum - 1][rootnum + 1])) {

							defrepeat(1);
							rootdef6.setBackgroundResource(R.drawable.red);
							myapp.playmusic(0);

							myapp.addwrongword(words[wordnum - 1]);

							Intent intent = new Intent(root.this, root.class);
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
			Toast.makeText(root.this, "Remember Green And Correct Choice", 1)
					.show();
			final Timer timer = new Timer();
			TimerTask timerTask = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (!words[wordnum - 1][rootnum + 2].equals("")) { // 证明后头还有root，

						myapp.set(5, Integer.toString(rootnum + 2));

						Intent intent = new Intent(root.this, root.class);

						startActivity(intent);
						timer.cancel();
						// playmusic(1);
						finish();
					}

					if (words[wordnum - 1][rootnum + 2].equals("")) { // 证明后头没root了
						timer.cancel();
						myapp.setscore(0, clicknum-1);   //因为这里clicknum不应+1 但是repeattt5（）中 复用情况下，clicknum必然+1 所以提前减掉
						repeattt5();
					}

				}
			};
			timer.schedule(timerTask, 3000);

		}

	}

	private void ran() {

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

		System.out.println("多少个root+" + numroot);

		for (int i = 0; i < numroot; i++) {

			if (words[wordnum - 1][rootnum + 1].equals(roots[i])) {
				a1 = i;
				break;
			}
		}

		//System.out.println("本次循环过");

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
				// myapp.playmusic(1);myapp.setscore(1, rightnum+1);
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
			
			}
		}

	}

	public void repeattt5() {
		// 这条选择代表root后没有root了代表走到头啦

		if (wordnum % 5 == 0) { // 代表走到5个词了

			if (myapp.getrepeatcontrol() == 1) { // 判断是否进入tt循环 //在TT循环中 在part2中

				myapp.set(5, Integer.toString(2)); // root没有了，需要重置

				double h = Math.random();

				if (h < 0.5) {
					Intent intent = new Intent(root.this, words.class);
					startActivity(intent);

					// playmusic(1);

					myapp.setscore(0, clicknum+1);finish();
				} else {
					Intent intent = new Intent(root.this, definition.class);

					startActivity(intent);
					myapp.setscore(0, clicknum+1);finish();

				}

			}

			if (myapp.getrepeatcontrol() == 0) { // 判断进入tt循环

				myapp.set(4, Integer.toString(wordnum + 1));
				myapp.set(5, Integer.toString(2)); // root没有了，需要重置
				myapp.setrepreatcontrol(1);
				Intent intent = new Intent(root.this, root.class);
				startActivity(intent);

				// playmusic(1);
				myapp.setscore(0, clicknum+1);finish();

			}

		}
		if (wordnum % 5 != 0) {

			myapp.set(5, Integer.toString(2)); // root没有了，需要重置

			if (myapp.getrepeatcontrol() == 1) { // 证明在tt循环中 在part2中
				double h = Math.random();

				if (h < 0.5) {
					Intent intent = new Intent(root.this, words.class);
					startActivity(intent);

					// playmusic(1);
					myapp.setscore(0, clicknum+1);finish();
				} else {
					Intent intent = new Intent(root.this, definition.class);
					startActivity(intent);
					myapp.setscore(0, clicknum+1);finish();
				}

			}

			if (myapp.getrepeatcontrol() == 0) { // 在part1 中

				myapp.set(4, Integer.toString(wordnum + 1));
				double h = Math.random();

				if (h < 0.5) {
					Intent intent = new Intent(root.this, words.class);
					startActivity(intent);

					// playmusic(1);
					myapp.setscore(0, clicknum+1);finish();
				} else {
					Intent intent = new Intent(root.this, definition.class);
					startActivity(intent);
					myapp.setscore(0, clicknum+1);finish();
				}

			}
		}

	}

	private SpannableStringBuilder getchangeword() { // 本方法自动创建相对应的缺失root的字符串
		// TODO Auto-generated method stub
		  //起始root的地点
		    String word = words[wordnum - 1][0];
		
			String changeword = words[wordnum - 1][rootnum];
			
			int start=word.indexOf(changeword);
            
		    int end=start+changeword.length();
          
		    SpannableStringBuilder style=new SpannableStringBuilder(word);
			
		    style.setSpan(new ForegroundColorSpan(Color.RED),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	

		/*
		 * String kString="Word: " + wordnum + "  "; SpannableStringBuilder
		 * style=new SpannableStringBuilder(kString); style.setSpan(new
		 * ForegroundColorSpan
		 * (Color.RED),1,3,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 1,3
		 * 代表从第二个字符到第三个字符，变色
		 */

		return style;
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

									Intent intent = new Intent(root.this,
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
