package level4;

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

public class wordsl4 extends Activity {


	/*
	 * private SoundPool sp;// ����һ��SoundPool
	 *//*
		 * private int musicright;// ����һ��������load������������suondID private int
		 * musicwrong;
		 */

	/* private managedb db; */
	private Dialog alertdDialog;
	private TextView textView1, textView2, defTextView, wordTextView1,
			wordTextView2, wordTextView3, wordTextView4;
	private TextView textViewlevel, textViewword, textViewwr;
	private mypublicvalue myapp;
	private String[][] words;

	/* private MediaPlayer mp = new MediaPlayer(); */

	// private String[][] lwords;

	private String[] roots;

	private int wordnum; // �ڼ���word
	private int numroot;

	public int con; // TT ѭ�����Ʒ�
	public int wcon; // ��ϰ���Ʒ�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.word);
		/*
		 * sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//
		 * ��һ������Ϊͬʱ���������������������ڶ����������ͣ�����Ϊ��������
		 * 
		 * musicright = sp.load(this, R.raw.right, 1); //
		 * ����������زķŵ�res/raw���2��������Ϊ��Դ�ļ�����3��Ϊ���ֵ����ȼ� musicwrong = sp.load(this,
		 * R.raw.wrong, 1); // ����������زķŵ�res/raw���2��������Ϊ��Դ�ļ�����3��Ϊ���ֵ����ȼ�\
		 */

		System.out.println("Word.class ����");

		textView1 = (TextView) this.findViewById(R.id.textview1);
		textView2 = (TextView) this.findViewById(R.id.textview2);
		textViewlevel = (TextView) this.findViewById(R.id.leveltext);
		textViewword = (TextView) this.findViewById(R.id.wordtext);
		textViewwr = (TextView) this.findViewById(R.id.wrtext);
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

		textViewlevel.setText(" Level: " + myapp.get(3)); // �趨��ʾlevel�Ŀؼ�

		wordnum = Integer.parseInt(myapp.get(4));

		textViewword.setText("Word: " + wordnum + "  "); // �趨��ʾ����word�Ŀؼ�

		wcon = myapp.getreviewwrongcontrol(); // ��ϰ���Ʒ�ֵ

		con = myapp.getrepeatcontrol(); // TTѭ�����Ʒ�

		if (wcon == 0) { // ��׼����� words ȡ��

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

		if (wcon == 1) { // ��������£� ȡ�ô��
			/* lwords = myapp.getwords(); */
			words = myapp.getCwrongwords();
			textViewwr.setText("Wrong Reivew");
			textViewwr.setBackgroundColor(Color.RED);

		}

		defTextView.setText(words[wordnum - 1][1]);

		System.out.println("Word TT ѭ�����Ʒ� ���� con " + myapp.getrepeatcontrol());
		System.out.println("Word wrong ѭ�����Ʒ� ���� wcon "
				+ myapp.getreviewwrongcontrol());

		System.out.println("ranǰһ��");

		this.ran();

		wordTextView1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String key = (String) wordTextView1.getText();

				if (wcon == 1) { // ���ѭ����

					if (key.equals(words[wordnum - 1][0])) { // ������

						wordTextView1.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						defrepeat(0);
						if (!words[wordnum][0].equals("")) {

							System.out.println("���ѭ����һ��");
							myapp.set(4, Integer.toString(wordnum + 1));
							Intent intent = new Intent(wordsl4.this, idroortsl4.class);
							startActivity(intent);
							// playmusic(1);

							finish();

						}

						if (words[wordnum][0].equals("")) { // ���ѭ��������

							System.out.println("���ѭ������");
							Intent intent = new Intent(wordsl4.this, score.class);

							startActivity(intent);
							// playmusic(1);

							finish();

						}

					}

					if (!key.equals(words[wordnum - 1][0])) { // ���ѭ����������

						wordTextView1.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						defrepeat(1); // ���������˼�1
						Intent intent = new Intent(wordsl4.this, wordsl4.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}

				if (wcon == 0) { // ��׼ѭ����

					if (key.equals(words[wordnum - 1][0])) {// ��׼ѭ���� ������

						defrepeat(0);
						wordTextView1.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						if (con == 1) { // ��׼ѭ��+ TTѭ����

							if (wordnum % 5 != 0) { // û������5������

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}

							if (wordnum % 5 == 0) { // ����5��������
								myapp.set(4, Integer.toString(wordnum + 1));
								myapp.setrepreatcontrol(0); // TTѭ����ͷ�� ��տ��Ʒ�

								if (words[wordnum][0].equals("")) { // �жϽ���
									// ����list��ǰ����sequence��������
									// ���븴ϰ wrong
									// words ���̡�
									myapp.cleanwrongwords(); // �����㷨����
																// ����wrongwords

									/*
									 * Toast.makeText( wordsl2.this,
									 * "OK, This list words is over, we will enter REVIEW step"
									 * , 1).show();
									 */

									myapp.set(4, Integer.toString(1)); // ����review
																		// wrong
																		// ѭ��
																		// wordnumber
																		// ��1

									myapp.setreviewwrongcontrol(1); // �趨����reviewwrong
																	// wcon��ֵΪ1

									String[][] wrongwords = myapp
											.getCwrongwords();

									System.out.println(wrongwords[0][0]);

									System.out.println(wrongwords[1][0]);

									System.out.println(wrongwords[2][0]); // ���ڲ���ʹ�ñ���

									System.out.println(wrongwords[3][0]);

									System.out.println(wrongwords[4][0]);

									if (!wrongwords[0][0].equals("")) {
										Intent intent = new Intent(wordsl4.this,
												idroortsl4.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

									if (wrongwords[0][0].equals("")) {

										Intent intent = new Intent(wordsl4.this,
												score.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

								}

								if (!words[wordnum][0].equals("")) { // ��׼
																		// TTѭ��û�н���  //����5������

									double h = Math.random();

									if (h < 0.5) {
										Intent intent = new Intent(wordsl4.this,
												wordsl4.class);
										startActivity(intent);

										// playmusic(1);

									} else {
										Intent intent = new Intent(wordsl4.this,
												definitionl4.class);
										startActivity(intent);
										finish();
									

									}
									finish();
								}
							}
						}

						if (con == 0) { // ��׼ѭ�� and ��TTѭ��

							if (wordnum % 5 != 0) {
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}
							if (wordnum % 5 == 0) {
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}

						}

					}
					if (!key.equals(words[wordnum - 1][0])) { // ��׼ѭ���������� ��TTѭ��

						defrepeat(1);
						wordTextView1.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						myapp.addwrongword(words[wordnum - 1]);

						String[][] wrongwords = myapp.getwrongwords();
						System.out.println(wrongwords[0][0]);

						System.out.println(wrongwords[1][0]);

						System.out.println(wrongwords[2][0]);

						Intent intent = new Intent(wordsl4.this, wordsl4.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}
			}
		});

		wordTextView2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String key = (String) wordTextView2.getText();

				if (wcon == 1) { // ���ѭ����

					if (key.equals(words[wordnum - 1][0])) { // ������

						wordTextView2.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						defrepeat(0);
						if (!words[wordnum][0].equals("")) {

							System.out.println("���ѭ����һ��");
							myapp.set(4, Integer.toString(wordnum + 1));
							Intent intent = new Intent(wordsl4.this, idroortsl4.class);
							startActivity(intent);
							// playmusic(1);

							finish();

						}

						if (words[wordnum][0].equals("")) { // ���ѭ��������

							System.out.println("���ѭ������");
							Intent intent = new Intent(wordsl4.this, score.class);

							startActivity(intent);
							// playmusic(1);

							finish();

						}

					}

					if (!key.equals(words[wordnum - 1][0])) { // ���ѭ����������

						wordTextView2.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						defrepeat(1); // ���������˼�1
						Intent intent = new Intent(wordsl4.this, wordsl4.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}

				if (wcon == 0) { // ��׼ѭ����

					if (key.equals(words[wordnum - 1][0])) {// ��׼ѭ���� ������

						defrepeat(0);
						wordTextView2.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						if (con == 1) { // ��׼ѭ��+ TTѭ����

							if (wordnum % 5 != 0) { // û������5������

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}

							if (wordnum % 5 == 0) { // ����5��������
								myapp.set(4, Integer.toString(wordnum + 1));
								myapp.setrepreatcontrol(0); // TTѭ����ͷ�� ��տ��Ʒ�

								if (words[wordnum][0].equals("")) { // �жϽ���
									// ����list��ǰ����sequence��������
									// ���븴ϰ wrong
									// words ���̡�
									myapp.cleanwrongwords(); // �����㷨����
																// ����wrongwords

									/*
									 * Toast.makeText( wordsl2.this,
									 * "OK, This list words is over, we will enter REVIEW step"
									 * , 1).show();
									 */

									myapp.set(4, Integer.toString(1)); // ����review
																		// wrong
																		// ѭ��
																		// wordnumber
																		// ��1

									myapp.setreviewwrongcontrol(1); // �趨����reviewwrong
																	// wcon��ֵΪ1

									String[][] wrongwords = myapp
											.getCwrongwords();

									System.out.println(wrongwords[0][0]);

									System.out.println(wrongwords[1][0]);

									System.out.println(wrongwords[2][0]); // ���ڲ���ʹ�ñ���

									System.out.println(wrongwords[3][0]);

									System.out.println(wrongwords[4][0]);

									if (!wrongwords[0][0].equals("")) {
										Intent intent = new Intent(wordsl4.this,
												idroortsl4.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

									if (wrongwords[0][0].equals("")) {

										Intent intent = new Intent(wordsl4.this,
												score.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

								}

								if (!words[wordnum][0].equals("")) { // ��׼
																		// TTѭ��û�н���  //����5������

									double h = Math.random();

									if (h < 0.5) {
										Intent intent = new Intent(wordsl4.this,
												wordsl4.class);
										startActivity(intent);

										// playmusic(1);

									} else {
										Intent intent = new Intent(wordsl4.this,
												definitionl4.class);
										startActivity(intent);
										finish();
									

									}
									finish();
								}
							}
						}

						if (con == 0) { // ��׼ѭ�� and ��TTѭ��

							if (wordnum % 5 != 0) {
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}
							if (wordnum % 5 == 0) {
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}

						}

					}
					if (!key.equals(words[wordnum - 1][0])) { // ��׼ѭ���������� ��TTѭ��

						defrepeat(1);
						wordTextView2.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						myapp.addwrongword(words[wordnum - 1]);

						String[][] wrongwords = myapp.getwrongwords();
						System.out.println(wrongwords[0][0]);

						System.out.println(wrongwords[1][0]);

						System.out.println(wrongwords[2][0]);

						Intent intent = new Intent(wordsl4.this, wordsl4.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}
			}
		});
		wordTextView3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String key = (String) wordTextView3.getText();

				if (wcon == 1) { // ���ѭ����

					if (key.equals(words[wordnum - 1][0])) { // ������

						wordTextView3.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						defrepeat(0);
						if (!words[wordnum][0].equals("")) {

							System.out.println("���ѭ����һ��");
							myapp.set(4, Integer.toString(wordnum + 1));
							Intent intent = new Intent(wordsl4.this, idroortsl4.class);
							startActivity(intent);
							// playmusic(1);

							finish();

						}

						if (words[wordnum][0].equals("")) { // ���ѭ��������

							System.out.println("���ѭ������");
							Intent intent = new Intent(wordsl4.this, score.class);

							startActivity(intent);
							// playmusic(1);

							finish();

						}

					}

					if (!key.equals(words[wordnum - 1][0])) { // ���ѭ����������

						wordTextView3.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						defrepeat(1); // ���������˼�1
						Intent intent = new Intent(wordsl4.this, wordsl4.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}

				if (wcon == 0) { // ��׼ѭ����

					if (key.equals(words[wordnum - 1][0])) {// ��׼ѭ���� ������

						defrepeat(0);
						wordTextView3.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						if (con == 1) { // ��׼ѭ��+ TTѭ����

							if (wordnum % 5 != 0) { // û������5������

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}

							if (wordnum % 5 == 0) { // ����5��������
								myapp.set(4, Integer.toString(wordnum + 1));
								myapp.setrepreatcontrol(0); // TTѭ����ͷ�� ��տ��Ʒ�

								if (words[wordnum][0].equals("")) { // �жϽ���
									// ����list��ǰ����sequence��������
									// ���븴ϰ wrong
									// words ���̡�
									myapp.cleanwrongwords(); // �����㷨����
																// ����wrongwords

									/*
									 * Toast.makeText( wordsl2.this,
									 * "OK, This list words is over, we will enter REVIEW step"
									 * , 1).show();
									 */

									myapp.set(4, Integer.toString(1)); // ����review
																		// wrong
																		// ѭ��
																		// wordnumber
																		// ��1

									myapp.setreviewwrongcontrol(1); // �趨����reviewwrong
																	// wcon��ֵΪ1

									String[][] wrongwords = myapp
											.getCwrongwords();

									System.out.println(wrongwords[0][0]);

									System.out.println(wrongwords[1][0]);

									System.out.println(wrongwords[2][0]); // ���ڲ���ʹ�ñ���

									System.out.println(wrongwords[3][0]);

									System.out.println(wrongwords[4][0]);

									if (!wrongwords[0][0].equals("")) {
										Intent intent = new Intent(wordsl4.this,
												idroortsl4.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

									if (wrongwords[0][0].equals("")) {

										Intent intent = new Intent(wordsl4.this,
												score.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

								}

								if (!words[wordnum][0].equals("")) { // ��׼
																		// TTѭ��û�н���  //����5������

									double h = Math.random();

									if (h < 0.5) {
										Intent intent = new Intent(wordsl4.this,
												wordsl4.class);
										startActivity(intent);

										// playmusic(1);

									} else {
										Intent intent = new Intent(wordsl4.this,
												definitionl4.class);
										startActivity(intent);
										finish();
									

									}
									finish();
								}
							}
						}

						if (con == 0) { // ��׼ѭ�� and ��TTѭ��

							if (wordnum % 5 != 0) {
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}
							if (wordnum % 5 == 0) {
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}

						}

					}
					if (!key.equals(words[wordnum - 1][0])) { // ��׼ѭ���������� ��TTѭ��

						defrepeat(1);
						wordTextView3.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						myapp.addwrongword(words[wordnum - 1]);

						String[][] wrongwords = myapp.getwrongwords();
						System.out.println(wrongwords[0][0]);

						System.out.println(wrongwords[1][0]);

						System.out.println(wrongwords[2][0]);

						Intent intent = new Intent(wordsl4.this, wordsl4.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}
			}
		});

		wordTextView4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String key = (String) wordTextView4.getText();

				if (wcon == 1) { // ���ѭ����

					if (key.equals(words[wordnum - 1][0])) { // ������

						wordTextView4.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						defrepeat(0);
						if (!words[wordnum][0].equals("")) {

							System.out.println("���ѭ����һ��");
							myapp.set(4, Integer.toString(wordnum + 1));
							Intent intent = new Intent(wordsl4.this, idroortsl4.class);
							startActivity(intent);
							// playmusic(1);

							finish();

						}

						if (words[wordnum][0].equals("")) { // ���ѭ��������

							System.out.println("���ѭ������");
							Intent intent = new Intent(wordsl4.this, score.class);

							startActivity(intent);
							// playmusic(1);

							finish();

						}

					}

					if (!key.equals(words[wordnum - 1][0])) { // ���ѭ����������

						wordTextView4.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						defrepeat(1); // ���������˼�1
						Intent intent = new Intent(wordsl4.this, wordsl4.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}

				if (wcon == 0) { // ��׼ѭ����

					if (key.equals(words[wordnum - 1][0])) {// ��׼ѭ���� ������

						defrepeat(0);
						wordTextView4.setBackgroundResource(R.drawable.green);
						myapp.playmusic(1);

						if (con == 1) { // ��׼ѭ��+ TTѭ����

							if (wordnum % 5 != 0) { // û������5������

								myapp.set(4, Integer.toString(wordnum + 1));
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}

							if (wordnum % 5 == 0) { // ����5��������
								myapp.set(4, Integer.toString(wordnum + 1));
								myapp.setrepreatcontrol(0); // TTѭ����ͷ�� ��տ��Ʒ�

								if (words[wordnum][0].equals("")) { // �жϽ���
									// ����list��ǰ����sequence��������
									// ���븴ϰ wrong
									// words ���̡�
									myapp.cleanwrongwords(); // �����㷨����
																// ����wrongwords

									/*
									 * Toast.makeText( wordsl2.this,
									 * "OK, This list words is over, we will enter REVIEW step"
									 * , 1).show();
									 */

									myapp.set(4, Integer.toString(1)); // ����review
																		// wrong
																		// ѭ��
																		// wordnumber
																		// ��1

									myapp.setreviewwrongcontrol(1); // �趨����reviewwrong
																	// wcon��ֵΪ1

									String[][] wrongwords = myapp
											.getCwrongwords();

									System.out.println(wrongwords[0][0]);

									System.out.println(wrongwords[1][0]);

									System.out.println(wrongwords[2][0]); // ���ڲ���ʹ�ñ���

									System.out.println(wrongwords[3][0]);

									System.out.println(wrongwords[4][0]);

									if (!wrongwords[0][0].equals("")) {
										Intent intent = new Intent(wordsl4.this,
												idroortsl4.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

									if (wrongwords[0][0].equals("")) {

										Intent intent = new Intent(wordsl4.this,
												score.class);

										startActivity(intent);

										// playmusic(1);
										finish();

									}

								}

								if (!words[wordnum][0].equals("")) { // ��׼
																		// TTѭ��û�н���  //����5������

									double h = Math.random();

									if (h < 0.5) {
										Intent intent = new Intent(wordsl4.this,
												wordsl4.class);
										startActivity(intent);

										// playmusic(1);

									} else {
										Intent intent = new Intent(wordsl4.this,
												definitionl4.class);
										startActivity(intent);
										finish();
									

									}
									finish();
								}
							}
						}

						if (con == 0) { // ��׼ѭ�� and ��TTѭ��

							if (wordnum % 5 != 0) {
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}
							if (wordnum % 5 == 0) {
								Intent intent = new Intent(wordsl4.this,
										idroortsl4.class);
								startActivity(intent);

								// playmusic(1);
								finish();

							}

						}

					}
					if (!key.equals(words[wordnum - 1][0])) { // ��׼ѭ���������� ��TTѭ��

						defrepeat(1);
						wordTextView4.setBackgroundResource(R.drawable.red);
						myapp.playmusic(0);

						myapp.addwrongword(words[wordnum - 1]);

						String[][] wrongwords = myapp.getwrongwords();
						System.out.println(wrongwords[0][0]);

						System.out.println(wrongwords[1][0]);

						System.out.println(wrongwords[2][0]);

						Intent intent = new Intent(wordsl4.this, wordsl4.class);
						startActivity(intent);

						// playmusic(0);
						finish();

					}

				}
			}
		});

	}

	private void ran() {
		numroot = 0; // ��ֹ �����ֲ���20 ���ֵ����

		this.getroots();

		/* if (wcon == 0) { */
		for (int i = 0; i < 20; i++) {

			if (!roots[i].equals("")) { // ��ѭ������
				numroot = i + 1;
			}
		}

		double h = Math.random() * 4; // ���Ϊ0��1֮�䣬 ������4����ť������ѡ4

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

		/* } */

		/*
		 * if (wcon == 1) {
		 * 
		 * for (int i = 0; i < 20; i++) { if (!lwords[i][0].equals("")) {
		 * numword = i + 1; } }
		 * 
		 * double h = Math.random() * 4; // ���Ϊ0��1֮�䣬 ������4����ť������ѡ4
		 * 
		 * // System.out.println((int) h); int k = (int) h;
		 * 
		 * if (k == 0) { method(1);
		 * 
		 * 
		 * } if (k == 1) { method(2);
		 * 
		 * 
		 * } if (k == 2) { method(3);
		 * 
		 * } if (k == 3) { method(4);
		 * 
		 * 
		 * }
		 * 
		 * }
		 */

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
		System.out.println("word��ѭ��" + numroot);

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
				// myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);

			}
			wordTextView2.setText(roots[a2]);
			wordTextView3.setText(roots[a3]);
			wordTextView4.setText(roots[a4]);
		}

		if (key == 2) {
			wordTextView2.setText(words[wordnum - 1][0]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				wordTextView2.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);
			}

			wordTextView1.setText(roots[a3]);
			wordTextView3.setText(roots[a2]);
			wordTextView4.setText(roots[a4]);
		}

		if (key == 3) {
			wordTextView3.setText(words[wordnum - 1][0]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				wordTextView3.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);
			}

			wordTextView1.setText(roots[a4]);
			wordTextView2.setText(roots[a3]);
			wordTextView4.setText(roots[a2]);
		}
		if (key == 4) {
			wordTextView4.setText(words[wordnum - 1][0]);

			if (Integer.parseInt(myapp.get(6)) == 2) {
				wordTextView4.setBackgroundResource(R.drawable.green);
				// myapp.playmusic(1);
				// //playmusic(1);
				defrepeat(0);
			}

			wordTextView1.setText(roots[a2]);
			wordTextView2.setText(roots[a4]);
			wordTextView3.setText(roots[a3]);
		}

	}

	/*
	 * private int rand() { double h = Math.random() * numroot;
	 * 
	 * int k = (int) h; return k;
	 * 
	 * }
	 */

	private void defrepeat(int key) { // ������ ���2�εĿ��Ʒ���

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
		System.out.println("root����");
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

	public void playmusic(int key) {

		if (key == 1) {

			myapp.playmusic(1);
		}

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

		if (key == 0) {

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
			 * timer.schedule(task, 900); timer.cancel(); }
			 */

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			alertdDialog = new AlertDialog.Builder(this)
					.setTitle("EXIT LEVEL")
					.setMessage("Do you want to exit this level learning��")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("Confirm",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									myapp.empty();

									Intent intent = new Intent(wordsl4.this,
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
