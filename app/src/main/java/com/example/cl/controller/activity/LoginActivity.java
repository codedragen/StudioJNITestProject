package com.example.cl.controller.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.cl.R;
import com.example.cl.model.Model;
import com.example.cl.model.bean.UserInfo;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by sks on 2016/12/21.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_login;
    private Button btn_regist;
    private TextInputLayout edit_password;
    private TextInputLayout edit_username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login=((Button) findViewById(R.id.login_btn_login));
        btn_regist= (Button) findViewById(R.id.login_btn_regist);
        edit_password= (TextInputLayout) findViewById(R.id.login_password);
        edit_username= (TextInputLayout) findViewById(R.id.login_username);
        btn_login.setOnClickListener(this);
        btn_regist.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.login_btn_login){

            login();

        }else if (R.id.login_btn_regist==v.getId()){
            reginst();

        }
    }

    private void reginst() {
        final String password = edit_password.getEditText().getText().toString();
        final String username = edit_username.getEditText().getText().toString();
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {

                    EMClient.getInstance().createAccount(username,password);
                    Snackbar.make(btn_login,"注册成功",Snackbar.LENGTH_INDEFINITE).show();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Snackbar.make(btn_login,e.getMessage(),Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });


    }

    private void login() {
        String password = edit_password.getEditText().getText().toString();
        final String username = edit_username.getEditText().getText().toString();
        if (TextUtils.isEmpty(password)||TextUtils.isEmpty(username)) {
            Snackbar.make(btn_login,"用户名或密码不能为空",Snackbar.LENGTH_INDEFINITE).show();
            return;
        }

        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                UserInfo user=new UserInfo(username);
                Model.getInstance().loginSuccess(user);
                Model.getInstance().getUserAccountDao().addAccount(user);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 跳转到主页面
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(intent);

                        finish();
                    }
                });

            }

            @Override
            public void onError(int i, String s) {
                Snackbar.make(btn_login,"登录失败，错误码:"+i+",错误信息："+s,Snackbar.LENGTH_INDEFINITE).show();

            }

            @Override
            public void onProgress(int i, String s) {


            }
        });
    }
}
