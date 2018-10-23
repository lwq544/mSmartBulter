package com.imooc.smartbulter.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.imooc.smartbulter.R;
import com.imooc.smartbulter.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/27.
 * 关于软件
 */

public class AboutActivity extends BaseActivity{
    private ListView mListView;
    private List<String>mList =new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private void initView() {
        mListView=(ListView)findViewById(R.id.mListView);
        mList.add("应用名:"+getString(R.string.app_name));
        mList.add("版本号:"+ UtilTools.getVersion(this));
        mList.add("官网:wwww.xxx.com");
        mList.add("created by lwq");

        mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mList);
        //设置适配器
        mListView.setAdapter(mAdapter);
    }

}
