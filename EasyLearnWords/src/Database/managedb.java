package Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import com.easylearnwords.MainActivity;
import com.easylearnwords.mypublicvalue;

import junit.framework.Assert;
import android.R.integer;
import android.R.string;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class managedb {

	public static final int listwordnum = 20;
	public mypublicvalue myapp;
	public dbopenhelper helper;
	private final int BUFFER_SIZE = 5120;
	public static final String DB_NAME = "mydb.db"; // 保存的数据库文件名
	
	public static final String WRONG_NAME="wrong.mp3";
	public static final String RIGHT_NAME="right.mp3";
	public static final String PACKAGE_NAME = "com.easylearnwords";// 工程包名
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME + "/databases"; // 在手机里存放数据库的位置
	public static final String B_PATH="/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME; 
	
	private Context context;

	public String[][] words;     //20个单词缓存在这个字符串数组中  需要21个，因为最后一个可以当做结束符

	public managedb(Context context) {
		this.context = context;
		helper = new dbopenhelper(context);
		myapp = (mypublicvalue) context.getApplicationContext();
	}

	public String path() {
		String string = DB_PATH;
		return DB_PATH;
	}

	public void CopyDatabase() {
		String dbfile = DB_PATH + "/" + DB_NAME;
		String rbfile = B_PATH + "/" + RIGHT_NAME;
		String wbfile = B_PATH + "/" + WRONG_NAME;
		File path = new File(dbfile);
        File rpath= new File(rbfile);
        File wpath = new File(wbfile); 
		if (!path.exists()) { // 文件夹不存在必须先创建文件夹
			File dbpath = new File(DB_PATH);
			dbpath.mkdir();

			AssetManager am = this.context.getAssets();

			try {
				// 执行数据库导入
				InputStream is = am.open(DB_NAME);// this.context.getResources().getAssets().open(DB_NAME);
													// //欲导入的数据库
				FileOutputStream fos = new FileOutputStream(dbfile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();// 关闭输出流
				is.close();// 关闭输入流
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
			
		if (!rpath.exists()) { // 文件夹不存在必须先创建文件夹
			File rbpath = new File(B_PATH);
			rbpath.mkdir();

			AssetManager am = this.context.getAssets();

				

				try {
					// 执行数据库导入
					InputStream is = am.open(RIGHT_NAME);// this.context.getResources().getAssets().open(DB_NAME);
														// //欲导入的数据库
					FileOutputStream fos = new FileOutputStream(rbfile);
					byte[] buffer = new byte[BUFFER_SIZE];
					int count = 0;
					while ((count = is.read(buffer)) > 0) {
						fos.write(buffer, 0, count);
					}
					fos.close();// 关闭输出流
					is.close();// 关闭输入流
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
			
	
		
		if (!wpath.exists()) { // 文件夹不存在必须先创建文件夹
			File wbpath = new File(B_PATH);
			wbpath.mkdir();

			AssetManager am = this.context.getAssets();
				

				try {
					// 执行数据库导入
					InputStream is = am.open(WRONG_NAME);// this.context.getResources().getAssets().open(DB_NAME);
														// //欲导入的数据库
					FileOutputStream fos = new FileOutputStream(wbfile);
					byte[] buffer = new byte[BUFFER_SIZE];
					int count = 0;
					while ((count = is.read(buffer)) > 0) {
						fos.write(buffer, 0, count);
					}
					fos.close();// 关闭输出流
					is.close();// 关闭输入流
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
			
	}

	public List<String> listtablename(String[] selectionArgs) {// 返回多条记录
		// TODO Auto-generated method stub

		List<String> list = new ArrayList<String>();

		String sql = "select  name  from sqlite_master where type='table' and name<>'android_metadata' and name<>'sqlite_sequence' ";
		SQLiteDatabase database = null;
		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, selectionArgs); // 行查询返回是游标
			int colums = cursor.getColumnCount();

			while (cursor.moveToNext()) {

				for (int i = 0; i < colums; i++) {
					String colums_name = cursor.getColumnName(i);
					String colums_value = cursor.getString(cursor
							.getColumnIndex(colums_name));
					if (colums_value == null) {
						colums_value = "";
					}

				//	System.out.println(colums_value);

					list.add(colums_value);

				}

			}

		} catch (Exception e) {
			// TODO: handle exception

		} finally {
			if (database != null)
				database.close();
		}

		return list;
	}

	public int numlist(String tablename) {

		SQLiteDatabase database = null;

		int number = 0; // 用来储存有多少个字在表里
		String sql = "select count(*) from " + "'" + tablename + "'";
		//System.out.println(sql);

		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, null);
			int colums = cursor.getColumnCount();

			while (cursor.moveToNext()) { // 标准形式，否则cursor游标无效
				for (int i = 0; i < colums; i++) {
					String colums_name = cursor.getColumnName(i);
					int colums_value = cursor.getInt(cursor
							.getColumnIndex(colums_name));

					number = colums_value;

				
				}
			}

		} catch (Exception e) {
			// TODO: handle exception

		} finally {
			if (database != null)
				database.close();
		}

		return number / listwordnum + 1;   // 因为每个表至少包含5个单词所以必须有1个list 所以加1是正确的

	}

	public String[][] getwords() { // 取得20个单词

		words = new String[21][10];

		for (int i = 0; i < 21; i++) {     // 这里是初始化各个单词为""；

			for (int j = 0; j < 10; j++) {

				words[i][j] = "";
			}
		}

		int listnum = Integer.parseInt(myapp.get(2)); // 取得几号表，用来计算取单词

		String tablename = myapp.get(0);
		SQLiteDatabase database = null;
		int k = 0; // 二维字符串数组第一参数
		String sql = "select name, def, froot,fdef,sroot,sdef,troot,tdef,fouthroot,fouthdef from "
				+ tablename
				+ " where id<="
				+ listwordnum
				* listnum
				+ " and id> " + listwordnum * (listnum - 1);
		// System.out.println(sql);

		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.rawQuery(sql, null);
			int colums = cursor.getColumnCount();

			while (cursor.moveToNext()) { // 标准形式，否则cursor游标无效
				for (int i = 0; i < colums; i++) {
					String colums_name = cursor.getColumnName(i);
					String colums_value = cursor.getString(cursor
							.getColumnIndex(colums_name));

					if (colums_value == null) {
						colums_value = "";
					}

					// System.out.println(colums_name+"="+colums_value);

					if (colums_name.equals("name")) {
						words[k][i] = colums_value;
						// System.out.println(k+words[k][i]+i);

					}

					if (colums_name.equals("def")) {
						words[k][i] = colums_value;
					}
					if (colums_name.equals("froot")) {
						words[k][i] = colums_value;
					}
					if (colums_name.equals("fdef")) {
						words[k][i] = colums_value;
					}
					if (colums_name.equals("sroot")) {
						words[k][i] = colums_value;
					}
					if (colums_name.equals("sdef")) {
						words[k][i] = colums_value;
					}
					if (colums_name.equals("troot")) {
						words[k][i] = colums_value;
					}
					if (colums_name.equals("tdef")) {
						words[k][i] = colums_value;
					}
					if (colums_name.equals("fouthroot")) {
						words[k][i] = colums_value;
					}
					if (colums_name.equals("fouthdef")) {
						words[k][i] = colums_value;
					}

				}
				k++;
			}
		} catch (Exception e) {
			// TODO: handle exception

		} finally {
			if (database != null)
				database.close();
		}

		// System.out.println("test word200"+words[19][0]);

		return words;

	}
}
