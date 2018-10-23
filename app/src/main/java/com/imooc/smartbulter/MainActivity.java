package com.imooc.smartbulter;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;

import com.imooc.smartbulter.fragment.BulterFragment;
import com.imooc.smartbulter.fragment.GirlFragment;
import com.imooc.smartbulter.fragment.UserFragment;
import com.imooc.smartbulter.fragment.WechatFragment;
import com.imooc.smartbulter.ui.SettingActivity;
import com.imooc.smartbulter.utils.L;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //TableLayout
    private TabLayout mTabLayout;
    //Viewpager
    private ViewPager mViewPager;
    //title
    private List<String> mTitle;
    //Fragment
    private List<Fragment>mFragment;//必须是V4
    //悬浮窗
    private FloatingActionButton fab_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //去掉阴影 顶框跟标题
        getSupportActionBar().setElevation(0);
        //调用以下两个方法
        initDate();
        initView();


       // L.d("Test");
        //L.i("Test");
        //L.e("Test");
        //L.w("Test");
    }
    //初始化数据
    private  void initDate(){
        mTitle=new ArrayList<>();//Array List是依次添加的
        mTitle.add("服务管家");
        mTitle.add("微信精选");
       // mTitle.add("趣味相册");
        mTitle.add("个人中心");

        mFragment=new ArrayList<>();
        mFragment.add(new BulterFragment());//顺序要与以上一一对应
        mFragment.add(new WechatFragment());
       // mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }
    //初始化view
    private void initView(){
        fab_setting=(FloatingActionButton) findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);
        //默认隐藏
        fab_setting.setVisibility(View.GONE);
        mTabLayout=(TabLayout) findViewById(R.id.mTabLayout);
        mViewPager=(ViewPager) findViewById(R.id.mViewPager);

        //预加载fragment
        mViewPager.setOffscreenPageLimit(mFragment.size());
        //viewpager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG","position:"+position);
                if(position==0){
                    fab_setting.setVisibility(View.GONE);
                }else {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            //返回item的个数
            public int getCount() {
                return mFragment.size();
            }
            //设置标题

            @Override
                 public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;

        }


    }

}
