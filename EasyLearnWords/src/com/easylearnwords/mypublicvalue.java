package com.easylearnwords;

import java.io.StreamCorruptedException;

import android.R.integer;
import android.app.Application;
import android.media.AudioManager;
import android.media.SoundPool;

public class mypublicvalue extends Application {

	public String tablename; // 0 代表表名 1代表list名 2代表几号list的阿拉伯数字
	public String listname;
	public String list;
	public String[][] words;
	public String[][] wordslv1;
	public String[][] wordslv2;
	public String level; // 3代表level多少
	public String wordnum; // 4代表学到几号词了

	public String step; // 代表root的时候第几步； seq 为5

	public String definitionrepeat; // 命名为definition+word+root图重复次数， seq 为6

	public String controllv3; // 控制level3的控制阀。 seq为7.

	public String lv1, lv2; // 控制level1 和 2的 随机取值控制阀 。 seq 分别为 8 和 9

	public int repeatcontrol; // tt 循环控制阀值

	public String[][] wrongwords = new String[300][10];
	public String[][] wrongwords1= new String[300][10];

	public int wrongwordkey,wrongwordkey1;

	public String[][] Cwrongwords = new String[21][10];
	public String[][] Lwrongwords = new String[21][10];

	public String[][] ranwords;

	public String[] x = new String[10];
	public String[] e = new String[10];
	
	public double clicknum;               //点击多少个activity次数
	public double rightnum;               //做对了多少个activity次数

	public int reviewwrongcontrol;

	private SoundPool sp;// 声明一个SoundPool

	private int musicright;// 定义一个整型用load（）；来设置suondID
	private int musicwrong;
	private int musicalarm;
	private int musicfailure;

	public String[][] emptywords = new String[21][10];

	public int numwords; // 每个list中words不为空的数量
	
	
	
	public double getscore(int key){
		double flag=0;
		if (key==0) {
			flag=clicknum;
		}
		if (key==1) {
			flag=rightnum;
		}
		return flag;
	}
	
	
	
	public void setscore(int key,double number)
	{
		if (key==0) {
			clicknum=number;
		}
		if (key==1) {
			rightnum=number;
		}
		
		
	}
	
	
	
	
	

	public void setranwords(String[][] key) {
		ranwords = key;

	}

	public String[][] getranwords() {

		return ranwords;

	}

	public String[][] emptywords() { // 提供空words数组的方法

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
		if (seq == 9) {
			flag = lv2;
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
		if (seq == 9) {
			lv2 = value;
		}

	}

	public void setwords(String[][] words) { // 赋值words的同时也把words中不为空的数目确定

		this.words = words;

	}

	public void addwrongword(String[] word) {

		wrongwords[wrongwordkey] = word;
		wrongwordkey++;

	}
	
	public void addwrongwords1(String[] word){
		wrongwords1[wrongwordkey1]=word;
		wrongwordkey1++;
	}
	
	
	public void cleanCwrongwords() // 注意这个wrongword记住循环的错误结果 然后通过快速排序，去掉重复的项

	{

		quicksort1(0, wrongwords1.length - 1);

		System.out.println("测试到快速排序");

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
		System.out.println(Lwrongwords[20][0] + "Cwrong");

	}

	
	
	

	public void cleanwrongwords() // 注意这个wrongword记住循环的错误结果 然后通过快速排序，去掉重复的项

	{

		quicksort(0, wrongwords.length - 1);

		System.out.println("测试到快速排序");

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
		System.out.println(Cwrongwords[20][0] + "Cwrong21");

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
	
	
	public void empty1(){
		
		
	

		set(4, "1"); // 第几号词归1
		set(5, "2"); // root归2 因为root1 是第二位
		set(6, "0"); // 错词变红归0
		set(7, "0");
		set(8, "0");
		set(9, "0");
		
		setscore(0, 0);
		setscore(1, 0);
		
		setrepreatcontrol(0); // TT循环控制阀归0
		
		
		
			
		

	
		
		
	}
	
	

	public void empty() {   //0代表清除错词，1代表完全归零

		
		wrongwordkey = 0; // 错词归0
        wrongwordkey1=0;
		set(4, "1"); // 第几号词归1
		set(5, "2"); // root归2 因为root1 是第二位
		set(6, "0"); // 错词变红归0
		set(7, "0");
		set(8, "0");
		set(9, "0");
		
		setscore(0, 0);
		setscore(1, 0);
		
		setrepreatcontrol(0); // TT循环控制阀归0
		
		
		
			
		

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
		 
			
			
			reviewwrongcontrol = 0; // 错词循环控制阀归0
			
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

		if (key == 1) {
			sp.play(musicright, 1, 1, 0, 0, 1);

		}

		if (key == 0) {

			sp.play(musicwrong, 1, 1, 0, 0, 1);
		}

		if (key == 2) {
			sp.play(musicalarm, 1, 1, 0, 0, 1);
		}
		if (key == 3) {
			sp.play(musicfailure, 1, 1, 0, 0, 1);
		}
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);// 第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
		musicright = sp.load(this, R.raw.right, 1); // 把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
		musicwrong = sp.load(this, R.raw.wrong, 1); // 把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级\
		musicalarm = sp.load(this, R.raw.timealarm, 1);
		musicfailure = sp.load(this, R.raw.failure, 1);

		clicknum=0;
		rightnum=0;
		reviewwrongcontrol = 0;
		wrongwordkey = 0;
		wrongwordkey1=0;
		set(0, "test");
		set(4, Integer.toString(1));
		set(5, "2"); // root 起始位置
		set(6, "0");
		set(7, "0");  // level3 
		set(8, "0");  //lv1
		set(9, "0");  // lv2
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
}
