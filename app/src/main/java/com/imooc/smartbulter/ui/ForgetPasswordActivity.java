package com.imooc.smartbulter.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imooc.smartbulter.R;
import com.imooc.smartbulter.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2018/4/17.\
 * 忘记重置密码
 * */

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_forget_password;
    private EditText et_email;
    //修改部分
    private EditText et_now;
    private EditText et_new;
    private EditText et_new_password;
    private Button btn_update_password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();

    }
    //初始化view
    private void initView() {
    btn_forget_password=(Button) findViewById(R.id.btn_gorget_password);
    btn_forget_password.setOnClickListener(this);
    et_email=(EditText)findViewById(R.id.et_email);

    et_now=(EditText) findViewById(R.id.et_now);
    et_new=(EditText)findViewById(R.id.et_new);
    et_new_password=(EditText)findViewById(R.id.et_new_password);
    btn_update_password=(Button)findViewById(R.id.btn_update_password);
    btn_update_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_gorget_password:
                //1获取输入框邮箱
                final String email=et_email.getText().toString().trim();
                //2判断是否为空
                if (!TextUtils.isEmpty(email)){
                //3发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                Toast.makeText(ForgetPasswordActivity.this,
                                        "邮箱已发送至"+email,Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(ForgetPasswordActivity.this,
                                        "邮箱发送失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }

            break;

            case R.id.btn_update_password:
                //1.回去输入框内容
                String now =et_now.getText().toString().trim();
                String news=et_new.getText().toString().trim();
                String new_password=et_new_password.getText().toString();
                //2.判断是否为空
                if (!TextUtils.isEmpty(now)&!TextUtils.isEmpty(news)&!TextUtils.isEmpty(new_password)){
                    //3.判断两次密码是否为一致
                    if (news.equals(new_password)){
                    //4.重置密码
                        MyUser.updateCurrentUserPassword(now, news, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    Toast.makeText(ForgetPasswordActivity.this,
                                            "重置密码成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(ForgetPasswordActivity.this,
                                            "密码重置成功",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
            break;

        }

    }
}
