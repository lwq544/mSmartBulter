package com.imooc.smartbulter.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2018/3/15.
 * 工具类
 */

public class UtilTools {
    //设置字体
    public static void setFont(Context mContext, TextView textView){
        Typeface fontType=Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    //保存图片到shareUtils
    public static void putImageToShare(Context mContext, ImageView imageView){
        BitmapDrawable drawable=(BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
        //第一步：将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //第二步:利用Base64将我们的字节数组输出流转成String
        byte [] byteArray=byStream.toByteArray();
        String imgString=new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        //第三步:将String保存ShareUtils
        ShareUtils.putString(mContext,"image_title",imgString);
    }

    //读取图片
    public static void getImageToShare(Context mContext,ImageView imageView){

        //1.拿到string
        String imgString=ShareUtils.getString(mContext,"image_title","");
        if (!imgString.equals("")){
            //2.利用Base64将我们的String转换
            byte [] byteArray=Base64.decode(imgString,Base64.DEFAULT);
            ByteArrayInputStream byStream=new ByteArrayInputStream(byteArray);
            //3.生成bitmap
            Bitmap bitmap= BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }

    }

    public static String getVersion(Context mContext){
        PackageManager pm=mContext.getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(mContext.getPackageName(),0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "未知";
        }
    }


}
