package com.example.administrator.phonesefe.biz;

import android.app.ActivityManager;
import android.content.Context;

import com.example.administrator.phonesefe.ui.HomeActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

public class ProcessManager {
    /**
     * 获取第三方正在运行的进程
     *
     * @param context
     * @return
     */
    //获得运转着的进程
    public static List<ActivityManager.RunningAppProcessInfo> getRunningProcess(Context context) {
        //获取ActivityManager                         //获得系统服务
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取所有的进程
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        return list;
    }
    /**
     * 杀死服务进程以下的进程
     */
    public static void killRunningProcess(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo info : getRunningProcess(context)) {
            am.killBackgroundProcesses(info.processName);
        }
    }

}
