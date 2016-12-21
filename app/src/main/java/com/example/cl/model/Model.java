package com.example.cl.model;

import android.content.Context;

import com.example.cl.model.bean.UserInfo;
import com.example.cl.model.dao.UserAccountDao;
import com.example.cl.model.db.DBManager;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sks on 2016/12/21.
 */

public class Model {

 static  Model model=new Model();

    ExecutorService executorService = Executors.newCachedThreadPool();
    private DBManager dbManager;
    private Context mContext;

    public static  Model getInstance(){

        return  model;
    }

    private Model(){

    }

    public void init(Context context){
         mContext=context.getApplicationContext();
        userAccountDao= new UserAccountDao(context.getApplicationContext());

    }

    public ExecutorService getGlobalThreadPool(){
        return  executorService;
    }
    UserAccountDao userAccountDao;
    public UserAccountDao getUserAccountDao(){

        return userAccountDao;
    }

    public void loginSuccess(UserInfo user) {
        // 校验
        if(user == null) {
            return;
        }

        if(dbManager != null) {
            dbManager.close();
        }

        dbManager = new DBManager(mContext, user.getName());
    }
    public DBManager getDbManager(){
        return dbManager;
    }
}
