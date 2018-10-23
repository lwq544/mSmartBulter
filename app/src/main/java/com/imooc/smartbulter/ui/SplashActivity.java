package com.imooc.smartbulter.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.imooc.smartbulter.MainActivity;
import com.imooc.smartbulter.R;
import com.imooc.smartbulter.utils.ShareUtils;
import com.imooc.smartbulter.utils.StaticClass;
import com.imooc.smartbulter.utils.UtilTools;

/**
 * Created by Administrator on 2018/4/13.
 * 闪屏页
 */

public class SplashActivity extends AppCompatActivity{

    /*
    * 1.延时200ms
    * 2.判断程序是否第一次运行
    * 3.自定义字体
    * 4.Activity全屏主题
    * */

    private TextView tv_splash;

    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否第一次运行
                    if (isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    //初始化view
    private void initView(){
        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);

        tv_splash=(TextView) findViewById(R.id.tv_splash);
        //设置字体
        //Typeface fontType=Typeface.createFromAsset(getAssets(),"fonts/FONT.TTF");
        //tv_splash.setTypeface(fontType);
        UtilTools.setFont(this,tv_splash);
    }

    //判断程序是否第一次运行
    private boolean isFirst(){
        boolean isFirst= ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){
            //标记已经启动过app
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            //是第一次运行
            return true;
        }else{
            return false;
        }
    }
    //禁止返回键
    @Override
    public void onBackPressed() {
        //
    }
}
