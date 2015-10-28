//
//  SQLiteHelper.java
//  FeOA
//
//  Created by LuTH on 2011-12-17.
//  Copyright 2011 flyrise. All rights reserved.
//
package com.zhuiji7.filedownloader.download.dbcontrol;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 类功能描述：</br>
 *
 * @author zhuiji7
 * @email 470508081@qq.com
 * @version 1.0
 * </p>
 */
public class SQLiteHelper extends SQLiteOpenHelper {

	private static final String mDatabasename = "filedownloader";
	private static CursorFactory mFactory = null;
	private static final int mVersion = 1;
	public static final String TABLE_NAME = "downloadinfo"; //文件下载信息数据表名称

	public SQLiteHelper(Context context) {
		super(context, mDatabasename, mFactory, mVersion);
	}

	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		//创建文件下载信息数据表
		String downloadsql = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +" ("
                + "id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "
                + "userID VARCHAR, "
                + "taskID VARCHAR, " 
                + "url VARCHAR, " 
                + "filePath VARCHAR, " 
                + "fileName VARCHAR, " 
                + "fileSize VARCHAR, " 
                + "downLoadSize VARCHAR " 
                + ")";
        db.execSQL(downloadsql);
        

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}
}
