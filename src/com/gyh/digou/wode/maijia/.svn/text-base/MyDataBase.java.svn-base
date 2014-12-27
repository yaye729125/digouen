package com.gyh.digou.wode.maijia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

//�̳�SQLiteOpenHelper
public class MyDataBase extends SQLiteOpenHelper {
	public MyDataBase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	// private static final String DB_NAME = "mydata.db";
	private static final int version =1;
	// ���������
	String createtable = "create table student(_id integer primary "
			+ "key autoincrement , title , things,time)";

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL(createtable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
