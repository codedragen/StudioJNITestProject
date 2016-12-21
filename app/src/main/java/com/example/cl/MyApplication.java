package com.example.cl;

import android.app.Application;

import com.example.cl.model.Model;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by sks on 2016/12/21.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EMOptions options=new EMOptions();
        options.setAcceptInvitationAlways(false);
        options.setAutoAcceptGroupInvitation(false);
        EaseUI.getInstance().init(this,options);
        Model.getInstance().init(this);
    }
}
