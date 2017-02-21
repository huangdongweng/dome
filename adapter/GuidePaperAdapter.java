package com.example.administrator.phonesefe.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */

public class GuidePaperAdapter extends PagerAdapter{
    private List<View>data;

    public GuidePaperAdapter(List<View> data) {
        this.data = data;
    }
    /**返回ViewPager要显示页面的数量*/
    @Override
    public int getCount() {
        return data.size();
    }
    /**判断视图是否来自View对象*/
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    /**添加下一个即将显示的View*/
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        //讲数据源中的View对像添加到容器中（如果显示，如何显示）
        container.addView(data.get(position));
        return data.get(position);
    }
    /**销毁上一个页面*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(data.get(position));
    }
}
