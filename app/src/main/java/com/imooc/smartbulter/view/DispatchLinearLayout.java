package com.imooc.smartbulter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/4/25.
 * 事件分发
 */

public class DispatchLinearLayout extends LinearLayout{

    private DispatchKeyEventListener dispatchKeyEventListener;

    public DispatchLinearLayout(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);


    }

    public DispatchKeyEventListener getDispatchKeyEventListener() {
        return dispatchKeyEventListener;
    }

    public void setDispatchKeyEventListener(DispatchKeyEventListener dispatchKeyEventListener) {
        this.dispatchKeyEventListener = dispatchKeyEventListener;
    }

    //接口
    public static interface DispatchKeyEventListener{
        boolean dispatchKeyEvent(KeyEvent event);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //如果不为空，说明调用 去获取事件
        if (dispatchKeyEventListener!=null){
            return dispatchKeyEventListener.dispatchKeyEvent(event);

        }
        return super.dispatchKeyEvent(event);
    }
}
