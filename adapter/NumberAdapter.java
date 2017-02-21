package com.example.administrator.phonesefe.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.base.BaseBaseAdapter;
import com.example.administrator.phonesefe.entity.TeNumber;

/**
 * Created by Administrator on 2016/12/16.
 */

public class NumberAdapter extends BaseBaseAdapter<TeNumber> {
    public NumberAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       Holder holder=null;
        if (view==null){
            view=inflater.inflate(R.layout.adapter_number_item,null);
            holder=new Holder();
            holder.name= (TextView) view.findViewById(R.id.adapter_name_item_tv);
            holder.number= (TextView) view.findViewById(R.id.adapter_number_item_tv);
            view.setTag(holder);
        }else {
            holder= (Holder) view.getTag();
        }

        holder.name.setText(data.get(i).getName());
        holder.number.setText(data.get(i).getNumber());
        return view;
    }
    class Holder{
        TextView name;
        TextView number;

    }

}
