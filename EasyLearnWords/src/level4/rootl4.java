package level4;

import com.easylearnwords.R;
import com.easylearnwords.definition;
import com.easylearnwords.mypublicvalue;
import com.easylearnwords.play;
import com.easylearnwords.root;
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

public class rootl4 extends Activity {


	/*private SoundPool sp;// 声明一个SoundPool
	private int musicright;// 定义一个整型用load（）；来设置suondID
	private int musicwrong;
*/
	private Dialog alertdDialog;
	private TextView textView1, textView2, rootTextView;
	private TextView rootdef1, rootdef2, rootdef3, rootdef4, rootdef5,
			rootdef6;
	private TextView textViewlevel, textViewword, textViewwr;
	private mypublicvalue myapp;
	private String[][] words;
	private int wordnum;
	private int rootnum;
	private String[] roots;
	private int numroot;
/*	private ArrayAdapter<String> adapter;*/

/*	private List<String> list;*/

	public int wcon, con;

/*	private int rootnumber;*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.root);

		/*sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);// 第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量

		musicright = sp.load(this, R.raw.right1, 1); // 把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
		musicwrong = sp.load(this, R.raw.wrong1, 1); // 把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
*/
		textView1 = (TextView) this.findViewById(R.id.textview1);
		textView2 = (TextView) this.findViewById(R.id.textview2);

		textViewlevel = (TextView) this.findViewById(R.id.leveltext);
		textViewword = (TextView) this.findViewById(R.id.wordtext);
		textViewwr = (TextView) this.findViewById(R.id.wrtext);
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

		textViewword.setText("Word: " + wordnum + "  "); // 设定显示几号word的控件

		wcon = myapp.getreviewwrongcontrol(); // 复习控制阀值
		con = myapp.getrepeatcontrol();

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

			words = myapp.getCwrongwords();
			textViewwr.setText("Wrong Reivew");
			textViewwr.setBackgroundColor(Color.RED);

		}

		rootTextView = (TextView) this.findViewById(R.id.roottestview);

		rootnum = Integer.parseInt(myapp.get(5)); // step
		String word = words[wordnum - 1][rootnum];
		rootTextView.setText(word);

		System.out.println(myapp.getrepeatcontrol() + "Root");

		this.ran();

		rootdef1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String k = (String) rootdef1.getText();

				if (wcon == 1) {

					if (k.equals(words[wordnum - 1][rootnum + 1])) { // 选对了

						defrepeat(0);
						rootdef1.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						// 变色

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);
							// playmusic(1);

							finish();
						}

						if (words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(2)); // root没有了，需要重置

							System.out.println("wcon" + wcon);
							System.out.println("进入word步骤");

							double h = Math.random();

							
							 if (h < 0.5) {

							/*
							 * if (wcon == 1) { Intent intent = new
							 * Intent(rootl2.this, WrongWord.class);
							 * startActivity(intent); finish(); } if (wcon == 0)
							 * {
							 */

							Intent intent = new Intent(rootl4.this, wordsl4.class);
							startActivity(intent);

							// playmusic(1);
							finish();

							 } else {
							 Intent intent = new Intent(rootl4.this,
							 definitionl4.class);
							 startActivity(intent); finish();
							 }

						}

					}

					if (!k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(1);
						rootdef1.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						// myapp.addwrongword(words[wordnum-1]);

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}

				if (wcon == 0) {

					if (k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(0);
						rootdef1.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);

							// playmusic(1);
							finish();
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

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);
						finish();

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
						myapp.playmusic(1);
						// 变色

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);

							// playmusic(1);
							finish();
						}

						if (words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(2)); // root没有了，需要重置

							System.out.println("wcon" + wcon);
							System.out.println("进入word步骤");

							double h = Math.random();

							 if (h < 0.5) {
							/*
							 * if (wcon == 1) { Intent intent = new
							 * Intent(rootl2.this, WrongWord.class);
							 * startActivity(intent); finish(); } if (wcon == 0)
							 * {
							 */

							Intent intent = new Intent(rootl4.this, wordsl4.class);
							startActivity(intent);

							// playmusic(1);
							finish();

							 } else {
							 Intent intent = new Intent(rootl4.this,
							 definitionl4.class);
							 startActivity(intent); finish();
							 }

						}

					}

					if (!k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(1);
						rootdef2.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						// myapp.addwrongword(words[wordnum-1]);

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);

						finish();

					}

				}

				if (wcon == 0) {

					if (k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(0);
						rootdef2.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);

							// playmusic(1);
							finish();
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

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);
						finish();

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
						myapp.playmusic(1);
						// playmusic(1); // 变色

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);

							// playmusic(1);
							finish();
						}

						if (words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(2)); // root没有了，需要重置

							System.out.println("wcon" + wcon);
							System.out.println("进入word步骤");
							double h = Math.random();

							 if (h < 0.5) {
							/*
							 * if (wcon == 1) { Intent intent = new
							 * Intent(rootl2.this, WrongWord.class);
							 * startActivity(intent); finish(); } if (wcon == 0)
							 * {
							 */

							Intent intent = new Intent(rootl4.this, wordsl4.class);
							startActivity(intent);

							// playmusic(1);
							finish();

							 } else {
							 Intent intent = new Intent(rootl4.this,
							 definitionl4.class);
							 startActivity(intent); finish();
							 }

						}

					}

					if (!k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(1);
						rootdef3.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);
						// playmusic(0);
						;

						// myapp.addwrongword(words[wordnum-1]);

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);

						finish();

					}

				}

				if (wcon == 0) {

					if (k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(0);
						rootdef3.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						// playmusic(1);

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);

							// playmusic(1);
							finish();
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

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);

						finish();

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
						myapp.playmusic(1);
						// playmusic(1); // 变色

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);

							// playmusic(1);
							finish();
						}

						if (words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(2)); // root没有了，需要重置

							System.out.println("wcon" + wcon);
							System.out.println("进入word步骤");
							
							double h = Math.random();

							
							 if (h < 0.5) {
							/*
							 * if (wcon == 1) { Intent intent = new
							 * Intent(rootl2.this, WrongWord.class);
							 * startActivity(intent); finish(); } if (wcon == 0)
							 * {
							 */

							Intent intent = new Intent(rootl4.this, wordsl4.class);
							startActivity(intent);
							// playmusic(1);
							finish();

							 } else {
							 Intent intent = new Intent(rootl4.this,
							 definitionl4.class);
							 startActivity(intent); finish();
							 }

						}

					}

					if (!k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(1);
						rootdef4.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);
						// playmusic(0);

						// myapp.addwrongword(words[wordnum-1]);

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);

						finish();

					}

				}

				if (wcon == 0) {

					if (k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(0);
						rootdef4.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);
						// playmusic(1);

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);
							// playmusic(1);

							finish();
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

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);

						finish();

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
						myapp.playmusic(1);
						// playmusic(1); // 变色

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);

							// playmusic(1);
							finish();
						}

						if (words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(2)); // root没有了，需要重置

							System.out.println("wcon" + wcon);
							System.out.println("进入word步骤");

							double h = Math.random();

							 if (h < 0.5) {
							/*
							 * if (wcon == 1) { Intent intent = new
							 * Intent(rootl2.this, WrongWord.class);
							 * startActivity(intent); finish(); } if (wcon == 0)
							 * {
							 */

							Intent intent = new Intent(rootl4.this, wordsl4.class);
							startActivity(intent);

							// playmusic(1);
							finish();

							 } else {
							 Intent intent = new Intent(rootl4.this,
							 definitionl4.class);
							 startActivity(intent); finish();
							 }

						}

					}

					if (!k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(1);
						rootdef5.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						// myapp.addwrongword(words[wordnum-1]);

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);

						finish();

					}

				}

				if (wcon == 0) {

					if (k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(0);
						rootdef5.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);

							// playmusic(1);
							finish();
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

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);

						finish();

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
						myapp.playmusic(1);
						// 变色

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);

							// playmusic(1);
							finish();
						}

						if (words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(2)); // root没有了，需要重置

							System.out.println("wcon" + wcon);
							System.out.println("进入word步骤");

							double h = Math.random();

							 if (h < 0.5) {
							/*
							 * if (wcon == 1) { Intent intent = new
							 * Intent(rootl2.this, WrongWord.class);
							 * startActivity(intent); finish(); } if (wcon == 0)
							 * {
							 */

							Intent intent = new Intent(rootl4.this, wordsl4.class);
							startActivity(intent);

							// playmusic(1);
							finish();

							 } else {
							 Intent intent = new Intent(rootl4.this,
							 definitionl4.class);
							 startActivity(intent); finish();
							 }

						}

					}

					if (!k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(1);
						rootdef6.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						// myapp.addwrongword(words[wordnum-1]);

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);

						finish();

					}

				}

				if (wcon == 0) {

					if (k.equals(words[wordnum - 1][rootnum + 1])) {

						defrepeat(0);
						rootdef6.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						if (!words[wordnum - 1][rootnum + 2].equals("")) {

							myapp.set(5, Integer.toString(rootnum + 2));

							Intent intent = new Intent(rootl4.this, rootl4.class);

							startActivity(intent);

							// playmusic(1);
							finish();
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

						Intent intent = new Intent(rootl4.this, rootl4.class);
						startActivity(intent);

						// playmusic(0);

						finish();

					}

				}
			}
		});

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
		/*adapter = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_single_choice, list);*/

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

	/*
	 * private int rand() { double h = Math.random() * numroot;
	 * 
	 * int k = (int) h; return k;
	 * 
	 * }
	 */

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

		System.out.println("本次循环过");

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
			//	myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);
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
			//	myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);
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
			//	myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);
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
			//	myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);
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
			//	myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);
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
				//myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);
			}
		}

	}

	public void repeattt5() {
		// 这条选择代表root后没有root了代表走到头啦

		if (wordnum % 5 == 0) { // 代表走到5个词了

			if (myapp.getrepeatcontrol() == 1) { // 判断是否进入tt循环 //在TT循环中

				myapp.set(5, Integer.toString(2)); // root没有了，需要重置

				double h = Math.random();

				 if (h < 0.5) {
				Intent intent = new Intent(rootl4.this, wordsl4.class);
				startActivity(intent);

				// playmusic(1);

				finish();
				 } else {
				 Intent intent = new Intent(rootl4.this,
				 definitionl4.class);
				
				 startActivity(intent); finish();
				 
				 }

			}

			if (myapp.getrepeatcontrol() == 0) { // 判断进入tt循环

				myapp.set(4, Integer.toString(wordnum - 4));
				myapp.set(5, Integer.toString(2)); // root没有了，需要重置
				myapp.setrepreatcontrol(1);
				Intent intent = new Intent(rootl4.this, rootl4.class);
				startActivity(intent);

				// playmusic(1);
				finish();

			}

		}
		if (wordnum % 5 != 0) {

			myapp.set(5, Integer.toString(2)); // root没有了，需要重置

			if (myapp.getrepeatcontrol() == 1) { // 证明在tt循环中
				double h = Math.random();

				 if (h < 0.5) {
				Intent intent = new Intent(rootl4.this, wordsl4.class);
				startActivity(intent);

				// playmusic(1);
				finish();
				 } else {
				 Intent intent = new Intent(rootl4.this,
				 definitionl4.class);
				 startActivity(intent); finish();
				 }

			}

			if (myapp.getrepeatcontrol() == 0) {

				myapp.set(4, Integer.toString(wordnum + 1));
				double h = Math.random();

				 if (h < 0.5) {
				Intent intent = new Intent(rootl4.this, wordsl4.class);
				startActivity(intent);

				// playmusic(1);
				finish();
				 } else {
				 Intent intent = new Intent(rootl4.this,
				 definitionl4.class);
				 startActivity(intent); finish();
				 }

			}
		}

	}

	public void playmusic(int key) {

		if (key == 1) {

			myapp.playmusic(1);

			/*
			 * sp.play(musicright, 1, 1, 0, 0, 1);
			 * 
			 * Timer timer=new Timer();
			 * 
			 * TimerTask task=new TimerTask() {
			 * 
			 * @Override public void run() { // TODO Auto-generated method stub
			 * sp.unload(musicright); } };
			 * 
			 * timer.schedule(task, 900); timer.cancel();
			 */

		} else if (key == 0) {

			myapp.playmusic(0);

			/*
			 * sp.play(musicwrong, 1, 1, 0, 0, 1);
			 * 
			 * Timer timer=new Timer();
			 * 
			 * TimerTask task=new TimerTask() {
			 * 
			 * @Override public void run() { // TODO Auto-generated method stub
			 * sp.release(); sp.unload(musicwrong); } };
			 * 
			 * timer.schedule(task, 900); timer.cancel();
			 */
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

									Intent intent = new Intent(rootl4.this,
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
									 * startActivity(intent);
									 */
									alertdDialog.cancel();
								}
							}).create();

			alertdDialog.show();

		}

		return super.onKeyDown(keyCode, event);
	}


	
	

}
