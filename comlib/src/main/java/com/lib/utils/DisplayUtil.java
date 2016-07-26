package com.lib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

/**
 * Created by shenwenjie on 3/6/2016.
 */

public class DisplayUtil {

    private static final String TAG = "DisplayUtil";

    /**
     * pixel convert to dip
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dip convert to pixel
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * pixel convert to sp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp convert to pixel
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     *
     * @param width 屏幕宽度像素值
     * @param height 屏幕高度像素值
     * @param pinch 屏幕对角线尺寸(5"),英寸inch
     * @return ppi,dpi=ppi/160;
     */
    public static long caculateDensity(int width, int height, int pinch){
        //pow 平方公式,2代表几次方
        //sqrt 平方根公式,次方根需要使用java.lang.StrictMath.pow(m,1.0/n)
        return (long) (Math.sqrt(Math.pow(width,2)+Math.pow(height,2))/pinch);
    }

    //check the device has a entity key,use this to define where the virtual navigationbar is show
    @SuppressLint("NewApi")
    public static boolean checkDeviceHasEntityKey(Context context) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)
        boolean hasMenuKey = ViewConfiguration.get(context)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (hasMenuKey || hasBackKey) {
            return true;
        }
        return false;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
//        Log.e("TransferFrament", "Navi height:" + height);
        return height;
    }


    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG,"statusHeight = " + statusHeight);
        return statusHeight;
    }
}