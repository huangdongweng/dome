package com.example.administrator.phonesefe.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.base.BaseBaseAdapter;
import com.example.administrator.phonesefe.entity.ClassListinfo;

/**
 * Created by Administrator on 2016/12/16.
 */

public class ClassListAdapter extends BaseBaseAdapter<ClassListinfo> {

    public ClassListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder=null;
        if (view==null){
            view=inflater.inflate(R.layout.adapter_classlist_item,null);
            holder=new Holder();
            holder.tv= (TextView) view.findViewById(R.id.adapter_class_item_class_tv);
            view.setTag(holder);
        }else {
            holder= (Holder) view.getTag();
        }
        holder.tv.setText(data.get(i).getName());
        return view;
    }
    class Holder{
        TextView tv;
    }
}
