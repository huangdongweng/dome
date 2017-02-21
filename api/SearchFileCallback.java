package com.example.administrator.phonesefe.api;

/**
 * Created by Administrator on 2016/12/28.
 */

public interface SearchFileCallback {
    void searching(long fileSize);//检索到文件时会调用的方法
    void ending(boolean isEnd);//文件检索完毕的时，会调的方法

}
