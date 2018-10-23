package com.imooc.smartbulter.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.imooc.smartbulter.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * Created by Administrator on 2018/4/26.
 * 生成二维码
 *
 */

public class QrCodeActivity extends BaseActivity {

    //我的二维码
    private ImageView iv_qr_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        initView();
    }

    private void initView() {
        iv_qr_code=(ImageView)findViewById(R.id.iv_qr_code);
        //获取屏幕的宽
        int width=getResources().getDisplayMetrics().widthPixels;


        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("我是智能管家", width/2, width/2,
                        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher) );
        iv_qr_code.setImageBitmap(qrCodeBitmap);
    }
}
