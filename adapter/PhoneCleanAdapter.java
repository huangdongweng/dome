package com.example.administrator.phonesefe.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.base.BaseBaseAdapter;
import com.example.administrator.phonesefe.entity.AppRubbish;
import com.example.administrator.phonesefe.entity.Rubbishinfo;
import com.example.administrator.phonesefe.utils.FileTypeUtil;
/**
 * Created by Administrator on 2016/12/23.
 */
public class PhoneCleanAdapter extends BaseBaseAdapter<AppRubbish> {
    public PhoneCleanAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder holder=null;
        if (view==null){
            view=inflater.inflate(R.layout.adapter_clean_item,null);
            holder=new MyHolder();
            holder.isceck= (CheckBox) view.findViewById(R.id.adapter_clean_item_ischeck_cb);
            holder.icon= (ImageView) view.findViewById(R.id.adapter_clean_item_icon_iv);
            holder.name= (TextView) view.findViewById(R.id.adapter_clean_item_Name_tv);
            holder.size= (TextView) view.findViewById(R.id.adapter_clean_item_size_tv);
            view.setTag(holder);
        }else {
            holder= (MyHolder) view.getTag();
        }
        //给控件赋值
        //获得AppRubbish对象
        final AppRubbish info=getItem(i);
        //是否选中
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setCheck(!info.isCheck());
            }
        };
        holder.size.setText(info.fileSize());
        holder.name.setText(info.getEnglishName());
        if (info.getIcon()!=null){
            holder.icon.setImageDrawable(info.getIcon());
        }else {
            holder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        holder.isceck.setChecked(info.isCheck());
        holder.isceck.setOnClickListener(listener);
        return view;
    }
    /**将文件转换为图片*/
    private Bitmap getBitmap(Rubbishinfo info){
        Bitmap bitmap=null;
        if(info.getFileType().equals(FileTypeUtil.TYPE_IMAGE)){
            //如果是图片，则进行图片压缩（图片的二次采样）
            BitmapFactory.Options options=new BitmapFactory.Options();
            //打开边界处理
            options.inJustDecodeBounds=true;
            //将图片缩至option中
            BitmapFactory.decodeFile(info.getFile().getAbsolutePath(),options);
            //获取图片短边的像素
            int scale =options.outWidth>options.outHeight?options.outHeight:options.outWidth;
            //缩放为原图的三分之一
            options.inSampleSize=scale/60;
            //关闭边界操作
            options.inJustDecodeBounds=false;
            //返回一个真正的bitmap
            bitmap=BitmapFactory.decodeFile(info.getFile().getAbsolutePath(),options);
        }else {
            //根据图标名字获取资源id
            int res =context.getResources().getIdentifier(info.getIconName(),"drawable",context.getPackageName());
            bitmap =BitmapFactory.decodeResource(context.getResources(),res);
        }
        return bitmap;
    }
    class MyHolder{
        CheckBox isceck;
        ImageView icon;
        TextView name;
        TextView size;
    }
}
