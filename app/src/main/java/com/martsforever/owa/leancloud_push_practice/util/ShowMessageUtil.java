package com.martsforever.owa.leancloud_push_practice.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by owa on 2017/1/13.
 */

public class ShowMessageUtil {
    public static void tosatFast(String msg, Context context){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }
    public static void tosatSlow(String msg, Context context){
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
    }
}
