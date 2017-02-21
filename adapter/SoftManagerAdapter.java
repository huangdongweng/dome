package com.example.administrator.phonesefe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.base.BaseBaseAdapter;
import com.example.administrator.phonesefe.entity.SoftWareInfo;

/**
 * Created by Administrator on 2016/12/21.
 */

public class SoftManagerAdapter extends BaseBaseAdapter<SoftWareInfo> {
    public SoftManagerAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder holder=null;
        if (view==null){
            view=inflater.inflate(R.layout.adapter_softmanager,null);
            holder=new MyHolder();
            //找控件
            holder.icom= (ImageView) view.findViewById(R.id.adapter_softmanager_icon_iv);
            holder.isCheak= (CheckBox) view.findViewById(R.id.adapter_softmanager_ischeck_cb);
            holder.label= (TextView) view.findViewById(R.id.adapter_softmanager_label_tv);
            holder.packageName= (TextView) view.findViewById(R.id.adapter_softmanager_packageName_tv);
            holder.version= (TextView) view.findViewById(R.id.adapter_softmanager_version_tv);
            //标记
            view.setTag(holder);
        }else {
            holder= (MyHolder) view.getTag();
        }
        //给控件赋值
        final SoftWareInfo info=getItem(i);
        //监听事件
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setCheak(!info.isCheak());
            }
        };
        holder.label.setText(info.getLabel());
        holder.packageName.setText(info.getPackageName());
        if (info.getVersion().length()>5){
            holder.version.setText(info.getVersion().substring(0,5));
        }else {
            holder.version.setText(info.getVersion());
        }
        //判断应用图片是否为空
        if (info.getIcom()!=null){
            holder.icom.setImageDrawable(info.getIcom());
        }else {
            //默认图片
            holder.icom.setImageResource(R.mipmap.ic_launcher);
        }
        //判断是否被删除，
        if (info.isDelete()){
            holder.isCheak.setChecked(info.isCheak());
            holder.isCheak.setOnClickListener(listener);
        }else {//如果被删除，则设置按钮不可
            holder.isCheak.setChecked(false);
            holder.isCheak.setClickable(false);
        }
        return view;
    }
    class MyHolder{
        TextView label;//应用名
        ImageView icom;//应用图标
        TextView packageName;//应用包名
        TextView version;//版本号
        CheckBox isCheak;//是否选中

    }
}
