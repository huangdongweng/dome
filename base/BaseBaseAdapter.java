package com.example.administrator.phonesefe.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public abstract class BaseBaseAdapter<T> extends BaseAdapter{
    protected Context context;
    protected LayoutInflater inflater;
    protected List<T> data=new ArrayList<>();
    public BaseBaseAdapter(Context context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
    }
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        //清除所有数据
        this.data.clear();
        //将新数据全部添加进集合
        this.data.addAll(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public abstract View getView(int i, View view, ViewGroup viewGroup);
}
