package level3;



import com.easylearnwords.R;
import com.easylearnwords.mypublicvalue;
import com.easylearnwords.play;
import com.easylearnwords.score;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class missroot extends Activity {

	private Dialog alertdDialog;
	private TextView textView1, textView2, missrootwordTextView,
			missrootworddeftTextView;
	private TextView root1, root2, root3, root4, root5, root6;
	private TextView textViewlevel, textViewword, textViewwr;
	private String[][] words;
	private int wordnum;
	private int rootnum = 0; // 统计这里有多少个root 每个word，跟root（）和word（）里头不一样
	private String[] roots;
	private int numroot;
	public int wcon, con; // 错词循环， 不需要TT循环con
	private int clicknum = 0;
	private int rootstep;
	private String[][] ranwords = new String[21][10];
	private mypublicvalue myapp;
	private int controllv3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.missroot);

		textView1 = (TextView) this.findViewById(R.id.textview1);
		textView2 = (TextView) this.findViewById(R.id.textview2);

		textViewlevel = (TextView) this.findViewById(R.id.leveltext);
		textViewword = (TextView) this.findViewById(R.id.wordtext);
		textViewwr = (TextView) this.findViewById(R.id.wrtext);
		root1 = (TextView) this.findViewById(R.id.wordroot1);
		root2 = (TextView) this.findViewById(R.id.wordroot2);
		root3 = (TextView) this.findViewById(R.id.wordroot3);
		root4 = (TextView) this.findViewById(R.id.wordroot4);
		root5 = (TextView) this.findViewById(R.id.wordroot5);
		root6 = (TextView) this.findViewById(R.id.wordroot6);

		myapp = (mypublicvalue) getApplication();

		ranwords = myapp.emptywords();

		textView1.setText(myapp.get(0));
		textView2.setText(myapp.get(1));

		textViewlevel.setText(" Level: " + myapp.get(3)); // 设定显示level的控件

		wordnum = Integer.parseInt(myapp.get(4));

		rootstep = Integer.parseInt(myapp.get(5));

		textViewword.setText("Word: " + wordnum + "  "); // 设定显示几号word的控件

		wcon = myapp.getreviewwrongcontrol(); // 复习控制阀值
		con = myapp.getrepeatcontrol();

		controllv3 = Integer.parseInt(myapp.get(7));

		if (controllv3 == 0) {
			words = myapp.getwords();
			words = getranword();

			System.out.println("ranwords设定" + words[0][0]);

			myapp.setranwords(words);

	

			myapp.set(7, "1");

		}
		if (controllv3 == 1) {
			System.out.println("走了controll3 等于1的这一步");
			words = myapp.getranwords();
		/*	System.out.println("ranwords读出" + words[0][0]);*/
		}

		/*
		 * managedb db= new managedb(getBaseContext());
		 * myapp.setwords(db.getwords());
		 */

		// myapp.getranword(); //application操作会把所有的值破坏，需要重新赋值

		missrootworddeftTextView = (TextView) this
				.findViewById(R.id.missrootworddefview);
		missrootworddeftTextView.setText(words[wordnum - 1][1]);

		missrootwordTextView = (TextView) this
				.findViewById(R.id.missrootwordview);

		if (rootstep == 2) {
			rootnum = 1;
			missrootwordTextView.setText(getchangeword(1));

		}
		if (rootstep == 4) {
			rootnum = 2;
			missrootwordTextView.setText(getchangeword(2));
		}
		if (rootstep == 6) {
			rootnum = 3;
			missrootwordTextView.setText(getchangeword(3));
		}
		if (rootstep == 8) {
			rootnum = 4;
			missrootwordTextView.setText(getchangeword(4));
		}

		this.ran();

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
					myapp.playmusic(1);
					clicknum++;

					if (clicknum == rootnum) { // 都做对了

						defrepeat(0);
						/* System.out.println("过了missroot"); */

						if (words[wordnum - 1][rootstep + 2].equals("")) {

							
								myapp.set(4, Integer.toString(wordnum + 1));// 下一个词
								myapp.set(5, Integer.toString(2)); // root没有了需要重置
							
							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										score.class);
								startActivity(intent);
								finish();

							}
							if (!words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										missroot.class);
								startActivity(intent);
								finish();
							}

						}

						if (!words[wordnum - 1][rootstep + 2].equals("")) {
							myapp.set(5, Integer.toString(rootstep + 2));
							System.out.println("过了missroot");
							Intent intent = new Intent(missroot.this,
									missroot.class);
							startActivity(intent);
							finish();
						}

						

					}

				} else {
					root1.setBackgroundResource(R.drawable.red);

					if (wcon == 0) {
						myapp.addwrongword(words[wordnum - 1]);
					}

					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(missroot.this, missroot.class);
					startActivity(intent);
					finish();

				}

			}
		});
		root2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String k = (String) root2.getText();
				if (k.equals(words[wordnum - 1][2])
						|| k.equals(words[wordnum - 1][4])
						|| k.equals(words[wordnum - 1][6])
						|| k.equals(words[wordnum - 1][8]))

				{
					root2.setBackgroundResource(R.drawable.green);
					myapp.playmusic(1);
					clicknum++;

					if (clicknum == rootnum) { // 都做对了

						defrepeat(0);
						/* System.out.println("过了missroot"); */

						if (words[wordnum - 1][rootstep + 2].equals("")) {

							
								myapp.set(4, Integer.toString(wordnum + 1));// 下一个词
								myapp.set(5, Integer.toString(2)); // root没有了需要重置
							
							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										score.class);
								startActivity(intent);
								finish();

							}
							if (!words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										missroot.class);
								startActivity(intent);
								finish();
							}

						}

						if (!words[wordnum - 1][rootstep + 2].equals("")) {
							myapp.set(5, Integer.toString(rootstep + 2));
							System.out.println("过了missroot");
							Intent intent = new Intent(missroot.this,
									missroot.class);
							startActivity(intent);
							finish();
						}

						

					}

				} else {
					root2.setBackgroundResource(R.drawable.red);
					if (wcon == 0) {
						myapp.addwrongword(words[wordnum - 1]);
					}
					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(missroot.this, missroot.class);
					startActivity(intent);
					finish();
				}

			}
		});
		root3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String k = (String) root3.getText();
				if (k.equals(words[wordnum - 1][2])
						|| k.equals(words[wordnum - 1][4])
						|| k.equals(words[wordnum - 1][6])
						|| k.equals(words[wordnum - 1][8]))

				{
					root3.setBackgroundResource(R.drawable.green);
					myapp.playmusic(1);
					clicknum++;

					if (clicknum == rootnum) { // 都做对了

						defrepeat(0);
						/* System.out.println("过了missroot"); */

						if (words[wordnum - 1][rootstep + 2].equals("")) {

							
								myapp.set(4, Integer.toString(wordnum + 1));// 下一个词
								myapp.set(5, Integer.toString(2)); // root没有了需要重置
							
							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										score.class);
								startActivity(intent);
								finish();

							}
							if (!words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										missroot.class);
								startActivity(intent);
								finish();
							}

						}

						if (!words[wordnum - 1][rootstep + 2].equals("")) {
							myapp.set(5, Integer.toString(rootstep + 2));
							System.out.println("过了missroot");
							Intent intent = new Intent(missroot.this,
									missroot.class);
							startActivity(intent);
							finish();
						}

						

					}

				} else {

					root3.setBackgroundResource(R.drawable.red);
					if (wcon == 0) {
						myapp.addwrongword(words[wordnum - 1]);
					}
					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(missroot.this, missroot.class);
					startActivity(intent);
					finish();
				}

			}
		});
		root4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String k = (String) root4.getText();
				if (k.equals(words[wordnum - 1][2])
						|| k.equals(words[wordnum - 1][4])
						|| k.equals(words[wordnum - 1][6])
						|| k.equals(words[wordnum - 1][8]))

				{
					root4.setBackgroundResource(R.drawable.green);
					myapp.playmusic(1);
					clicknum++;

					if (clicknum == rootnum) { // 都做对了

						defrepeat(0);
						/* System.out.println("过了missroot"); */

						if (words[wordnum - 1][rootstep + 2].equals("")) {

							
								myapp.set(4, Integer.toString(wordnum + 1));// 下一个词
								myapp.set(5, Integer.toString(2)); // root没有了需要重置
							
							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										score.class);
								startActivity(intent);
								finish();

							}
							if (!words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										missroot.class);
								startActivity(intent);
								finish();
							}

						}

						if (!words[wordnum - 1][rootstep + 2].equals("")) {
							myapp.set(5, Integer.toString(rootstep + 2));
							System.out.println("过了missroot");
							Intent intent = new Intent(missroot.this,
									missroot.class);
							startActivity(intent);
							finish();
						}

						

					}

				} else {
					root4.setBackgroundResource(R.drawable.red);
					if (wcon == 0) {
						myapp.addwrongword(words[wordnum - 1]);
					}
					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(missroot.this, missroot.class);
					startActivity(intent);
					finish();
				}

			}
		});
		root5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String k = (String) root5.getText();
				if (k.equals(words[wordnum - 1][2])
						|| k.equals(words[wordnum - 1][4])
						|| k.equals(words[wordnum - 1][6])
						|| k.equals(words[wordnum - 1][8]))

				{
					root5.setBackgroundResource(R.drawable.green);
					myapp.playmusic(1);
					clicknum++;

					if (clicknum == rootnum) { // 都做对了

						defrepeat(0);
						/* System.out.println("过了missroot"); */

						if (words[wordnum - 1][rootstep + 2].equals("")) {

							
								myapp.set(4, Integer.toString(wordnum + 1));// 下一个词
								myapp.set(5, Integer.toString(2)); // root没有了需要重置
							
							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										score.class);
								startActivity(intent);
								finish();

							}
							if (!words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										missroot.class);
								startActivity(intent);
								finish();
							}

						}

						if (!words[wordnum - 1][rootstep + 2].equals("")) {
							myapp.set(5, Integer.toString(rootstep + 2));
							System.out.println("过了missroot");
							Intent intent = new Intent(missroot.this,
									missroot.class);
							startActivity(intent);
							finish();
						}

						

					}

				} else {
					root5.setBackgroundResource(R.drawable.red);
					if (wcon == 0) {
						myapp.addwrongword(words[wordnum - 1]);
					}
					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(missroot.this, missroot.class);
					startActivity(intent);
					finish();
				}

			}
		});
		root6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String k = (String) root6.getText();
				if (k.equals(words[wordnum - 1][2])
						|| k.equals(words[wordnum - 1][4])
						|| k.equals(words[wordnum - 1][6])
						|| k.equals(words[wordnum - 1][8]))

				{
					root6.setBackgroundResource(R.drawable.green);
					myapp.playmusic(1);
					clicknum++;

					if (clicknum == rootnum) { // 都做对了

						defrepeat(0);
						/* System.out.println("过了missroot"); */

						if (words[wordnum - 1][rootstep + 2].equals("")) {

							
								myapp.set(4, Integer.toString(wordnum + 1));// 下一个词
								myapp.set(5, Integer.toString(2)); // root没有了需要重置
							
							if (words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										score.class);
								startActivity(intent);
								finish();

							}
							if (!words[wordnum][0].equals("")) {
								Intent intent = new Intent(missroot.this,
										missroot.class);
								startActivity(intent);
								finish();
							}

						}

						if (!words[wordnum - 1][rootstep + 2].equals("")) {
							myapp.set(5, Integer.toString(rootstep + 2));
							System.out.println("过了missroot");
							Intent intent = new Intent(missroot.this,
									missroot.class);
							startActivity(intent);
							finish();
						}

						

					}

				} else {
					root6.setBackgroundResource(R.drawable.red); // 做错了的情况
					if (wcon == 0) {
						myapp.addwrongword(words[wordnum - 1]);
					}
					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(missroot.this, missroot.class);
					startActivity(intent);
					finish();
				}

			}
		});

	}

	private String getchangeword(int key) { // 本方法自动创建相对应的缺失root的字符串
		// TODO Auto-generated method stub
		int k = 2;  //起始root的地点
		String word = words[wordnum - 1][0];
		for (int i = 0; i < key; i++) {
			String changeword = words[wordnum - 1][k];
			String targetword = "";

			for (int j = 0; j < changeword.length(); j++) {
				targetword = targetword + "_";
			}
                targetword=targetword+" ";
			word = word.replace(changeword, targetword);

			k = k + 2;
		}

		System.out.println("替换的字符串" + word);

		/* String changeword = words[wordnum - 1][2]; */

		/* String targetword = ""; */
		/*
		 * for (int i = 0; i < changeword.length(); i++) { targetword =
		 * targetword + "_"; }
		 * 
		 * String word = words[wordnum - 1][0].replace(changeword, targetword);
		 */

		return word;
	}

	private void ran() {
		// TODO Auto-generated method stub
		numroot = 0; // 防止 最后出现不够20 出现的情况

		this.getroots();

		for (int i = 0; i < 80; i++) { // root最多80个

			if (!roots[i].equals("")) { // 一共取得了多少个可用的ROOT
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

		int a1, a2, a3, a4, a5, a6;
		double h;
		a1 = 0;
		a2 = 0;
		a3 = 0;
		a4 = 0;
		a5 = 0;
		a6 = 0;

		// System.out.println(a1+"+"+a2+"+"+a3+"+"+a4+"+"+a5+"+"+a6);
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

		System.out.println(a1 + "+" + a2 + "+" + a3 + "+" + a4 + "+" + a5 + "+"
				+ a6);

		/* if (a2==0) { */

		/*
		 * } if (a2 != 0 && a3 == 0) { rootnum=2; } if (a2 != 0 && a3 != 0 && a4
		 * == 0) { rootnum=3; } if (a2 != 0 && a3 != 0 && a4 != 0 && a5 == 0) {
		 * rootnum=4; }
		 */

		System.out.println("本次循环过");

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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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

					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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

					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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
					// myapp.playmusic(1);
					// //playmusic(1);
					defrepeat(0);
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

	public void randword(int key) {

		System.out.println(words[0][0]);
		System.out.println(words[1][0]);
		System.out.println(words[2][0]);
		System.out.println(words[3][0]);
		System.out.println(words[4][0]);

		System.out.println("————————————————");
		if (key == 5) {
			int r = 0;
			int len = 5;

			int k = 0;
			int x = 0;
			boolean[] f = new boolean[20];
			for (int i = 0; i < f.length; i++) {
				f[i] = true;
			}

			for (int i = 0; i < len; i++) {

				boolean find = false;

				while (!find) {
					double h = Math.random() * 20;
					x = (int) h;

					find = f[x];
					r = x;

				}

				f[r] = false;

				System.out.println("随机数" + r);

				if (words[x][0].equals("")) {
					len++;
				}
				if (!words[x][0].equals("")) {
					ranwords[k] = words[x];

					System.out.println(ranwords[k][0]);
					k++;
				}

			}

		}

		if (key == 10) {
			int r = 0;
			int len = 10;

			int k = 0;
			int x = 0;
			boolean[] f = new boolean[20];
			for (int i = 0; i < f.length; i++) {
				f[i] = true;
			}

			for (int i = 0; i < len; i++) {

				boolean find = false;

				while (!find) {
					double h = Math.random() * 20;
					x = (int) h;

					find = f[x];
					r = x;

				}

				f[r] = false;

				System.out.println("随机数" + r);

				if (words[x][0].equals("")) {
					len++;
				}
				if (!words[x][0].equals("")) {
					ranwords[k] = words[x];

					System.out.println(ranwords[k][0]);
					k++;
				}

			}

		}

	}

	public String[][] getranword() {
		int numwords = 0;

		for (int i = 0; i < words.length; i++) {
			if (!words[i][0].equals("")) {

				numwords++;

			}
		}

		if (numwords == 5) {
			randword(5);
		}

		if (numwords >= 10) {
			randword(10);
		}

		return ranwords;

	}

	private void defrepeat(int key) {

		if (key == 0) {
			myapp.set(6, Integer.toString(0));
		}

		if (key == 1) {
			myapp.set(6, Integer.toString((Integer.parseInt(myapp.get(6))) + 1));

		}

	}

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

									Intent intent = new Intent(missroot.this,
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
