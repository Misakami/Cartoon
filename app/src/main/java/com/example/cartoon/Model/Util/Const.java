package com.example.cartoon.Model.Util;

import android.content.Context;


import com.example.cartoon.Model.Util.JsoupUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Const {
    public static String Guide_Int_Date = "1";
    public static String Need_to_show = "2";
    public static String INTENT = "3";
    public static String CARTOONNum = "4";
    public static String CARTOON = "5";

    public static int screenWidthPx(Context context)
    {
        int widthPx = context.getResources().getDisplayMetrics().widthPixels;
        int heightPx = context.getResources().getDisplayMetrics().heightPixels;
        return widthPx > heightPx ? heightPx : widthPx;
    }
    public static String Db_Name = "TimFaker.db";
    public static int Db_Version = 1;

    public static String stampToDate(long s){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(s);
        return simpleDateFormat.format(date);
    }

    public static String comefrom(int index){
        if (index == JsoupUtil.TypeManHuaNiu){
            return "来自： 漫画牛";
        }else if (index == JsoupUtil.TypeMankeZhan){
            return  "来自: 漫画栈";
        }else if (index == JsoupUtil.TypeMisaka) {
            return "来自: 某不愿意透露姓名的Misaka";
        }
        return "";
    }
}
