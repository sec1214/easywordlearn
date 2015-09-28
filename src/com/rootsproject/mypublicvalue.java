package com.rootsproject;

import java.io.IOException;
import java.util.Random;

import Database.managedb;
import android.R.bool;
import android.R.drawable;
import android.R.integer;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.rootsproject.R;

public class mypublicvalue extends Application {

	public static final String prefsName = "com.rootsproject.prefs";

	public int buttonsound, musicsound;

	public boolean splashscreen;
	
	public float buttonvolume;
	public float musicvolume;
	public float splashmusicvolume;

	public String tablename; // 0 ������� 1����list�� 2������list�İ���������
	public String listname;
	public String list;
	public String[][] words;
	public String[][] wordslv1;
	public String[][] wordslv2;
	public String level; // 3����level����
	public String wordnum; // 4����ѧ�����Ŵ���

	public String step; // ����root��ʱ��ڼ����� seq Ϊ5

	public String definitionrepeat; // ����Ϊdefinition+word+rootͼ�ظ������� seq Ϊ6

	public String controllv3; // ����level3�Ŀ��Ʒ��� seqΪ7.

	public String lv1; // ����level1 �� 2�� ���ȡֵ���Ʒ� �� seq �ֱ�Ϊ 8

	public int repeatcontrol; // tt ѭ�����Ʒ�ֵ

	public String[][] wrongwords = new String[300][10];
	public String[][] wrongwords1 = new String[300][10];

	public int wrongwordkey, wrongwordkey1;

	public String[][] Cwrongwords = new String[21][10];
	public String[][] Lwrongwords = new String[21][10];

	public String[][] ranwords;

	public String[] x = new String[10];
	public String[] e = new String[10];

	public double clicknum; // ������ٸ�activity����
	public double rightnum; // �����˶��ٸ�activity����

	public double idrootclicknum, idrootrightnum;
	public double rootclicknum, rootrightnum;
	public double defwordclicknum, defwordrightnum;

	public int reviewwrongcontrol;

	private SoundPool sp;// ����һ��SoundPool
	private MediaPlayer mediaPlayer;

	private MediaPlayer mediaPlayerlv1, mediaPlayerlv2, mediaPlayerlv3,
			mediaPlayerlv4, mediaPlayerlv5, mediaPlayerlv6, mediaPlayerlv7,
			mediaPlayerlv8;
	private int musicright;// ����һ��������load������������suondID
	private int musicwrong;
	private int musicalarm;
	private int musicfailure;
	private int musictick;
	private int musicscorepulse;
	private int musicexplosion;

	public int misroot, definition, idroot, root, greenhelp;

	public Vibrator vibrator;
	public String[][] emptywords = new String[21][10];

	private String[][] rootword;
	private String[][] crootword = new String[100][10];

	public int numwords; // ÿ��list��words��Ϊ�յ�����

	public int listnum;
	
	public int video;
	private int pulseSoundStreamID;
	/*added by xiaoqian yu, 2014-12-22, start*/
	int constantCorrectCount;
	//increment of constantCorrectCount and judge if pop out a praise
	public void judgeAndCalculateConstantCorrectCount()
	{
		int comboGraphicIndex;
		boolean showCombolImage = false;
		constantCorrectCount++;
		if(constantCorrectCount > 7)
		{
			//constantCorrectCount = 0;
		}
		if(constantCorrectCount > 1)
		{
			final Toast showImageToast=new Toast(getBaseContext());
			
			/*new 1223*/
			LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			View toastView = inflater.inflate( R.layout.toastcombopraise, null );
			View toastViewWithoutImage = inflater.inflate( R.layout.toastcombocount, null );
			ImageView imageView = (ImageView)toastView.findViewById(R.id.image);
			toastView.getBackground().setAlpha(0);
			/**/
			if(constantCorrectCount % 3 == 0)
			{
				showCombolImage = true;
			}
			else
			{
				showCombolImage = false;
			}
			
			/*new 1223*/
			if(showCombolImage == true)
			{
				Random random = new Random();
				comboGraphicIndex = random.nextInt(10) + 1;//10 pictures, index from 1 to 10
				switch(comboGraphicIndex)
				{
					case 1:
						imageView.setImageResource(R.drawable.rainingroots);
						break;
					case 2:
						imageView.setImageResource(R.drawable.rootasaurus);
						break;
					case 3:
						imageView.setImageResource(R.drawable.rootmoney);
						break;
					case 4:
						imageView.setImageResource(R.drawable.rootstorm);
						break;
					case 5:
						imageView.setImageResource(R.drawable.rootking);
						break;
					case 6:
						imageView.setImageResource(R.drawable.rootcity);
						break;
					case 7:
						imageView.setImageResource(R.drawable.rootbomb);
						break;
					case 8:
						imageView.setImageResource(R.drawable.rootsandwich);
						break;
					case 9:
						imageView.setImageResource(R.drawable.roottrain);
						break;
					case 10:
						imageView.setImageResource(R.drawable.vocabexplosion);
						break;
				    default:
				    	imageView.setImageResource(R.drawable.rootasaurus);
				    	break;
				}
				
				TextView textView = (TextView)toastView.findViewById(R.id.text);
				Typeface typeFace =Typeface.createFromAsset(getAssets(),"fonts/waltograph.ttf");
				textView.setTypeface(typeFace);
				textView.setText(" " +constantCorrectCount + " IN A ROW!! ");
				/**/
				showImageToast.setGravity(Gravity.TOP, 0, 100);
				showImageToast.setDuration(Toast.LENGTH_LONG);
				showImageToast.setView(toastView);
				showImageToast.show();
				
				Handler handler = new Handler();
		        handler.postDelayed(new Runnable() {
		           @Override
		           public void run() {
		        	   showImageToast.cancel(); 
		           }
		        }, 1000);
			}
			else
			{
				TextView textView = (TextView)toastViewWithoutImage.findViewById(R.id.text);
				Typeface typeFace =Typeface.createFromAsset(getAssets(),"fonts/waltograph.ttf");
				textView.setTypeface(typeFace);
				textView.setText("  " +constantCorrectCount + " IN A ROW!! ");
				/**/
				showImageToast.setGravity(Gravity.TOP, 0, 230);
				showImageToast.setDuration(Toast.LENGTH_LONG);
				showImageToast.setView(toastViewWithoutImage);
				showImageToast.show();
				
				Handler handler = new Handler();
		        handler.postDelayed(new Runnable() {
		           @Override
		           public void run() {
		        	   showImageToast.cancel(); 
		           }
		        }, 1000);
			}
		}
		else
		{}
	}
	public int scorePulseIntervalCount;
	public void judgeAndCalculateConstantErrorCount()
	{
		constantCorrectCount = 0;
	}
	/*added by xiaoqian yu, 2014-12-22, over*/
	public void resetConstantCorrectCount()
	{
		constantCorrectCount = 0;
	}
	/*added by xiaoqian yu, 2014-12-22, start*/
	public int setPulseTimeInterval(double correctCount, double totalCount)
	{
		int rtTimeInterval = 250;
		if(correctCount > 0 && totalCount > 0)
		{
			if((correctCount / totalCount) <= 0.25)
			{
				rtTimeInterval = 250;
			}
			else if((correctCount / totalCount) > 0.25 &&
					(correctCount / totalCount) <= 0.5)
			{
				rtTimeInterval = 500;
			}
			else if((correctCount / totalCount) > 0.5 &&
					(correctCount / totalCount) <= 0.75)
			{
				rtTimeInterval = 1000;
			}
			else if((correctCount / totalCount) > 0.75 &&
					(correctCount / totalCount) <= 1.0)
			{
				rtTimeInterval = 2000;
			}
		}
		return rtTimeInterval;
	}
	/*added by xiaoqian yu, 2014-12-22, over*/
	/*added by xiaoqian yu, 2014-12-23, start*/
	public int calculateViewAnimationID(int timeInterval)
	{
		int rtAnimationID = 0;
		if(timeInterval <= 250)
		{
			rtAnimationID = R.anim.textviewscalel1;
		}
		else if(timeInterval > 250 && timeInterval <= 500)
		{
			rtAnimationID = R.anim.textviewscalel2;
		}
		else if(timeInterval > 500 && timeInterval <= 1000)
		{
			rtAnimationID = R.anim.textviewscalel3;
		}
		else if(timeInterval > 1000 && timeInterval <= 2000)
		{
			rtAnimationID = R.anim.textviewscalel4;
		}
		return rtAnimationID;
	}
	
	public void playPulseSound(int timeInterval, int loop)
	{
		sp.stop(pulseSoundStreamID);
		
		if(timeInterval <= 250)
		{
			//pulseSoundStreamID = sp.play(musicscorepulse, buttonvolume, buttonvolume, 1, loop, (float)1.5);
		}
		else if(timeInterval > 250 && timeInterval <= 500)
		{			
			//pulseSoundStreamID = sp.play(musicscorepulse, buttonvolume, buttonvolume, 1, loop, (float) 1.25);
		}
		else if(timeInterval > 500 && timeInterval <= 1000)
		{
			//pulseSoundStreamID = sp.play(musicscorepulse, buttonvolume, buttonvolume, 1, loop, (float)1.0);
		}
		else if(timeInterval > 1000 && timeInterval <= 2000)
		{
			//pulseSoundStreamID = sp.play(musicscorepulse, buttonvolume, buttonvolume, 1, loop, (float) 0.5);
		}
		
	}
	public void stopPulseSound()
	{
		sp.stop(pulseSoundStreamID);
	}
	/*added by xiaoqian yu, 2014-12-23, over*/
	public int getvideo(){
		return video;
	}
	
	public void setvideo(){
		db.updateacount(7, 1);
	}

	// public BroadcastReceiver receiver;

	private managedb db;

	public void setdefault() {
		db.setdefault();
		misroot = 0;
		idroot = 0;
		definition = 0;
		root = 0;
		musicsound = 1;
		buttonsound = 1;
	}

	public void deletereviewrecord() {

		db.deletereviewrecord();
	}

	public void setsplashscreen() {

		splashscreen = true;

	}

	public boolean getsplashscreen() {
		return splashscreen;
	}

	public void setmusic(int key, int value) {
		if (key == 0) {
			buttonsound = value;
			db.updateacount(0, value);
		}
		if (key == 1) {
			musicsound = value;
			db.updateacount(1, value);
		}

	}

	public int getmusic(int key) {
		int flag = 0;
		if (key == 0) {
			flag = buttonsound;

		}
		if (key == 1) {
			flag = musicsound;
		}
		return flag;

	}

	/*
	 * public void setreceiver(BroadcastReceiver key) { // no use
	 * 
	 * receiver = key; }
	 */
	/*
	 * public BroadcastReceiver getreceiver() { // no use
	 * 
	 * return receiver; }
	 */
	public void setlistnum(int key) {

		listnum = key;
	}

	public int getlistnum() {
		return listnum;
	}

	public void sethelpcontrol(int key, int p) {
		if (key == 0) {

			if (misroot == 0 && p == 1) {
				db.updateacount(2, 1);
			}

			misroot = p;
		}
		if (key == 1) {
			if (definition == 0 && p == 1) {
				db.updateacount(3, 1);
			}
			definition = p;
		}
		if (key == 2) {
			if (idroot == 0 && p == 1) {
				db.updateacount(4, 1);
			}
			idroot = p;
		}
		if (key == 3) {
			if (root == 0 && p == 1) {
				db.updateacount(5, 1);
			}
			root = p;
		}
		if (key == 4) {

			if (greenhelp == 0 && p == 1) {
				db.updateacount(6, 1);
			}

			greenhelp = p;
		}

	}

	public int gethelpcontrol(int key) { // helpbutton shaping control
		int flag = 0;

		if (key == 0) {
			flag = misroot;

		}
		if (key == 1) {
			flag = definition;
		}
		if (key == 2) {
			flag = idroot;
		}
		if (key == 3) {
			flag = root;
		}
		if (key == 4) {
			flag = greenhelp; // greentoast
		}

		return flag;

	}

	public void greentoast() {

		if (gethelpcontrol(4) == 0) {
			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View toastRoot = inflater.inflate(R.layout.toast, null);

			Toast toast = new Toast(getApplicationContext());
			toast.setView(toastRoot);
			TextView tv = (TextView) toastRoot.findViewById(R.id.TextViewInfo);
			tv.setText(getString(R.string.greenhelp));
			toast.show();
			sethelpcontrol(4, 1);

		} else {

		}

	}

	public void Vibrate() {

		vibrator.vibrate(300);

	}

	public String[][] getrootword() {
		return crootword;
	}

	public void setrootword(String[][] word) {
		rootword = word;
	}

	public double getdefwordscore(int key) {
		double flag = 0;
		if (key == 0) {
			flag = defwordclicknum;
		}
		if (key == 1) {
			flag = defwordrightnum;
		}
		return flag;
	}

	public void setdefwordscore(int key, double number) {
		if (key == 0) {
			defwordclicknum = number;
		}
		if (key == 1) {
			defwordrightnum = number;
		}

	}

	public double getidrootscore(int key) {
		double flag = 0;
		if (key == 0) {
			flag = idrootclicknum;
		}
		if (key == 1) {
			flag = idrootrightnum;
		}
		return flag;
	}

	public void setidrootscore(int key, double number) {
		if (key == 0) {
			idrootclicknum = number;
		}
		if (key == 1) {
			idrootrightnum = number;
		}

	}

	public double getrootscore(int key) {
		double flag = 0;
		if (key == 0) {
			flag = rootclicknum;
		}
		if (key == 1) {
			flag = rootrightnum;
		}
		return flag;
	}

	public void setrootscore(int key, double number) {
		if (key == 0) {
			rootclicknum = number;
		}
		if (key == 1) {
			rootrightnum = number;
		}

	}

	public double getscore(int key) {
		double flag = 0;
		if (key == 0) {
			flag = clicknum;
		}
		if (key == 1) {
			flag = rightnum;
		}
		return flag;
	}

	public void setscore(int key, double number) {
		if (key == 0) {
			clicknum = number;
		}
		if (key == 1) {
			rightnum = number;
		}

	}

	public void setranwords(String[][] key) {
		ranwords = key;

	}

	public String[][] getranwords() {

		return ranwords;

	}

	public String[][] emptywords() { // support a empty array. �ṩ��words����ķ���

		return emptywords;
	}

	public int getreviewwrongcontrol() {
		return reviewwrongcontrol;

	}

	public void setreviewwrongcontrol(int key) {
		reviewwrongcontrol = key;
	}

	public int getrepeatcontrol() {

		return repeatcontrol;
	}

	public void setrepreatcontrol(int key) {

		repeatcontrol = key;

	}

	public String get(int seq) {
		String flag = new String();
		flag = "test";
		if (seq == 0) {
			flag = tablename;
		}
		if (seq == 1) {
			flag = listname;
		}
		if (seq == 2) {
			flag = list;
		}

		if (seq == 3) {
			flag = level;

		}

		if (seq == 4) {
			flag = wordnum;
		}

		if (seq == 5) {
			flag = step;
		}

		if (seq == 6) {
			flag = definitionrepeat;
		}

		if (seq == 7) {
			flag = controllv3;
		}
		if (seq == 8) {
			flag = lv1;
		}

		return flag;

	}

	public String[][] getwordslv1() {
		return wordslv1;
	}

	public void setwordslv1(String[][] words) {
		wordslv1 = words;
	}

	public String[][] getwordslv2() {
		return wordslv2;
	}

	public void setwordslv2(String[][] words) {
		wordslv2 = words;
	}

	public String[][] getwords() {

		return words;

	}

	public void set(int seq, String value) {
		if (seq == 0) {

			tablename = value;

		}

		if (seq == 1) {
			listname = value;
		}

		if (seq == 2) {
			list = value;
		}

		if (seq == 3) {
			level = value;
		}
		if (seq == 4) {
			wordnum = value;
		}
		if (seq == 5)
			step = value;
		if (seq == 6) {
			definitionrepeat = value;
		}

		if (seq == 7) {
			controllv3 = value;
		}
		if (seq == 8) {
			lv1 = value;
		}

	}

	public void setwords(String[][] words) { // ��ֵwords��ͬʱҲ��words�в�Ϊ�յ���Ŀȷ��

		this.words = words;

	}

	public void addwrongword(String[] word) {

		wrongwords[wrongwordkey] = word;
		wrongwordkey++;

	}

	public void addwrongwords1(String[] word) {
		wrongwords1[wrongwordkey1] = word;
		wrongwordkey1++;
	}

	public void cleanrootwords() // ע�����wrongword��סѭ���Ĵ����� Ȼ��ͨ����������ȥ���ظ�����

	{

		quicksort2(0, rootword.length - 1);

		int k = 0;
		for (int i = 0; i < rootword.length - 1; i++) {

			if (!rootword[i][0].equals(rootword[i + 1][0])) {
				if (!rootword[i][0].equals(""))

				{
					crootword[k] = rootword[i];
					k++;
				}
			}

		}

	}

	public void quicksort2(int p, int r) {

		if (p < r) {
			int q = partition2(p, r);
			quicksort2(p, q - 1);
			quicksort2(q + 1, r);
		}

	}

	public int partition2(int p, int r) {
		x = rootword[r];
		int i = p - 1;
		for (int j = p; j <= r - 1; j++) {
			if (rootword[j][0].compareTo(x[0]) >= 0) {
				i = i + 1;
				e = rootword[i];
				rootword[i] = rootword[j];
				rootword[j] = e;

			}

		}
		e = rootword[i + 1];
		rootword[i + 1] = rootword[r];
		rootword[r] = e;

		return i + 1;
	}

	public void cleanCwrongwords() // ע�����wrongword��סѭ���Ĵ����� Ȼ��ͨ����������ȥ���ظ�����

	{

		quicksort1(0, wrongwords1.length - 1);

		int k = 0;
		for (int i = 0; i < wrongwords1.length - 1; i++) {

			if (!wrongwords1[i][0].equals(wrongwords1[i + 1][0])) {
				if (!wrongwords1[i][0].equals(""))

				{
					Lwrongwords[k] = wrongwords1[i];
					k++;
				}
			}

		}

	}

	public void cleanwrongwords() // ע�����wrongword��סѭ���Ĵ����� Ȼ��ͨ����������ȥ���ظ�����

	{

		quicksort(0, wrongwords.length - 1);

		int k = 0;
		for (int i = 0; i < wrongwords.length - 1; i++) {

			if (!wrongwords[i][0].equals(wrongwords[i + 1][0])) {
				if (!wrongwords[i][0].equals(""))

				{
					Cwrongwords[k] = wrongwords[i];
					k++;
				}
			}

		}

	}

	public void quicksort(int p, int r) {

		if (p < r) {
			int q = partition(p, r);
			quicksort(p, q - 1);
			quicksort(q + 1, r);
		}

	}

	public int partition(int p, int r) {
		x = wrongwords[r];
		int i = p - 1;
		for (int j = p; j <= r - 1; j++) {
			if (wrongwords[j][0].compareTo(x[0]) >= 0) {
				i = i + 1;
				e = wrongwords[i];
				wrongwords[i] = wrongwords[j];
				wrongwords[j] = e;

			}

		}
		e = wrongwords[i + 1];
		wrongwords[i + 1] = wrongwords[r];
		wrongwords[r] = e;

		return i + 1;
	}

	public void quicksort1(int p, int r) {

		if (p < r) {
			int q = partition1(p, r);
			quicksort1(p, q - 1);
			quicksort1(q + 1, r);
		}

	}

	public int partition1(int p, int r) {
		x = wrongwords1[r];
		int i = p - 1;
		for (int j = p; j <= r - 1; j++) {
			if (wrongwords1[j][0].compareTo(x[0]) >= 0) {
				i = i + 1;
				e = wrongwords1[i];
				wrongwords1[i] = wrongwords1[j];
				wrongwords1[j] = e;

			}

		}
		e = wrongwords1[i + 1];
		wrongwords1[i + 1] = wrongwords1[r];
		wrongwords1[r] = e;

		return i + 1;
	}

	public void empty1() {

		set(4, "1"); // �ڼ��Ŵʹ�1
		set(5, "2"); // root��2 ��Ϊroot1 �ǵڶ�λ
		set(6, "0"); // ��ʱ���0
		set(7, "0");
		set(8, "0");
		set(9, "0");

		setscore(0, 0);
		setscore(1, 0);

		setdefwordscore(0, 0);
		setdefwordscore(1, 0);

		setidrootscore(0, 0);
		setidrootscore(1, 0);

		setrootscore(0, 0);
		setrootscore(1, 0);

	}

	public void empty() { // 0���������ʣ�1������ȫ����

		wrongwordkey = 0; // ��ʹ�0
		wrongwordkey1 = 0;
		set(4, "1"); // �ڼ��Ŵʹ�1
		set(5, "2"); // root��2 ��Ϊroot1 �ǵڶ�λ
		set(6, "0"); // ��ʱ���0
		set(7, "0");
		set(8, "0");
		set(9, "0");

		setscore(0, 0);
		setscore(1, 0);

		setdefwordscore(0, 0);
		setdefwordscore(1, 0);

		setidrootscore(0, 0);
		setidrootscore(1, 0);

		setrootscore(0, 0);
		setrootscore(1, 0);

		setrepreatcontrol(0); // TTѭ�����Ʒ���0

		for (int i = 0; i < wrongwords.length; i++) {
			for (int j = 0; j < wrongwords[0].length; j++) {
				wrongwords[i][j] = "";
			}

		}
		for (int i = 0; i < wrongwords1.length; i++) {
			for (int j = 0; j < wrongwords1[0].length; j++) {
				wrongwords1[i][j] = "";
			}

		}

		reviewwrongcontrol = 0; // ���ѭ�����Ʒ���0

		for (int i = 0; i < Cwrongwords.length; i++) {
			for (int j = 0; j < Cwrongwords[0].length; j++) {
				Cwrongwords[i][j] = "";
			}
		}

		for (int i = 0; i < Lwrongwords.length; i++) {
			for (int j = 0; j < Lwrongwords[0].length; j++) {
				Lwrongwords[i][j] = "";
			}
		}
		stopPulseSound();
		pulseSoundStreamID = 0;
		constantCorrectCount = 0;
	}
	
	public void setComboBeginningState()
	{
		pulseSoundStreamID = 0;
		constantCorrectCount = 0;
	}

	public String[][] getwrongwords() {

		return wrongwords;

	}

	public String[][] getCwrongwords() {

		return Cwrongwords;
	}

	public String[][] getLwrongwords() {

		return Lwrongwords;
	}

	public void playmusic(int key) {

		if (buttonsound == 1) {

			if (key == 1) {
				sp.play(musicright, buttonvolume, buttonvolume, 1, 0, 1);

			}

			if (key == 0) {

				sp.play(musicwrong, buttonvolume, buttonvolume, 1, 0, 1);
			}

			if (key == 2) {
				sp.play(musicalarm, buttonvolume, buttonvolume, 1, 0, 1);
			}
			if (key == 3) {
				sp.play(musicfailure, buttonvolume, buttonvolume, 1, 0, 1);
			}
			if (key == 4) {
				sp.play(musictick, buttonvolume, buttonvolume, 1, 0, 1);
			}
			if (key == 5) {
				sp.play(musicexplosion, buttonvolume, buttonvolume, 1, 0, 1);
			}
		} else {

		}
	}

	public void startlevelmusic() {

		if (musicsound == 1) {

			if (level.equals("1")) {
				mediaPlayerlv1.setVolume(musicvolume, musicvolume);
				mediaPlayerlv1.start();
			}
			if (level.equals("2")) {
				mediaPlayerlv2.setVolume(musicvolume, musicvolume);
				mediaPlayerlv2.start();
			}
			if (level.equals("3")) {
				mediaPlayerlv3.setVolume(musicvolume, musicvolume);
				mediaPlayerlv3.start();
			}

			if (level.equals("4")) {
				mediaPlayerlv4.setVolume(musicvolume, musicvolume);
				mediaPlayerlv4.start();
			}
			if (level.equals("5")) {
				mediaPlayerlv5.setVolume(musicvolume, musicvolume);
				mediaPlayerlv5.start();
			}
			if (level.equals("6")) {
				mediaPlayerlv6.setVolume(musicvolume, musicvolume);
				mediaPlayerlv6.start();
			}
			if (level.equals("7")) {
				mediaPlayerlv7.setVolume(musicvolume, musicvolume);
				mediaPlayerlv7.start();
			}
			if (level.equals("8")) {
				mediaPlayerlv8.setVolume(musicvolume, musicvolume);
				mediaPlayerlv8.start();
			}

		}
	}
	
	public void pauselevelmusic() {

		if (musicsound == 1) {

			if (level.equals("1")) {
				mediaPlayerlv1.pause();
			}
			if (level.equals("2")) {
				mediaPlayerlv2.pause();
			}
			if (level.equals("3")) {
				mediaPlayerlv3.pause();
			}

			if (level.equals("4")) {
				mediaPlayerlv4.pause();
			}
			if (level.equals("5")) {
				mediaPlayerlv5.pause();
			}
			if (level.equals("6")) {
				mediaPlayerlv6.pause();
			}
			if (level.equals("7")) {
				mediaPlayerlv7.pause();
			}
			if (level.equals("8")) {
				mediaPlayerlv8.pause();
			}

		}
	}
	
	
	
	
	
	

	
	
	
	public void stoplevelmusic() {

	

			if (level.equals("1")) {
				mediaPlayerlv1.stop();
				mediaPlayerlv1.release();
				mediaPlayerlv1 = MediaPlayer.create(this, R.raw.tick);
				mediaPlayerlv1.setLooping(true);
			}
			if (level.equals("2")) {
				mediaPlayerlv2.stop();
				mediaPlayerlv2.release();
				mediaPlayerlv2 = MediaPlayer.create(this, R.raw.tick);
				mediaPlayerlv2.setLooping(true);
			}
			if (level.equals("3")) {
				mediaPlayerlv3.stop();
				mediaPlayerlv3.release();
				mediaPlayerlv3 = MediaPlayer.create(this, R.raw.tick);
				mediaPlayerlv3.setLooping(true);
			}

			if (level.equals("4")) {
				mediaPlayerlv4.stop();
				mediaPlayerlv4.release();
				mediaPlayerlv4 = MediaPlayer.create(this, R.raw.tick);
				mediaPlayerlv4.setLooping(true);
			}
			if (level.equals("5")) {
				mediaPlayerlv5.stop();
				mediaPlayerlv5.release();
				mediaPlayerlv5 = MediaPlayer.create(this, R.raw.tick);
				mediaPlayerlv5.setLooping(true);
			}
			if (level.equals("6")) {
				mediaPlayerlv6.stop();
				mediaPlayerlv6.release();
				mediaPlayerlv6 = MediaPlayer.create(this, R.raw.tick);
				mediaPlayerlv6.setLooping(true);
			}
			if (level.equals("7")) {
				mediaPlayerlv7.stop();
				mediaPlayerlv7.release();
				mediaPlayerlv7 = MediaPlayer.create(this, R.raw.tick);
				mediaPlayerlv7.setLooping(true);
			}
			if (level.equals("8")) {
				mediaPlayerlv8.stop();
				mediaPlayerlv8.release();
				mediaPlayerlv8 = MediaPlayer.create(this, R.raw.tick);
				mediaPlayerlv8.setLooping(true);
			}

		
	}

	public void startsplashmusic() {

		if (musicsound == 1) {
			mediaPlayer.setVolume(splashmusicvolume, splashmusicvolume);
			mediaPlayer.start();
			
		}

	}

	public void pausesplashmusic() {

		if (musicsound == 1) {
			mediaPlayer.pause();
		}

	}

	public void stopsplashmusic() {

		mediaPlayer.stop();
		mediaPlayer.release();
		mediaPlayer = MediaPlayer.create(this, R.raw.splashmusic);
	
		mediaPlayer.setLooping(true);

	}

	public String underlineclear(String key) {
		String flag = key.replace("_", " ");
		return flag;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		splashscreen = false;

		db = new managedb(this);

		db.CopyDatabase();
		
		/*db set default*/
		db.setdefault();

		int[] k = db.getacount();

		buttonsound = k[0];
		musicsound = k[1];
		misroot = k[2];

		definition = k[3];
		idroot = k[4];
		root = k[5];

		greenhelp = k[6];
		
		video=k[7];

		vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

		sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);// ��һ������Ϊͬʱ���������������������ڶ����������ͣ�����Ϊ��������
		sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
		musicright = sp.load(this, R.raw.right, 1); // ����������زķŵ�res/raw���2��������Ϊ��Դ�ļ�����3��Ϊ���ֵ����ȼ�
		musicwrong = sp.load(this, R.raw.wrong, 1); // ����������زķŵ�res/raw���2��������Ϊ��Դ�ļ�����3��Ϊ���ֵ����ȼ�\
		musicalarm = sp.load(this, R.raw.timealarm, 1);
		musicfailure = sp.load(this, R.raw.failure, 1);
		musictick = sp.load(this, R.raw.tick, 1);
		musicscorepulse = sp.load(this, R.raw.scorepulse, 1);
		musicexplosion = sp.load(this, R.raw.soundexplosion, 1);
		
		buttonvolume=Float.parseFloat(getResources().getString(R.string.buttonvolume));

	    musicvolume=Float.parseFloat(getResources().getString(R.string.musicvolume));
	    
	    splashmusicvolume=Float.parseFloat(getResources().getString(R.string.splashmusicvolume));
		
		mediaPlayer = MediaPlayer.create(this, R.raw.splashmusic);		
		mediaPlayer.setLooping(true);		
		mediaPlayerlv1 = MediaPlayer.create(this, R.raw.tick);
		mediaPlayerlv1.setLooping(true);
		mediaPlayerlv2 = MediaPlayer.create(this, R.raw.tick);
		mediaPlayerlv2.setLooping(true);
		mediaPlayerlv3 = MediaPlayer.create(this, R.raw.tick);
		mediaPlayerlv3.setLooping(true);
		mediaPlayerlv4 = MediaPlayer.create(this, R.raw.tick);
		mediaPlayerlv4.setLooping(true);
		mediaPlayerlv5 = MediaPlayer.create(this, R.raw.tick);
		mediaPlayerlv5.setLooping(true);
		mediaPlayerlv6 = MediaPlayer.create(this, R.raw.tick);
		mediaPlayerlv6.setLooping(true);
		mediaPlayerlv7 = MediaPlayer.create(this, R.raw.tick);
		mediaPlayerlv7.setLooping(true);
		mediaPlayerlv8 = MediaPlayer.create(this, R.raw.tick);
		mediaPlayerlv8.setLooping(true);
		
		
		
		mediaPlayer.setVolume(1, 1);
		mediaPlayerlv1.setVolume(3, 3);
		mediaPlayerlv2.setVolume(3, 3);
		mediaPlayerlv3.setVolume(3, 3);
		mediaPlayerlv4.setVolume(3, 3);
		mediaPlayerlv5.setVolume(3, 3);
		mediaPlayerlv6.setVolume(3, 3);
		mediaPlayerlv7.setVolume(3, 3);
		mediaPlayerlv8.setVolume(3, 3);
		

		clicknum = 0;
		rightnum = 0;
		idrootclicknum = 0;
		idrootrightnum = 0;
		defwordclicknum = 0;
		defwordrightnum = 0;
		rootclicknum = 0;
		rootrightnum = 0;

		reviewwrongcontrol = 0;
		wrongwordkey = 0;
		wrongwordkey1 = 0;
		setlistnum(0);
		set(0, "test");
		set(4, Integer.toString(1));
		set(5, "2"); // root ��ʼλ��
		set(6, "0");
		set(7, "0"); // level3
		set(8, "0"); // lv1
		set(9, "0"); // lv2
		setrepreatcontrol(0);

		for (int i = 0; i < wrongwords.length; i++) {
			for (int j = 0; j < wrongwords[0].length; j++) {
				wrongwords[i][j] = "";
			}

		}

		for (int i = 0; i < wrongwords1.length; i++) {
			for (int j = 0; j < wrongwords1[0].length; j++) {
				wrongwords1[i][j] = "";
			}

		}

		for (int i = 0; i < crootword.length; i++) {
			for (int j = 0; j < crootword[0].length; j++) {
				crootword[i][j] = "";
			}
		}

		for (int i = 0; i < Cwrongwords.length; i++) {
			for (int j = 0; j < Cwrongwords[0].length; j++) {
				Cwrongwords[i][j] = "";
			}
		}

		for (int i = 0; i < Lwrongwords.length; i++) {
			for (int j = 0; j < Lwrongwords[0].length; j++) {
				Lwrongwords[i][j] = "";
			}
		}

		for (int i = 0; i < emptywords.length; i++) {
			for (int j = 0; j < emptywords[0].length; j++) {

				emptywords[i][j] = "";

			}
		}
	}
	
	public void toastImage()
	{
		final Toast showImageToast=new Toast(getBaseContext());
		LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View toastView = inflater.inflate( R.layout.toastbombimage, null );
		
		ImageView imageView = (ImageView)toastView.findViewById(R.id.image);
		toastView.getBackground().setAlpha(0);
		
		imageView.setImageResource(R.drawable.boom);
		
		showImageToast.setGravity(Gravity.CENTER, 0, 0);
		showImageToast.setDuration(Toast.LENGTH_LONG);
		showImageToast.setView(toastView);
		showImageToast.show();
        
        Handler handlerToast = new Handler();
        handlerToast.postDelayed(new Runnable() {
           @Override
           public void run() {
        	   showImageToast.cancel(); 
           }
        }, 1000);
	}
	
	public Spannable setFillInBlankViewEffect(String viewText, String rootText)
	{
		int start = viewText.indexOf(rootText);
		int end = start + rootText.length();
		Spannable span = new SpannableString(viewText);
		span.setSpan(new RelativeSizeSpan(1.2f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		span.setSpan(new ForegroundColorSpan(Color.parseColor("#00CCFF")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		
		return span;
	}
}
