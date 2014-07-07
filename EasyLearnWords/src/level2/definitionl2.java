package level2;

import com.easylearnwords.R;
import com.easylearnwords.definition;
import com.easylearnwords.mypublicvalue;
import com.easylearnwords.play;
import com.easylearnwords.root;
import com.easylearnwords.score;
import com.easylearnwords.words;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class definitionl2 extends Activity {

	private Dialog alertdDialog;
	private TextView textView1, textView2, wordtTextView, textViewdef1,
			textViewdef2, textViewdef3;
	private TextView textViewlevel, textViewword, textViewwr;
	private mypublicvalue myapp;
	private String[][] words;
	private int wordnum;
	private int con;
	public int wcon; // 复习控制阀
	private String[] roots;
	private int numroot;

	private String[][] wrongwords;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.definition);

		System.out.println("definitionl2.class 启动");

		textView1 = (TextView) this.findViewById(R.id.textview1);
		textView2 = (TextView) this.findViewById(R.id.textview2);
		wordtTextView = (TextView) this.findViewById(R.id.wordtestview);
		textViewdef1 = (TextView) this.findViewById(R.id.textviewdef1);
		textViewdef2 = (TextView) this.findViewById(R.id.textviewdef2);
		textViewdef3 = (TextView) this.findViewById(R.id.textviewdef3);
		textViewlevel = (TextView) this.findViewById(R.id.leveltext);
		textViewword = (TextView) this.findViewById(R.id.wordtext);
		textViewwr = (TextView) this.findViewById(R.id.wrtext);

		myapp = (mypublicvalue) getApplication();

		textView1.setText(myapp.get(0));
		textView2.setText(myapp.get(1));

		textViewlevel.setText(" Level: " + myapp.get(3)); // 设定显示level的控件

		wordnum = Integer.parseInt(myapp.get(4));

		textViewword.setText("Word: " + wordnum + "  "); // 设定显示几号word的控件

		/* words = myapp.getwords(); */

		wcon = myapp.getreviewwrongcontrol(); // 复习控制阀值

		con = myapp.getrepeatcontrol(); // TT循环控制阀

		if (wcon == 0) { // 标准情况下 words 取用

			words = myapp.getwords();
			textViewwr.setText("");
			textViewwr.setBackgroundColor(Color.TRANSPARENT);

			if (con == 0) {
				textViewwr.setText("");

			}
			if (con == 1) {
				textViewwr.setText("Repeat 5 words");
				textViewwr.setBackgroundColor(Color.GREEN);

			}

		}

		if (wcon == 1) { // 错误情况下， 取用错词
			/* lwords = myapp.getwords(); */
			words = myapp.getCwrongwords();
			textViewwr.setText("Wrong Reivew");
			textViewwr.setBackgroundColor(Color.RED);

		}

		int a1 = words.length;
		int a2 = words[0].length;

		String word = words[wordnum - 1][0];
		wordtTextView.setText(word);

		System.out.println("definition TT 循环控制阀 变量 con "
				+ myapp.getrepeatcontrol());
		System.out.println("definition wrong 循环控制阀 变量 wcon "
				+ myapp.getreviewwrongcontrol());

		System.out.println("ran前一步");

		this.ran();

		System.out.println("Def" + myapp.getrepeatcontrol());
		/* con = myapp.getrepeatcontrol(); */
		textViewdef1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String key = (String) textViewdef1.getText();

				if (wcon == 1) { // 错词循环下

					if (key.equals(words[wordnum - 1][1])) { // 做对了

						textViewdef1.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						defrepeat(0);
						if (!words[wordnum][0].equals("")) {

							System.out.println("错词循环下一个");
							myapp.set(4, Integer.toString(wordnum + 1));
							Intent intent = new Intent(definitionl2.this, idroortsl2.class);
							startActivity(intent);
							// playmusic(1);

							finish();

						}

						if (words[wordnum][0].equals("")) { // 错词循环结束了

							System.out.println("错词循环结束");
							Intent intent = new Intent(definitionl2.this, score.class);

							startActivity(intent);
							// playmusic(1);

							finish();

						}

					}

					if (!key.equals(words[wordnum - 1][1])) { // 错词循环下做错了

						textViewdef1.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						defrepeat(1); // 这是做错了加1
						Intent intent = new Intent(definitionl2.this, definitionl2.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}

				if (wcon == 0) { // 标准循环下

					if (key.equals(words[wordnum - 1][1])) {// 标准循环下 做对了

						defrepeat(0);
						textViewdef1.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						if (con == 1) { // 标准循环+ TT循环下

							if (wordnum % 5 != 0) { // 没有走完5个单词

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(definitionl2.this,
										idroortsl2.class);
								startActivity(intent);

								// playmusic(1);
								finish();

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

									/*
									 * Toast.makeText( definitionl2.this,
									 * "OK, This list words is over, we will enter REVIEW step"
									 * , 1).show();
									 */

									myapp.set(4, Integer.toString(1)); // 进入review
																		// wrong
																		// 循环
																		// wordnumber
																		// 给1

									myapp.setreviewwrongcontrol(1); // 设定进入reviewwrong
																	// wcon赋值为1

									String[][] wrongwords = myapp
											.getCwrongwords();

									System.out.println(wrongwords[0][0]);

									System.out.println(wrongwords[1][0]);

									System.out.println(wrongwords[2][0]); // 便于测试使用本段

									System.out.println(wrongwords[3][0]);

									System.out.println(wrongwords[4][0]);

									if (!wrongwords[0][0].equals("")) {
										Intent intent = new Intent(definitionl2.this,
												idroortsl2.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

									if (wrongwords[0][0].equals("")) {

										Intent intent = new Intent(definitionl2.this,
												score.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

								}

								if (!words[wordnum][0].equals("")) { // 标准
																		// 本次TT循环结束 但前两个循环还有单词

									double h = Math.random();

									 if (h < 0.5) {
									Intent intent = new Intent(definitionl2.this,
											wordsl2.class);
									startActivity(intent);

									// playmusic(1);
									finish();

									 } else {
									 Intent intent = new Intent(definitionl2.this,
									 definitionl2.class);
									 startActivity(intent);finish();finish();

									 }
								}
							}
						}

						if (con == 0) { // 标准循环 and 非TT循环

							if (wordnum % 5 != 0) {
								Intent intent = new Intent(definitionl2.this,
										idroortsl2.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}
							if (wordnum % 5 == 0) {
								Intent intent = new Intent(definitionl2.this,
										idroortsl2.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}

						}

					}
					if (!key.equals(words[wordnum - 1][1])) { // 标准循环下做错了

						defrepeat(1);
						textViewdef1.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						myapp.addwrongword(words[wordnum - 1]);

						String[][] wrongwords = myapp.getwrongwords();
						System.out.println(wrongwords[0][0]);

						System.out.println(wrongwords[1][0]);

						System.out.println(wrongwords[2][0]);

						Intent intent = new Intent(definitionl2.this, definitionl2.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}
			}
		});

		textViewdef2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String key = (String) textViewdef2.getText();

				if (wcon == 1) { // 错词循环下

					if (key.equals(words[wordnum - 1][1])) { // 做对了

						textViewdef2.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						defrepeat(0);
						if (!words[wordnum][0].equals("")) {

							System.out.println("错词循环下一个");
							myapp.set(4, Integer.toString(wordnum + 1));
							Intent intent = new Intent(definitionl2.this, idroortsl2.class);
							startActivity(intent);
							// playmusic(1);

							finish();

						}

						if (words[wordnum][0].equals("")) { // 错词循环结束了

							System.out.println("错词循环结束");
							Intent intent = new Intent(definitionl2.this, score.class);

							startActivity(intent);
							// playmusic(1);

							finish();

						}

					}

					if (!key.equals(words[wordnum - 1][1])) { // 错词循环下做错了

						textViewdef2.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						defrepeat(1); // 这是做错了加1
						Intent intent = new Intent(definitionl2.this, definitionl2.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}

				if (wcon == 0) { // 标准循环下

					if (key.equals(words[wordnum - 1][1])) {// 标准循环下 做对了

						defrepeat(0);
						textViewdef2.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						if (con == 1) { // 标准循环+ TT循环下

							if (wordnum % 5 != 0) { // 没有走完5个单词

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(definitionl2.this,
										idroortsl2.class);
								startActivity(intent);

								// playmusic(1);
								finish();

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

									/*
									 * Toast.makeText( definitionl2.this,
									 * "OK, This list words is over, we will enter REVIEW step"
									 * , 1).show();
									 */

									myapp.set(4, Integer.toString(1)); // 进入review
																		// wrong
																		// 循环
																		// wordnumber
																		// 给1

									myapp.setreviewwrongcontrol(1); // 设定进入reviewwrong
																	// wcon赋值为1

									String[][] wrongwords = myapp
											.getCwrongwords();

									System.out.println(wrongwords[0][0]);

									System.out.println(wrongwords[1][0]);

									System.out.println(wrongwords[2][0]); // 便于测试使用本段

									System.out.println(wrongwords[3][0]);

									System.out.println(wrongwords[4][0]);

									if (!wrongwords[0][0].equals("")) {
										Intent intent = new Intent(definitionl2.this,
												idroortsl2.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

									if (wrongwords[0][0].equals("")) {

										Intent intent = new Intent(definitionl2.this,
												score.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

								}

								if (!words[wordnum][0].equals("")) { // 标准
																		// 本次TT循环结束 但前两个循环还有单词

									double h = Math.random();

									 if (h < 0.5) {
									Intent intent = new Intent(definitionl2.this,
											wordsl2.class);
									startActivity(intent);

									// playmusic(1);
									finish();

									 } else {
									 Intent intent = new Intent(definitionl2.this,
									 definitionl2.class);
									 startActivity(intent);finish();finish();

									 }
								}
							}
						}

						if (con == 0) { // 标准循环 and 非TT循环

							if (wordnum % 5 != 0) {
								Intent intent = new Intent(definitionl2.this,
										idroortsl2.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}
							if (wordnum % 5 == 0) {
								Intent intent = new Intent(definitionl2.this,
										idroortsl2.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}

						}

					}
					if (!key.equals(words[wordnum - 1][1])) { // 标准循环下做错了

						defrepeat(1);
						textViewdef2.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						myapp.addwrongword(words[wordnum - 1]);

						String[][] wrongwords = myapp.getwrongwords();
						System.out.println(wrongwords[0][0]);

						System.out.println(wrongwords[1][0]);

						System.out.println(wrongwords[2][0]);

						Intent intent = new Intent(definitionl2.this, definitionl2.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}
			}
		});
		textViewdef3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String key = (String) textViewdef3.getText();

				if (wcon == 1) { // 错词循环下

					if (key.equals(words[wordnum - 1][1])) { // 做对了

						textViewdef3.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						defrepeat(0);
						if (!words[wordnum][0].equals("")) {

							System.out.println("错词循环下一个");
							myapp.set(4, Integer.toString(wordnum + 1));
							Intent intent = new Intent(definitionl2.this, idroortsl2.class);
							startActivity(intent);
							// playmusic(1);

							finish();

						}

						if (words[wordnum][0].equals("")) { // 错词循环结束了

							System.out.println("错词循环结束");
							Intent intent = new Intent(definitionl2.this, score.class);

							startActivity(intent);
							// playmusic(1);

							finish();

						}

					}

					if (!key.equals(words[wordnum - 1][1])) { // 错词循环下做错了

						textViewdef3.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						defrepeat(1); // 这是做错了加1
						Intent intent = new Intent(definitionl2.this, definitionl2.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}

				if (wcon == 0) { // 标准循环下

					if (key.equals(words[wordnum - 1][1])) {// 标准循环下 做对了

						defrepeat(0);
						textViewdef3.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						if (con == 1) { // 标准循环+ TT循环下

							if (wordnum % 5 != 0) { // 没有走完5个单词

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(definitionl2.this,
										idroortsl2.class);
								startActivity(intent);

								// playmusic(1);
								finish();

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

									/*
									 * Toast.makeText( definitionl2.this,
									 * "OK, This list words is over, we will enter REVIEW step"
									 * , 1).show();
									 */

									myapp.set(4, Integer.toString(1)); // 进入review
																		// wrong
																		// 循环
																		// wordnumber
																		// 给1

									myapp.setreviewwrongcontrol(1); // 设定进入reviewwrong
																	// wcon赋值为1

									String[][] wrongwords = myapp
											.getCwrongwords();

									System.out.println(wrongwords[0][0]);

									System.out.println(wrongwords[1][0]);

									System.out.println(wrongwords[2][0]); // 便于测试使用本段

									System.out.println(wrongwords[3][0]);

									System.out.println(wrongwords[4][0]);

									if (!wrongwords[0][0].equals("")) {
										Intent intent = new Intent(definitionl2.this,
												idroortsl2.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

									if (wrongwords[0][0].equals("")) {

										Intent intent = new Intent(definitionl2.this,
												score.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

								}

								if (!words[wordnum][0].equals("")) { // 标准
																		// 本次TT循环结束 但前两个循环还有单词

									double h = Math.random();

									 if (h < 0.5) {
									Intent intent = new Intent(definitionl2.this,
											wordsl2.class);
									startActivity(intent);

									// playmusic(1);
									finish();

									 } else {
									 Intent intent = new Intent(definitionl2.this,
									 definitionl2.class);
									 startActivity(intent);finish();finish();

									 }
								}
							}
						}

						if (con == 0) { // 标准循环 and 非TT循环

							if (wordnum % 5 != 0) {
								Intent intent = new Intent(definitionl2.this,
										idroortsl2.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}
							if (wordnum % 5 == 0) {
								Intent intent = new Intent(definitionl2.this,
										idroortsl2.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}

						}

					}
					if (!key.equals(words[wordnum - 1][1])) { // 标准循环下做错了

						defrepeat(1);
						textViewdef3.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						myapp.addwrongword(words[wordnum - 1]);

						String[][] wrongwords = myapp.getwrongwords();
						System.out.println(wrongwords[0][0]);

						System.out.println(wrongwords[1][0]);

						System.out.println(wrongwords[2][0]);

						Intent intent = new Intent(definitionl2.this, definitionl2.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}
			}
		});

	}

	private void defrepeat(int key) {

		if (key == 0) {
			myapp.set(6, Integer.toString(0));
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



		System.out.println(a1 + "+" + a2 + "+" + a3);

		if (key == 1) {
			textViewdef1.setText(words[wordnum - 1][1]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				textViewdef1.setBackgroundResource(R.drawable.green);
		//		myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);

			}
			textViewdef2.setText(roots[a2]);
			textViewdef3.setText(roots[a3]);
			/*wordTextView4.setText(roots[a4]);*/
		}

		if (key == 2) {
			textViewdef2.setText(words[wordnum - 1][1]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				textViewdef2.setBackgroundResource(R.drawable.green);
			//	myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);
			}

			textViewdef1.setText(roots[a3]);
			textViewdef3.setText(roots[a2]);
			/*wordTextView4.setText(roots[a4]);*/
		}

		if (key == 3) {
			textViewdef3.setText(words[wordnum - 1][1]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				textViewdef3.setBackgroundResource(R.drawable.green);
			//	myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);
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

	public void playmusic(int key) {

		if (key == 1) {

			myapp.playmusic(1);
		}

		if (key == 0) {

			myapp.playmusic(0);

		}

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

									Intent intent = new Intent(definitionl2.this,
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
									/*
									 * Intent intent= new
									 * Intent(Word.this,Play.class);
									 * startActivity(intent);finish();
									 */
									alertdDialog.cancel();
								}
							}).create();

			alertdDialog.show();

		}

		return super.onKeyDown(keyCode, event);
	}



}
