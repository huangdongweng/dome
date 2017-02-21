package com.example.administrator.phonesefe.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.phonesefe.entity.AppRubbish;
import com.example.administrator.phonesefe.entity.ClassListinfo;
import com.example.administrator.phonesefe.entity.TeNumber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 * 管理数据库
 *      Assets 文件夹下的资源会随着apk的打包，跟apk一起发布
 *              不能直接被操作，如果想要操作assets文件夹下的资源，
 *              需要先把资源写入内存中，然后操作内存的文件。
 */

public class DBManager {
    /***将assets文件夹的数据库，写入内存中*/
    public static void copyDbFileToStorage(Context context,File toFile,String dbName){
        //声明输入和输出流
        InputStream is=null;
        OutputStream os=null;
        try {
            is=context.getAssets().open(dbName);
            os= new  FileOutputStream(toFile);
            byte []by=new byte[1024*2];
            int len=0;
            while ((len=is.read(by))!=-1){
                os.write(by,0,len);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * @param context
     * @return  分类列表
     */
    public static List<ClassListinfo> readClassListinfo(Context context){
        List<ClassListinfo> list=new ArrayList<>();
        File file=new File(context.getCacheDir(),"commonnum.db");
        //创建数据库所在的文件对象      获取根目录，文件命名
        if (file.exists()){//判断文件是否存在
            //打开火创建一个数据库对象              打开数据库
            SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(file,null);
            //查询数据：查表classlist中的name和idx字段返回一个cursor游标
            Cursor cursor=db.query("classlist",new String[]{"name","idx"},null,null,null,null,null);
            if (cursor!=null){
                ClassListinfo info=null;
                while (cursor.moveToNext()){
                    String name=cursor.getString(cursor.getColumnIndex("name"));
                    int idx=cursor.getInt(cursor.getColumnIndex("idx"));
                    info=new ClassListinfo(name,idx);//创建对象
                    list.add(info);//存入集合
                }
                //关掉游标
                cursor.close();
                db.close();
                return list;
            }
        }else {
            //文件不存在         自己写入文件
            copyDbFileToStorage(context,new File(context.getCacheDir(),"commonnum.db"),"commonnum.db");
        }
        //递归  返回方法本身
        return readClassListinfo(context);
    }
    /**
     * @param context   上下文
     * @param idx   查询那张表
     * @return  电话号码
     */
    public static List<TeNumber> readTeNumber(Context context,int idx){
        //创建集合
        List<TeNumber> list=new ArrayList<>();
        //创建数据库所在的文件对象      获取根目录，文件命名
        File file=new File(context.getCacheDir(),"commonnum.db");
            //打开火创建一个数据库对象              打开数据库
        SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(file,null);
            //查询数据：查表classlist中的name和idx字段返回一个cursor游标
        Cursor cursor=db.query("table"+idx,new String[]{"name","number"},null,null,null,null,null);
        if (cursor!=null) {
            TeNumber info = null;
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String number = cursor.getString(cursor.getColumnIndex("number"));
                info = new TeNumber(name, number);//创建对象
                list.add(info);//存入集合
            }
            //关掉游标
            cursor.close();
            db.close();
            return list;
        }
        return null;
    }
    /**
     *读取数据库中的垃圾软件
     * @param context
     * @return
     */
    public static List<AppRubbish> readAppRubbish(Context context){
        List<AppRubbish> list =new ArrayList<>();
        File file=new File(context.getFilesDir(),"clearpath.db");
        if (file.exists()){
            SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(file,null);
            Cursor cursor=db.query("softdetail",null,null,null,null,null,null);
            if (cursor!=null){
                AppRubbish rubbish=null;
                while (cursor.moveToNext()){
                    String chineseName=cursor.getString(cursor.getColumnIndex("softChinesename"));
                    String englishName=cursor.getString(cursor.getColumnIndex("softEnglishname"));
                    String packageName=cursor.getString(cursor.getColumnIndex("apkname"));
                    String filePath=cursor.getString(cursor.getColumnIndex("filepath"));
                    rubbish=new AppRubbish(chineseName,englishName,packageName,filePath);
                    list.add(rubbish);
                }
            }
            cursor.close();
            db.close();
            return list;
        }else {
            copyDbFileToStorage(context,new File(context.getFilesDir(),"clearpath.db"),"clearpath.db");
        }
        return readAppRubbish(context);
    }
}
