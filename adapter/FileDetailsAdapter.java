package com.example.administrator.phonesefe.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.phonesefe.R;
import com.example.administrator.phonesefe.base.BaseBaseAdapter;
import com.example.administrator.phonesefe.entity.Fileinfo;
import com.example.administrator.phonesefe.utils.FileTypeUtil;
import com.example.administrator.phonesefe.utils.TimeUtils;

/**
 * Created by Administrator on 2016/12/29.
 */

public class FileDetailsAdapter extends BaseBaseAdapter<Fileinfo> {
    public FileDetailsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FileHolder fileHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_filedetails_item, null);
            fileHolder = new FileHolder();
            fileHolder.isCheck = (CheckBox) view.findViewById(R.id.adapter_filedetails_ischeck_cb);
            fileHolder.icon = (ImageView) view.findViewById(R.id.adapter_filedetails_icon_iv);
            fileHolder.name = (TextView) view.findViewById(R.id.adapter_filedetails_name_tv);
            fileHolder.day = (TextView) view.findViewById(R.id.adapter_filedetails_day_tv);
            fileHolder.size = (TextView) view.findViewById(R.id.adapter_filedetails_size_tv);
            view.setTag(fileHolder);
        } else {
            fileHolder = (FileHolder) view.getTag();
        }
        //给控件赋值
        final Fileinfo info = getItem(i);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setCheck(!info.isCheck());
            }
        };
        fileHolder.isCheck.setChecked(info.isCheck());
        fileHolder.isCheck.setOnClickListener(listener);
        if (info.getIconName() != null) {
            fileHolder.icon.setImageBitmap(getBitmap(info));
        } else {
            fileHolder.icon.setImageResource(R.mipmap.ic_launcher);
        }
        fileHolder.name.setText(info.getIconName());
        fileHolder.day.setText(TimeUtils.fomartTime(info.getFile().lastModified()));
        fileHolder.size.setText(Formatter.formatFileSize(context, info.getFile().length()));
        return view;
    }

    class FileHolder {
        CheckBox isCheck;
        ImageView icon;
        TextView name;
        TextView day;
        TextView size;
    }
    /**
     * 将文件转换为图片
     */
    private Bitmap getBitmap(Fileinfo info) {
        Bitmap bitmap = null;
        //判断文件类型，如果是图像文件，就用图像作为icon,提前将图像进行压缩
        if (info.getFileType().equals(FileTypeUtil.TYPE_IMAGE)) {
            //如果是图片，则进行图片压缩（图片的二次采样）
            BitmapFactory.Options options = new BitmapFactory.Options();
            //打开边界处理
            options.inJustDecodeBounds = true;
            //将图片缩至option中
            BitmapFactory.decodeFile(info.getFile().getAbsolutePath(), options);
            //获取图片短边的像素
            int scale = options.outWidth > options.outHeight ? options.outHeight : options.outWidth;
            //缩放为原图的三分之一
            options.inSampleSize = scale / 60;
            //关闭边界操作
            options.inJustDecodeBounds = false;
            //返回一个真正的bitmap
            bitmap = BitmapFactory.decodeFile(info.getFile().getAbsolutePath(), options);
        } else {
            //根据图标名字获取资源id
            int res = context.getResources().getIdentifier(info.getIconName(), "drawable", context.getPackageName());
            bitmap = BitmapFactory.decodeResource(context.getResources(), res);
        }
        return bitmap;
    }
}

