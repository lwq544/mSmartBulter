package com.imooc.smartbulter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
//import android.webkit.WebViewClient;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.imooc.smartbulter.R;
import com.imooc.smartbulter.utils.L;

/**
 * Created by Administrator on 2018/4/23.
 * 新闻详情
 */

public class WebViewActivity extends BaseActivity{

    //进度
    private ProgressBar mProgressBar;
    //网页
    private WebView mWebView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initview();
    }

    private void initview() {

        mProgressBar=(ProgressBar)findViewById(R.id.mProgressBar);
        mWebView=(WebView)findViewById(R.id.mWebView);

        //接收intent的传值
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        final String url=intent.getStringExtra("url");
        L.i("url"+url);
        //测试toast 可以拿到url
        //Toast.makeText(this, "url"+url, Toast.LENGTH_SHORT).show();

        //设置标题
        getSupportActionBar().setTitle(title);


        //进行加载网页的逻辑
        //支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        //接口回掉
        mWebView.setWebChromeClient(new WebViewClient());
        //加载网页
        mWebView.loadUrl(url);

        //本地显示 失败 WebResourceRequest request
        // urlString
      mWebView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });



    }

   public class WebViewClient extends WebChromeClient{

       @Override
       public void onProgressChanged(WebView view, int newProgress) {
           if (newProgress==100){
               mProgressBar.setVisibility(View.GONE);
           }
           super.onProgressChanged(view, newProgress);
       }
   }

}
