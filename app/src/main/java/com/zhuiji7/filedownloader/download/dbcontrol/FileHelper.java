package com.zhuiji7.filedownloader.download.dbcontrol;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 类功能描述：文件操作辅助类</br>
 *
 * @author zhuiji7
 * @email 470508081@qq.com
 * @version 1.0
 * </p>
 */
public class FileHelper {
    private static String userID = "luffy";
    private static String baseFilePath = Environment.getExternalStorageDirectory().toString()+ "/filedownloader";
    private static String dowloadFilePath = baseFilePath + "/" + userID + "/FILETEMP";
    /**下载文件的临时路径*/
    private static String tempDirPath = baseFilePath + "/" + userID + "/TEMPDir";



    private static String[] wrongChars = {
        "/", "\\", "*", "?", "<", ">", "\"", "|"};
    // 创建文件
    public void newFile(File f) {
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**创建目录
     * @param
     * */
    public static void newDirFile(File f) {
        if (!f.exists()) {
            f.mkdirs();
        }
    }





    // 获取一个文件列表的里的总文件大小
    public static double getSize(List<String> willupload) {
        return (double)getSizeUnitByte(willupload) / (1024 * 1024);
    };
    
    /**
     * 计算文件的大小，单位是字节
     * @param willupload
     * @return
     */
    public static long getSizeUnitByte(List<String> willupload){
        long allfilesize = 0;
        for (int i = 0; i < willupload.size(); i++) {
            File newfile = new File(willupload.get(i));
            if (newfile.exists() && newfile.isFile()) {
                allfilesize = allfilesize + newfile.length();
            }
        }
        return allfilesize;
    }

    /**
     * 获取默认文件存放路径
     */
    public static String getFileDefaultPath() {
        return dowloadFilePath;
    }

    /**获取下载文件的临时路径*/
    public static String getTempDirPath() {
        return tempDirPath;
    }

    /**  
     *  复制单个文件  
     *  @param  oldPath  String  原文件路径  如：c:/fqf.txt  
     *  @param  newPath  String  复制后路径  如：f:/fqf.txt  
     *  @return  boolean  
     */  
    public static boolean  copyFile(String  oldPath,  String  newPath)  {
        boolean iscopy = false;
        InputStream  inStream  =  null;  
        FileOutputStream  fs  =  null;
        try  { 
            int  byteread  =  0;  
            File  oldfile  =  new  File(oldPath);  
            if  (oldfile.exists()){  //文件存在时  
                inStream  =  new  FileInputStream(oldPath); //读入原文件  
                fs  =  new  FileOutputStream(newPath);
                byte[]  buffer  =  new  byte[1024];  
                while  ((byteread  =  inStream.read(buffer))  !=  -1)  {  
                    fs.write(buffer,  0,  byteread);  
                }
                iscopy = true;
            }  
        }  
        catch  (Exception  e)  {  
            e.printStackTrace();  
        }finally{
            try {
                if(inStream != null){
                    inStream.close();
                } 
            } catch (IOException e) {
                e.printStackTrace();
            } 
            try {
                if(fs != null){
                    fs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }  
        return iscopy;
    }

    public static void setUserID(String newUserID){
        userID = newUserID;
        dowloadFilePath = baseFilePath + "/" + userID + "/FILETEMP";
        tempDirPath = baseFilePath + "/" + userID + "/TEMPDir";
    }

    public static String getUserID(){
        return userID;
    };
    
    /**
     * 过滤附件ID中某些不能存在在文件名中的字符
     */
    public static String filterIDChars(String attID) {
        if (attID != null) {
            for (int i = 0; i < wrongChars.length; i++) {
                String c = wrongChars[i];
                if (attID.contains(c)) {
                    attID = attID.replaceAll(c, "");
                }
            }
        }
        return attID;
    }


    /**
     * 获取过滤ID后的文件名
     */
    public static String getFilterFileName(String flieName) {
        if (flieName == null || "".equals(flieName)) {
            return flieName;
        }
        boolean isNeedFilter = flieName.startsWith("(");
        int index = flieName.indexOf(")");
        if (isNeedFilter && index != -1) {
            int startIndex = index + 1;
            int endIndex = flieName.length();
            if (startIndex < endIndex) {
                return flieName.substring(startIndex, endIndex);
            }
        }
        return flieName;
    }
    

}
