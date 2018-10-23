package com.imooc.smartbulter.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.imooc.smartbulter.R;
import com.imooc.smartbulter.adapter.CourierAdapter;
import com.imooc.smartbulter.entity.CourierData;
import com.imooc.smartbulter.utils.L;
import com.imooc.smartbulter.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 * 快递查询
 */

public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private  EditText et_number;
    private Button btn_get_courier;
    private ListView mListView;

    private List<CourierData>mList=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }
    //初始化View
    private void initView() {
        et_name=(EditText)findViewById(R.id.et_name);
        et_number=(EditText)findViewById(R.id.et_number);
        btn_get_courier=(Button)findViewById(R.id.btn_get_courier);
        btn_get_courier.setOnClickListener(this);
        mListView=(ListView)findViewById(R.id.mListView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_courier:
                /*
                * 1.获取输入框内容
                * 2.判断是否为空
                * 3.拿到数据去请求数据
                * 4.解析json
                * Listview适配器
                * 6.实体类（item）
                * 7.设置数据/显示效果
                * */

                //1.获取输入框内容
                String name=et_name.getText().toString().trim();
                String number=et_number.getText().toString().trim();

                //拼接我们的url
                String url="http://v.juhe.cn/exp/index?key="+ StaticClass.COURIER_KEY
                        +"&com="+name+"&no="+number;

                //2.判断是否为空
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(number)){
                    //3.拿到数据去请求数据
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(CourierActivity.this,t,Toast.LENGTH_SHORT).show();
                            L.i("Json"+t);
                            //解析Json
                            parsingJson(t);
                        }
                    });


                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
            break;
        }
    }
    //解析数据
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonResult=jsonObject.getJSONObject("result");
            JSONArray jsonArray=jsonResult.getJSONArray("list");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject json=(JSONObject)jsonArray.get(i);
                //实体类塞数据
                CourierData data=new CourierData();
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                data.setDatetime(json.getString("datetime"));
                mList.add(data);
            }
            //倒序处理
            Collections.reverse(mList);
            CourierAdapter adapter=new CourierAdapter(this,mList);
            mListView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
