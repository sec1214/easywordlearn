package com.easylearnwords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class wrongword extends Activity {
	private TextView textView1, textView2, defTextView, wordTextView1,
	wordTextView2, wordTextView3, wordTextView4;
private TextView textViewlevel, textViewword;
private mypublicvalue myapp;
private String[][] words;
private int wordnum; // 第几个word

private int wcon; // 复习控制阀
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.word);

	textView1 = (TextView) this.findViewById(R.id.textview1);
	textView2 = (TextView) this.findViewById(R.id.textview2);
	textViewlevel = (TextView) this.findViewById(R.id.leveltext);
	textViewword = (TextView) this.findViewById(R.id.wordtext);

	defTextView = (TextView) this.findViewById(R.id.definition);
	wordTextView1 = (TextView) this.findViewById(R.id.word1);
	wordTextView2 = (TextView) this.findViewById(R.id.word2);
	wordTextView3 = (TextView) this.findViewById(R.id.word3);
	wordTextView4 = (TextView) this.findViewById(R.id.word4);

	myapp = (mypublicvalue)getApplication();
	wcon = myapp.getreviewwrongcontrol(); // 复习控制阀值
	textView1.setText(myapp.get(0));
	textView2.setText(myapp.get(1));

	textViewlevel.setText(" Level: " + myapp.get(3)); // 设定显示level的控件

	wordnum = Integer.parseInt(myapp.get(4));

	textViewword.setText("Word: " + wordnum + "  "); // 设定显示几号word的控件
	
	words = myapp.getCwrongwords();
	
	defTextView.setText(words[wordnum - 1][1]);
	
	
	
	this.ran();
	
wordTextView1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		
		
			String key = (String) wordTextView1.getText();
			 
			if (key.equals(words[wordnum - 1][0])){   //做对了
				
				wordTextView1.setBackgroundResource(R.drawable.green);
				defrepeat(0);
				if (!words[wordnum][0].equals("")) {

					System.out.println("走了root1");
					myapp.set(4, Integer.toString(wordnum + 1));
					Intent intent = new Intent(wrongword.this, root.class);
					startActivity(intent);
				}
				
				if (words[wordnum][0].equals("")) {

					System.out.println("走了root2");
					Intent intent = new Intent(wrongword.this, score.class);
		//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				
				
			}
				
				
			}
			
			if (!key.equals(words[wordnum - 1][0])) {

				
				wordTextView1.setBackgroundResource(R.drawable.red);
				defrepeat(1);  //这是加1
				Intent intent = new Intent(wrongword.this, wrongword.class);
				startActivity(intent);
			
				
			}
			

			
		}
});


wordTextView2.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	
String key = (String) wordTextView2.getText();
		
		if (key.equals(words[wordnum - 1][0])){
			
			wordTextView2.setBackgroundResource(R.drawable.green);
			defrepeat(0);
			if (!words[wordnum][0].equals("")) {

				System.out.println("走了root1");
				myapp.set(4, Integer.toString(wordnum + 1));
				Intent intent = new Intent(wrongword.this, root.class);
				startActivity(intent);
			}
			
			if (words[wordnum][0].equals("")) {

				System.out.println("走了root2");
				Intent intent = new Intent(wrongword.this, score.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			
		}
		
		}
if (!key.equals(words[wordnum - 1][0])) {

			
			wordTextView2.setBackgroundResource(R.drawable.red);
			defrepeat(1);
			Intent intent = new Intent(wrongword.this, wrongword.class);
			startActivity(intent);
			
			
		}
		
			
		

	
	}
});

wordTextView3.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
String key = (String) wordTextView3.getText();
		
		if (key.equals(words[wordnum - 1][0])){
			
			wordTextView3.setBackgroundResource(R.drawable.green);
			defrepeat(0);
			if (!words[wordnum][0].equals("")) {

				System.out.println("走了root1");
				myapp.set(4, Integer.toString(wordnum + 1));
				Intent intent = new Intent(wrongword.this, root.class);
				startActivity(intent);
			}
			
			if (words[wordnum][0].equals("")) {

				System.out.println("走了root2");
				Intent intent = new Intent(wrongword.this, score.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			
			
		}
			
		}
		
	if (!key.equals(words[wordnum - 1][0])) {

			
			wordTextView3.setBackgroundResource(R.drawable.red);
			defrepeat(1);
			Intent intent = new Intent(wrongword.this, wrongword.class);
			startActivity(intent);
		
			
		}
		
		
	
	}
});

wordTextView4.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		String key = (String) wordTextView4.getText();
		
		if (key.equals(words[wordnum - 1][0])){
			
			wordTextView4.setBackgroundResource(R.drawable.green);
			defrepeat(0);
			if (!words[wordnum][0].equals("")) {

				System.out.println("走了root1");
				myapp.set(4, Integer.toString(wordnum + 1));
				Intent intent = new Intent(wrongword.this, root.class);
				startActivity(intent);
			}
			
			if (words[wordnum][0].equals("")) {

				System.out.println("走了root2");
				Intent intent = new Intent(wrongword.this, score.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			
			
		}
			
		}
		
		if (!key.equals(words[wordnum - 1][0])) {

			
			wordTextView4.setBackgroundResource(R.drawable.red);
			defrepeat(1);
			Intent intent = new Intent(wrongword.this, wrongword.class);
			startActivity(intent);
			
			
		
			
		}
		
		
		
	
	}
});


	

	/*wordTextView1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			key = (String) wordTextView1.getText();
			
			if (wcon == 1) {

				if (key.equals(words[wordnum - 1][0])) {

					defrepeat(0);
					wordTextView1.setBackgroundResource(R.drawable.green);
					myapp.set(4, Integer.toString(wordnum + 1));

					if (!words[wordnum][0].equals("")) {

						System.out.println("走了root1");

						Intent intent = new Intent(WrongWord.this, Root.class);
						startActivity(intent);
					}
					if (words[wordnum][0].equals("")) {

						System.out.println("走了root2");
						Intent intent = new Intent(WrongWord.this, Score.class);
			//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);

					}

				}
				if (!key.equals(words[wordnum - 1][0])) {

					defrepeat(1);
					wordTextView1.setBackgroundResource(R.drawable.red);

					// myapp.addwrongword(words[wordnum - 1]);

					Intent intent = new Intent(WrongWord.this, Word.class);
					startActivity(intent);

				}

			}
		}
	});
	
	
	wordTextView2.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			key = (String) wordTextView2.getText();
			
			if (wcon == 1) {

				if (key.equals(words[wordnum - 1][0])) {

					defrepeat(0);
					wordTextView2.setBackgroundResource(R.drawable.green);
					myapp.set(4, Integer.toString(wordnum + 1));

					if (!words[wordnum][0].equals("")) {

						System.out.println("走了root1");
						Intent intent = new Intent(WrongWord.this, Root.class);
						startActivity(intent);
					}
					if (words[wordnum][0].equals("")) {

						System.out.println("走了root2");
						Intent intent = new Intent(WrongWord.this, Score.class);
				//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);

					}

				}
				if (!key.equals(words[wordnum - 1][0])) {

					defrepeat(1);
					wordTextView2.setBackgroundResource(R.drawable.red);

					// myapp.addwrongword(words[wordnum - 1]);

					Intent intent = new Intent(WrongWord.this, Word.class);
					startActivity(intent);

				}

			}
		}
	});
	
	
	wordTextView3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			key = (String) wordTextView3.getText();
			if (wcon == 1) {

				if (key.equals(words[wordnum - 1][0])) {

					defrepeat(0);
					wordTextView3.setBackgroundResource(R.drawable.green);
					myapp.set(4, Integer.toString(wordnum + 1));

					if (!words[wordnum][0].equals("")) {

						System.out.println("走了root1");
						Intent intent = new Intent(WrongWord.this, Root.class);
						startActivity(intent);
					}
					if (words[wordnum][0].equals("")) {

						System.out.println("走了root2");
						Intent intent = new Intent(WrongWord.this, Score.class);
				//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);

					}

				}
				if (!key.equals(words[wordnum - 1][0])) {

					defrepeat(1);
					wordTextView3.setBackgroundResource(R.drawable.red);

					// myapp.addwrongword(words[wordnum - 1]);

					Intent intent = new Intent(WrongWord.this, Word.class);
					startActivity(intent);

				}

			}
		}
	});
	
	
	wordTextView4.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			key = (String) wordTextView4.getText();

			if (wcon == 1) {

				if (key.equals(words[wordnum - 1][0])) {

					defrepeat(0);
					wordTextView4.setBackgroundResource(R.drawable.green);
					myapp.set(4, Integer.toString(wordnum + 1));

					if (!words[wordnum][0].equals("")) {

						System.out.println("走了root1");
						Intent intent = new Intent(WrongWord.this, Root.class);
						startActivity(intent);
					}
					if (words[wordnum][0].equals("")) {

						System.out.println("走了root2");
						Intent intent = new Intent(WrongWord.this, Score.class);
			//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);

					}

				}
				if (!key.equals(words[wordnum - 1][0])) {

					defrepeat(1);
					wordTextView4.setBackgroundResource(R.drawable.red);

					// myapp.addwrongword(words[wordnum - 1]);

					Intent intent = new Intent(WrongWord.this, Word.class);
					startActivity(intent);

				}

			}
			
		}
	});*/
	
	
	
	
	
	
	
	
}

private void ran() {
	int numword = 0; // 防止 最后出现不够20 出现的情况
	for (int i = 0; i < 20; i++) {

		if (!words[i][0].equals("")) {
			numword = i + 1;
		}

	}

	double h = Math.random() * 4;

	// System.out.println((int) h);
	int k = (int) h;

	h = Math.random() * numword;

	while (k == (int) h) {
		h = Math.random() * numword;

	}

	int c = (int) h;

	h = Math.random() * numword;
	while (k == (int) h || c == (int) h) {

		h = Math.random() * numword;

	}
	int s = (int) h;
	h = Math.random() * numword;
	while (k == (int) h || c == (int) h || s == (int) h) {
		h = Math.random() * numword;
	}
	int x = (int) h;

	if (k == 0) {
		method(1);
		wordTextView2.setText(words[c][0]);
		wordTextView3.setText(words[s][0]);
		wordTextView4.setText(words[x][0]);

	}
	if (k == 1) {
		method(2);
		wordTextView1.setText(words[c][0]);
		wordTextView3.setText(words[s][0]);
		wordTextView4.setText(words[x][0]);

	}
	if (k == 2) {
		method(3);
		wordTextView1.setText(words[c][0]);
		wordTextView2.setText(words[s][0]);
		wordTextView4.setText(words[x][0]);

	}
	if (k == 3) {
		method(4);
		wordTextView1.setText(words[c][0]);
		wordTextView2.setText(words[s][0]);
		wordTextView3.setText(words[x][0]);

	}

}

private void method(int key) {

	if (key == 1) {
		wordTextView1.setText(words[wordnum - 1][0]);

		if (Integer.parseInt(myapp.get(6)) == 2) {
			wordTextView1.setBackgroundResource(R.drawable.green);
			defrepeat(0);
		}
	}

	if (key == 2) {
		wordTextView2.setText(words[wordnum - 1][0]);

		if (Integer.parseInt(myapp.get(6)) == 2) {
			wordTextView2.setBackgroundResource(R.drawable.green);
			defrepeat(0);
		}
	}

	if (key == 3) {
		wordTextView3.setText(words[wordnum - 1][0]);

		if (Integer.parseInt(myapp.get(6)) == 2) {
			wordTextView3.setBackgroundResource(R.drawable.green);
			defrepeat(0);
		}
	}
	if (key == 4) {
		wordTextView4.setText(words[wordnum - 1][0]);

		if (Integer.parseInt(myapp.get(6)) == 2) {
			wordTextView4.setBackgroundResource(R.drawable.green);
			defrepeat(0);
		}
	}

}

private void defrepeat(int key) {

	if (key == 0) {
		myapp.set(6, Integer.toString(0));
	}

	if (key == 1) {
		myapp.set(6, Integer.toString((Integer.parseInt(myapp.get(6))) + 1));

	}

}

	
	
	

}
