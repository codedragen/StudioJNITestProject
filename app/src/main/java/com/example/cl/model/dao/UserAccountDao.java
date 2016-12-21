package com.example.cl.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cl.model.bean.UserInfo;
import com.example.cl.model.db.UserAccountDB;

/**
 * Created by sks on 2016/12/21.
 */
public class UserAccountDao {


    private final UserAccountDB mHelper;

    public UserAccountDao(Context context){
        mHelper = new UserAccountDB(context.getApplicationContext());

    }

    public void addAccount(UserInfo userInfo){

        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(UserAccountTable.COL_HXID,userInfo.getHxid());
        contentValues.put(UserAccountTable.COL_NAME,userInfo.getName());
        contentValues.put(UserAccountTable.COL_NICK,userInfo.getNick());
        contentValues.put(UserAccountTable.COL_PHOTO,userInfo.getPhoto());
        db.replace(UserAccountTable.TAB_NAME,null,contentValues);
        db.close();

    }

    public UserInfo getAccountByHxId(String hxId) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor=db.query(UserAccountTable.TAB_NAME,null,UserAccountTable.COL_HXID+"=?",new String[]{hxId},null,null,null);
        UserInfo user=null;
        if (cursor.moveToNext()){
              user = new UserInfo();
              user.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
              user.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
              user.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICK)));
              user.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));

          }
        cursor.close();
        return  user;
    }

}
