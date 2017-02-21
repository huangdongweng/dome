package com.example.administrator.phonesefe.biz;

import android.util.Log;

import com.example.administrator.phonesefe.api.SearchFileCallback;
import com.example.administrator.phonesefe.entity.Fileinfo;
import com.example.administrator.phonesefe.utils.FileTypeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/24.
 * 文件管理业务类
 */

public class FlieManager {
    /**单列模式*/
    private FlieManager(){init();}
    private static FlieManager manager=new FlieManager();
    public static  FlieManager getFileManager(){
        return manager;
    }
    /**内置存储卡*/
    private static File inSdStorageFile;
    /**外置存储卡*/
    private static File OutSdStorageFile;
    /**静态代码快初始化文件根路径*/
    static {
        if (MemeryManager.getSDstoragePath() != null) {
            inSdStorageFile =null;
            //打开内置路径文件
           inSdStorageFile= new File(MemeryManager.getSDstoragePath());
        }
        if (MemeryManager.getOutSDstoragePath()!=null){
            OutSdStorageFile =null;
            //打开外置路径文件
            OutSdStorageFile =new File(MemeryManager.getOutSDstoragePath());
        }
    }
    /**存储所有文件的集合*/
    private List<Fileinfo> allFile;
    private List<Fileinfo> docFile;
    private List<Fileinfo> videofFile;
    private List<Fileinfo> audioFile;
    private List<Fileinfo> imageFile;
    private List<Fileinfo> zipFile;
    private List<Fileinfo> apkFile;
    private long allFileSize;
    private long docFileSize;
    private long videofFileSize;
    private long audioFileSize;
    private long imageFileSize;
    private long zipFileSize;
    private long apkFileSize;
    /**初始化集合*/
    private void init(){
        allFile =new ArrayList<>();
        docFile =new ArrayList<>();
        videofFile =new ArrayList<>();
        audioFile =new ArrayList<>();
        imageFile =new ArrayList<>();
        zipFile =new ArrayList<>();
        apkFile =new ArrayList<>();
    }
    /**初始化数据*/
    private void  initData(){
        //清空集合数据
        allFile.clear();
        docFile.clear();
        videofFile.clear();
        audioFile.clear();
        imageFile.clear();
        zipFile.clear();
        apkFile.clear();
        //文件大小置为零
        allFileSize=0;
        docFileSize=0;
        videofFileSize=0;
        audioFileSize=0;
        imageFileSize=0;
        zipFileSize=0;
        apkFileSize=0;
    }
    /**回调的接口*/
    private SearchFileCallback listener;
    /**判断是否在检索文件*/
    private boolean isSearching=false;
    /**文件检索*/
    public void searchFiles(){
        if (isSearching){
            return;
        }else {
            //重置集合
            initData();
            search(inSdStorageFile,true);//内置存储卡,传入false，不让结果反馈文件检索
//            search(OutSdStorageFile,true);//外置存储卡，传入true，让结果可以反馈文件检索结果
        }
    }

    /**
     * 检索文件
     * @param file      要检索的文件
     * @param endFlag   用来判断是否回调文件检索结束方法  true 表示回调 ，false表示不回调
     */
    private void search(File file,boolean endFlag) {
        //#1.文件异常的情况：文件为空，或者不能读，或不存在
        if (file==null||!file.canRead()||!file.exists()){
            if (endFlag){
                searchend(true);//表示文件检索异常结束
            }
            return;
        }
        //#2.file为文件夹的情况
        if (file.isDirectory()){
            File[] files=file.listFiles();
            //判断文件夹中是否存在文件或文件夹
            if (files==null ||files.length<=0){
                return;
            }
            for (File  f:files){
                if (f!=null&&f.length()>0){
                    //递归检索文件夹中的文件，传入false，不让文件检索结束
                    search(f,false);
                }
            }
            if (endFlag){
                //如果可以反馈结果，反馈正常结束
                searchend(false);
            }
        }else {
            //#3.file为文件的情况
            if (file.length()<0){
                return;
            }
            //获取文件的图像及类型
            String[] iconAndNamw =FileTypeUtil.getFileIconAndTypeName(file);
            String icoName= iconAndNamw[0];
            String typeName =iconAndNamw[1];
            Fileinfo info=new Fileinfo(file,icoName,typeName);
            //获取文件的大小
            long size =getFileSize(file);
            //添加集合
            allFile.add(info);
            allFileSize+=size;
            //分类  , 按类型分类
            if (typeName.equals(FileTypeUtil.TYPE_IMAGE)){
                imageFile.add(info);
                imageFileSize+=size;
            }
            if (typeName.equals(FileTypeUtil.TYPE_APK)){
                apkFile.add(info);
                apkFileSize+=size;
            }
            if (typeName.equals(FileTypeUtil.TYPE_AUDIO)){
                audioFile.add(info);
                audioFileSize+=size;
            }
            if (typeName.equals(FileTypeUtil.TYPE_TXT)){
                docFile.add(info);
                docFileSize+=size;
            }
            if (typeName.equals(FileTypeUtil.TYPE_VIDEO)){
                videofFile.add(info);
                videofFileSize+=size;
            }
            if (typeName.equals(FileTypeUtil.TYPE_ZIP)){
                zipFile.add(info);
                zipFileSize+=size;
            }
            //每检索一个文件，回调一个方法
            searchFile(allFileSize);
        }


    }
    /**检索文件之后调用的方法*/
    private void searchFile(long fileSize){
        if (listener!=null){
            listener.searching(fileSize);
        }
    }
    /**文件检索完毕之后调用的方法*/
    private void searchend(boolean isEnd){
        if (listener!=null){
            listener.ending(isEnd);
        }
    }
    /**
     * 获取文件大小
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (!file.isDirectory()) {
            return file.length();
        } else {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    size += getFileSize(f);
                } else {
                    size += f.length();
                }
            }
        }
        return size;
    }
    /**
     * 文件删除的方法
     */
    public static void deleteFile(File file) {
        if (!file.isDirectory()) {//如果不是文件夹
            //删除文件
            file.delete();
        } else {//是文件夹
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    deleteFile(f);
                } else {
                    f.delete();
                }
            }
        }
    }

    public void setAllFileSize(long allFileSize) {
        this.allFileSize = allFileSize;
    }

    public void setDocFileSize(long docFileSize) {
        this.docFileSize = docFileSize;
    }

    public void setVideofFileSize(long videofFileSize) {
        this.videofFileSize = videofFileSize;
    }

    public void setAudioFileSize(long audioFileSize) {
        this.audioFileSize = audioFileSize;
    }

    public void setImageFileSize(long imageFileSize) {
        this.imageFileSize = imageFileSize;
    }

    public void setZipFileSize(long zipFileSize) {
        this.zipFileSize = zipFileSize;
    }

    public void setApkFileSize(long apkFileSize) {
        this.apkFileSize = apkFileSize;
    }

    public long getApkFileSize() {
        return apkFileSize;
    }

    public List<Fileinfo> getAllFile() {
        return allFile;
    }

    public List<Fileinfo> getDocFile() {
        return docFile;
    }

    public List<Fileinfo> getVideofFile() {
        return videofFile;
    }

    public List<Fileinfo> getAudioFile() {
        return audioFile;
    }

    public List<Fileinfo> getImageFile() {
        return imageFile;
    }

    public List<Fileinfo> getZipFile() {
        return zipFile;
    }

    public List<Fileinfo> getApkFile() {
        return apkFile;
    }

    public long getAllFileSize() {
        return allFileSize;
    }

    public long getDocFileSize() {
        return docFileSize;
    }

    public long getVideofFileSize() {
        return videofFileSize;
    }

    public long getAudioFileSize() {
        return audioFileSize;
    }

    public long getImageFileSize() {
        return imageFileSize;
    }

    public long getZipFileSize() {
        return zipFileSize;
    }

    public void setListener(SearchFileCallback listener) {
        this.listener = listener;
    }
}
