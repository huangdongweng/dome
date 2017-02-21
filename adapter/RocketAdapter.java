package com.example.administrator.phonesefe.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.base.BaseBaseAdapter;
import com.example.administrator.phonesefe.entity.Rocketinfo;

/**
 * Created by Administrator on 2016/12/27.
 */

public class RocketAdapter extends BaseBaseAdapter <Rocketinfo>{

    public RocketAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder holder=null;
        if (view==null){
            view=inflater.inflate(R.layout.adapter_rockete_item,null);
            holder=new MyHolder();
            //初始化
            holder.ischeck= (CheckBox) view.findViewById(R.id.adapter_rocket_item_ischeck_cb);
            holder.icom= (ImageView) view.findViewById(R.id.adapter_rocket_item_img_iv);
            holder.appName= (TextView) view.findViewById(R.id.adapter_rocket_item_name_tv);
            holder.packageName= (TextView) view.findViewById(R.id.adapter_rocket_item_packageName_tv);
            holder.size= (TextView) view.findViewById(R.id.adapter_rocket_item_size_tv);
            //标记
            view.setTag(holder);
        }else {
            holder= (MyHolder) view.getTag();
        }
        //给控件赋值
        //获得Rocketinfo对象
        final Rocketinfo info=getItem(i);
        //是否选中
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setIscheck(!info.ischeck());
            }
        };
        holder.ischeck.setChecked(info.ischeck());
        holder.ischeck.setOnClickListener(listener);
        try {
            //设置图标
            holder.icom.setImageDrawable(context.getPackageManager().getApplicationIcon(info.getInfo().processName));
            //设置应用名
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(info.getInfo().processName, PackageManager.MATCH_UNINSTALLED_PACKAGES);
            holder.appName.setText(context.getPackageManager().getApplicationLabel(appInfo));
        } catch (PackageManager.NameNotFoundException e) {
            holder.icom.setImageResource(R.mipmap.ic_launcher);
            holder.appName.setText("未知应用");
        }
        //应用包名
        holder.appName.setText(info.getPackageName());
        return view;
    }
    class MyHolder{
        CheckBox ischeck;
        ImageView icom;
        TextView appName;
        TextView packageName;
        TextView size;
    }
}
