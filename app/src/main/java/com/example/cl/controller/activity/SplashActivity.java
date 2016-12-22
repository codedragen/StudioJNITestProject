package com.example.cl.controller.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cl.R;
import com.example.cl.model.Model;
import com.example.cl.model.bean.UserInfo;

import com.hyphenate.chat.EMClient;


public class SplashActivity extends AppCompatActivity {


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (isFinishing()){
                return;
            }

            toMainOrLogin();
        }
    };

    private void toMainOrLogin() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if (EMClient.getInstance().isLoggedInBefore()){
                    UserInfo user = Model.getInstance().getUserAccountDao().getAccountByHxId(EMClient.getInstance().getCurrentUser());
                   if (user==null){
                       login();
                   }else{
                       Model.getInstance().loginSuccess(user);
                       Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                       startActivity(intent);
                   }
                }else{
                    login();
                }
            }
        });

    }

    private void login() {
        Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(0,2000);
    }


    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();

    }
}
