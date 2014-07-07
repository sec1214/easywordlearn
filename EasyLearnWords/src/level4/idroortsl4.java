package level4;

import com.easylearnwords.R;
import com.easylearnwords.mypublicvalue;
import com.easylearnwords.play;
import com.easylearnwords.root;

import android.R.integer;
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

public class idroortsl4 extends Activity {

	private Dialog alertdDialog;
	private TextView textView1, textView2, wordTextView;
	private TextView root1, root2, root3, root4, root5, root6;
	private TextView textViewlevel, textViewword, textViewwr;
	private String[][] words;
	private int wordnum;
	private int rootnum; // 统计这里有多少个root 每个word，跟root（）和word（）里头不一样
	private String[] roots;
	private int numroot;
	public int wcon, con; // 错词循环， 不需要TT循环con
	private int clicknum = 0;

	private mypublicvalue myapp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.idroots);
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

		textView1.setText(myapp.get(0));
		textView2.setText(myapp.get(1));

		textViewlevel.setText(" Level: " + myapp.get(3)); // 设定显示level的控件

		wordnum = Integer.parseInt(myapp.get(4));

		textViewword.setText("Word: " + wordnum + "  "); // 设定显示几号word的控件

	   wcon=myapp.getreviewwrongcontrol();
	
			words = myapp.getwords();
		
		

		wordTextView = (TextView) this.findViewById(R.id.wordtextview);

		String word = words[wordnum - 1][0];
		wordTextView.setText(word);

		this.ran();

		System.out.println("idroots 这个word多少个root： " + rootnum);

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
						System.out.println("过了idrootsl4");
						Intent intent = new Intent(idroortsl4.this,
								rootl4.class);
						startActivity(intent);
						finish();
						

					}

				} else {
					root1.setBackgroundResource(R.drawable.red);
					
					if (wcon==0) {
						myapp.addwrongword(words[wordnum - 1]);
					}
					
					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(idroortsl4.this,
							idroortsl4.class);
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
					if (clicknum == rootnum) {
						defrepeat(0);
						System.out.println("过了idrootsl2");
						Intent intent = new Intent(idroortsl4.this,
								rootl4.class);
						startActivity(intent);
						finish();

					}

				} else {
					root2.setBackgroundResource(R.drawable.red);
					if (wcon==0) {
						myapp.addwrongword(words[wordnum - 1]);
					}
					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(idroortsl4.this,
							idroortsl4.class);
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
					if (clicknum == rootnum) {
						defrepeat(0);
						System.out.println("过了idrootsl4");
						Intent intent = new Intent(idroortsl4.this,
								rootl4.class);
						startActivity(intent);
						finish();

					}

				} else {
					
					
					
					root3.setBackgroundResource(R.drawable.red);
					if (wcon==0) {
						myapp.addwrongword(words[wordnum - 1]);
					}
					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(idroortsl4.this,
							idroortsl4.class);
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
					if (clicknum == rootnum) {
						defrepeat(0);
						System.out.println("过了idrootsl4");
						Intent intent = new Intent(idroortsl4.this,
								rootl4.class);
						startActivity(intent);
						finish();

					}

				} else {
					root4.setBackgroundResource(R.drawable.red);
					if (wcon==0) {
						myapp.addwrongword(words[wordnum - 1]);
					}
					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(idroortsl4.this,
							idroortsl4.class);
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
					if (clicknum == rootnum) {
						defrepeat(0);
						System.out.println("过了idrootsl4");
						Intent intent = new Intent(idroortsl4.this,
								rootl4.class);
						startActivity(intent);
						finish();

					}

				} else {
					root5.setBackgroundResource(R.drawable.red);
					if (wcon==0) {
						myapp.addwrongword(words[wordnum - 1]);
					}
					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(idroortsl4.this,
							idroortsl4.class);
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
					if (clicknum == rootnum) {
						defrepeat(0);
						System.out.println("过了idrootsl4");
						Intent intent = new Intent(idroortsl4.this,
								rootl4.class);
						startActivity(intent);
						finish();

					}

				} else {
					root6.setBackgroundResource(R.drawable.red);
					if (wcon==0) {
						myapp.addwrongword(words[wordnum - 1]);
					}
					myapp.playmusic(0);
					defrepeat(1);
					Intent intent = new Intent(idroortsl4.this,
							idroortsl4.class);
					startActivity(intent);
					finish();
				}

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

	//	System.out.println(a1+"+"+a2+"+"+a3+"+"+a4+"+"+a5+"+"+a6);
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
		
		System.out.println(a1+"+"+a2+"+"+a3+"+"+a4+"+"+a5+"+"+a6);
		
		if (a2==0) {
			rootnum=1;
		}
		if (a2 != 0 && a3 == 0) {
			rootnum=2;
		}
		if (a2 != 0 && a3 != 0 && a4 == 0) {
			rootnum=3;
		}
		if (a2 != 0 && a3 != 0 && a4 != 0 && a5 == 0) {
		rootnum=4;	
		}
		
		
		

		System.out.println("本次循环过");

		if (rootnum == 1) {    //证明只有一个root

			
			System.out.println("rootnum   "+1);

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

		if (rootnum == 2) {  //证明有2个root 分别是a1 和 a2

			
			
			System.out.println("rootnum   "+2);

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
					root1.setBackgroundResource(R.drawable.green); //a1
					root2.setBackgroundResource(R.drawable.green); //a2
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

		if (rootnum == 3) {  //证明有3个root

			
			System.out.println("rootnum   "+3);

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

		if (rootnum == 4) { //4个root

			
			System.out.println("rootnum   "+4);

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

									Intent intent = new Intent(idroortsl4.this,
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
