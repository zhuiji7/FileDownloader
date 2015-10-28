package com.zhuiji7.filedownloader.download.dbcontrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhuiji7.filedownloader.download.dbcontrol.bean.SQLDownLoadInfo;

import java.util.ArrayList;

/**
 * 类功能描述：信息存储类，主要在任务下载各个环节执行数据的存储</br>
 *
 * @author zhuiji7
 * @email 470508081@qq.com
 * @version 1.0
 * </p>
 */

public class DataKeeper {
    private SQLiteHelper dbhelper;
    private SQLiteDatabase db;
    private int doSaveTimes = 0;
    public DataKeeper(Context context){
        this.dbhelper = new SQLiteHelper(context);
    }
    /**
     * (保存一个任务的下载信息到数据库)
     * @param downloadInfo
     */
    public void saveDownLoadInfo(SQLDownLoadInfo downloadInfo){
        ContentValues cv = new ContentValues();
        cv.put("userID", downloadInfo.getUserID());
        cv.put("taskID", downloadInfo.getTaskID());
        cv.put("downLoadSize", downloadInfo.getDownloadSize());
        cv.put("fileName", downloadInfo.getFileName());
        cv.put("filePath", downloadInfo.getFilePath());
        cv.put("fileSize", downloadInfo.getFileSize());
        cv.put("url", downloadInfo.getUrl());
        Cursor cursor = null;
        try{
            db = dbhelper.getWritableDatabase();
            cursor = db.rawQuery(
                    "SELECT * from " + SQLiteHelper.TABLE_NAME 
                    + " WHERE userID = ? AND taskID = ? ", new String[]{downloadInfo.getUserID(),downloadInfo.getTaskID()});
            if(cursor.moveToNext()){
                db.update(SQLiteHelper.TABLE_NAME, cv, "userID = ? AND taskID = ? ", new String[]{downloadInfo.getUserID(),downloadInfo.getTaskID()});
            }else{
                db.insert(SQLiteHelper.TABLE_NAME, null, cv);
            }
            cursor.close();
            db.close();
        }catch(Exception e){
            doSaveTimes ++;
            if(doSaveTimes < 5){ //最多只做5次数据保存，降低数据保存失败率
                saveDownLoadInfo(downloadInfo);
            }else{
                doSaveTimes = 0; 
            }
            if(cursor != null){
                cursor.close();
            }
            if(db != null){
                db.close();
            }
        }
        doSaveTimes = 0;
    }
    public SQLDownLoadInfo getDownLoadInfo(String userID,String taskID){
        SQLDownLoadInfo downloadinfo= null;
        db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * from " + SQLiteHelper.TABLE_NAME 
                + "WHERE userID = ? AND taskID = ? ", new String[]{userID,taskID});
        if(cursor.moveToNext()){
            downloadinfo = new SQLDownLoadInfo();
            downloadinfo.setDownloadSize(cursor.getLong(cursor.getColumnIndex("downLoadSize")));
            downloadinfo.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
            downloadinfo.setFilePath(cursor.getString(cursor.getColumnIndex("filePath")));
            downloadinfo.setFileSize(cursor.getLong(cursor.getColumnIndex("fileSize")));
            downloadinfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            downloadinfo.setTaskID(cursor.getString(cursor.getColumnIndex("taskID")));
            downloadinfo.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
        }
        cursor.close();
        db.close();
        return downloadinfo;
    }
    public ArrayList<SQLDownLoadInfo> getAllDownLoadInfo(){
        ArrayList<SQLDownLoadInfo> downloadinfoList = new ArrayList<SQLDownLoadInfo>();
        db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * from " + SQLiteHelper.TABLE_NAME, null);
        while(cursor.moveToNext()){
            SQLDownLoadInfo downloadinfo = new SQLDownLoadInfo();
            downloadinfo.setDownloadSize(cursor.getLong(cursor.getColumnIndex("downLoadSize")));
            downloadinfo.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
            downloadinfo.setFilePath(cursor.getString(cursor.getColumnIndex("filePath")));
            downloadinfo.setFileSize(cursor.getLong(cursor.getColumnIndex("fileSize")));
            downloadinfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            downloadinfo.setTaskID(cursor.getString(cursor.getColumnIndex("taskID")));
            downloadinfo.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
            downloadinfoList.add(downloadinfo);
        }
        cursor.close();
        db.close();
        return downloadinfoList;

    }
    public ArrayList<SQLDownLoadInfo> getUserDownLoadInfo(String userID){
        ArrayList<SQLDownLoadInfo> downloadinfoList = new ArrayList<SQLDownLoadInfo>();
        db = dbhelper.getWritableDatabase();
        try {
            Cursor cursor = null;
            cursor = db.rawQuery(
                    "SELECT * from " + SQLiteHelper.TABLE_NAME + " WHERE userID = '" + userID +"'", null);
            while(cursor.moveToNext()){
                SQLDownLoadInfo downloadinfo = new SQLDownLoadInfo();
                downloadinfo.setDownloadSize(cursor.getLong(cursor.getColumnIndex("downLoadSize")));
                downloadinfo.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
                downloadinfo.setFilePath(cursor.getString(cursor.getColumnIndex("filePath")));
                downloadinfo.setFileSize(cursor.getLong(cursor.getColumnIndex("fileSize")));
                downloadinfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                downloadinfo.setTaskID(cursor.getString(cursor.getColumnIndex("taskID")));
                downloadinfo.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
                downloadinfoList.add(downloadinfo);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return downloadinfoList;
    }
    public void deleteDownLoadInfo(String userID,String taskID){
        db = dbhelper.getWritableDatabase();
        db.delete(SQLiteHelper.TABLE_NAME, "userID = ? AND taskID = ? ", new String[]{userID,taskID});
        db.close();
    }
    
    public void deleteUserDownLoadInfo(String userID){
        db = dbhelper.getWritableDatabase();
        db.delete(SQLiteHelper.TABLE_NAME, "userID = ? ", new String[]{userID});
        db.close();
    }
    
    public void deleteAllDownLoadInfo(){
        db = dbhelper.getWritableDatabase();
        db.delete(SQLiteHelper.TABLE_NAME, null, null);
        db.close();
    }
}
