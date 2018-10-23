package com.imooc.smartbulter.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.imooc.smartbulter.R;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

import java.io.File;

/**
 * Created by Administrator on 2018/4/26.
 * 下载(烂尾)
 */

public class UpdateActivity extends BaseActivity {

    //一下
    public static final int HANDLER_LOADING = 10001;

    public static final int HANDLER_OK = 10002;
    //下载失败
    public static final int HANDLER_ON = 10003;
    //

    private TextView tv_size;
    private String url;
    private String path;//文件地址

    //以下
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HANDLER_LOADING:
                    //实时更新进度
                    Bundle bundle=msg.getData();
                    long transferredBytes=bundle.getLong("transferredBytes");
                    long totalSize=bundle.getLong("totalSize");
                    tv_size.setText(transferredBytes+"/"+totalSize);
                    break;
                case HANDLER_OK:
                    tv_size.setText("下载成功");
                    //启动这个应用安装
                    startInstallApk();
                    break;

                case HANDLER_ON:
                    tv_size.setText("下载失败");
                    break;
            }


        }
    };
//以上
    //启动安装
    public void startInstallApk(){
        Intent i=new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setDataAndType(Uri.fromFile(new File(path)),"application/vnd.android.package-archive");
        startActivity(i);
        finish();
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initView();
    }

    private void initView() {
        tv_size=(TextView)findViewById(R.id.tv_size);
        path= FileUtils.getSDCardPath()+"/"+System.currentTimeMillis()+".apk";

        //下载 接收settingActivity传来的url
        url=getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)){
            //下载
            RxVolley.download(path, url, new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    //Log.i("tr")
                    Toast.makeText(UpdateActivity.this, "tr" + transferredBytes, Toast.LENGTH_SHORT).show();
                    //以下
                    Message msg=new Message();
                    msg.what=HANDLER_LOADING;
                    Bundle bundle=new Bundle();
                    bundle.putLong("transferredBytes",transferredBytes);
                    bundle.putLong("totalSize",totalSize);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    //以上

                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    Toast.makeText(UpdateActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(HANDLER_OK);//新加
                }

                @Override
                public void onFailure(VolleyError error) {
                    Toast.makeText(UpdateActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessage(HANDLER_ON);//新加
                }
            });
        }


    }
}
