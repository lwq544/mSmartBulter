package com.imooc.smartbulter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.imooc.smartbulter.R;
import com.imooc.smartbulter.adapter.WeChatAdapter;
import com.imooc.smartbulter.entity.WeChatData;
import com.imooc.smartbulter.ui.WebViewActivity;
import com.imooc.smartbulter.utils.L;
import com.imooc.smartbulter.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 * 微信精选
 */

public class WechatFragment extends Fragment {

    private ListView mListView;
    private List<WeChatData> mList=new ArrayList<>();
    //标题
    private List<String> mListTitle=new ArrayList<>();
    //地址
    private List<String> mListUrl=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_wechat,null);
        findView(view);
        return view;
    }
    //初始化view
    private void findView(View view) {
        mListView=(ListView)view.findViewById(R.id.mListView);

        //解析接口
        String url="http://v.juhe.cn/weixin/query?key="+ StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
               // Toast.makeText(getActivity(),t,Toast.LENGTH_SHORT).show();
                //可以弹出toast
                parsingJson(t);
            }
        });

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.i("position"+position);
               // Toast.makeText(getActivity(), "position"+position, Toast.LENGTH_SHORT).show();
                //此Toast可以显示
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
               intent.putExtra("title",mListTitle.get(position));//键值对
               intent.putExtra("url",mListUrl.get(position));
               startActivity(intent);

            }
        });

    }

    //解析json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonresult=jsonObject.getJSONObject("result");
            JSONArray jsonList=jsonresult.getJSONArray("list");
            for (int i=0;i<jsonList.length();i++){
                JSONObject json=(JSONObject) jsonList.get(i);
                WeChatData data=new WeChatData();

                String titlr=json.getString("title");
                String url=json.getString("url");
                data.setTitle(titlr);
                data.setSource(json.getString("source"));
                data.setImgUrl(json.getString("firstImg"));

                mList.add(data);
                //不光要解析到，还要保存下来
                mListTitle.add(titlr);
                mListUrl.add(url);
            }
            WeChatAdapter adapter=new WeChatAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
