package com.example.cl.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.cl.R;


/**
 * Created by sks on 2016/12/22.
 */
public class MainActivity extends AppCompatActivity{


    private FrameLayout cantianer;
    private BottomNavigationBar navigate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cantianer = (FrameLayout) findViewById(R.id.main_cantianer);
         navigate = (BottomNavigationBar) findViewById(R.id.main_navigate);

        navigate.setBarBackgroundColor(R.color.colorPrimary).addItem(new BottomNavigationItem(R.mipmap.ic_forum_black_24dp,"会话")).setActiveColor("#32CD32").addItem(new BottomNavigationItem(R.mipmap.ic_email_black_24dp,"联系人")).addItem(new BottomNavigationItem(R.mipmap.ic_settings_black_24dp,"设置"));
       ;
        navigate.setMode(BottomNavigationBar.MODE_SHIFTING);
        navigate.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
    }
}
