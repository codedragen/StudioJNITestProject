package com.example.cl.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cl.model.bean.UserInfo;
import com.example.cl.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sks on 2016/12/21.
 */
public class ContactTableDao {
    private final DBHelper dbHelper;

    public ContactTableDao(DBHelper dbHelper) {
        this.dbHelper=dbHelper;
    }

    public List<UserInfo> getContacts(){
        List<UserInfo> list=new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor=db.query(ContactTable.TAB_NAME,null,ContactTable.COL_IS_CONTACT+"=?",new String[]{"1"},null,null,null);
         while (cursor.moveToNext()){
             UserInfo info=new UserInfo();
             info.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHOTO)));
             info.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NICK)));
             info.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
             info.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
             list.add(info);

         }

        cursor.close();
        db.close();
        dbHelper.close();
        return  list;

    }

    public UserInfo getContactByHx(String hxId) {
        if (hxId == null) {
            return null;
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ContactTable.TAB_NAME, null, ContactTable.COL_HXID + "=?", new String[]{hxId}, null, null, null);
        UserInfo info=null;
        if (cursor.moveToNext()){
            info = new UserInfo();
            info.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
            info.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NICK)));
            info.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
            info.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHOTO)));
        }
        cursor.close();

        return info;


    }

    // 通过环信id获取用户联系人信息
    public List<UserInfo> getContactsByHx(List<String> hxIds) {

        if (hxIds == null || hxIds.size() <= 0) {
            return null;
        }

        List<UserInfo> contacts = new ArrayList<>();

        // 遍历hxIds，来查找
        for (String hxid : hxIds) {
            UserInfo contact = getContactByHx(hxid);

            contacts.add(contact);
        }

        // 返回查询的数据
        return contacts;
    }


    // 保存单个联系人
    public void saveContact(UserInfo user, boolean isMyContact) {
        if (user==null)
            return;


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(ContactTable.COL_HXID,user.getHxid());
        values.put(ContactTable.COL_NAME,user.getName());
        values.put(ContactTable.COL_NICK,user.getNick());
        values.put(ContactTable.COL_PHOTO,user.getPhoto());
        values.put(ContactTable.COL_IS_CONTACT,isMyContact?1:0);
        db.insert(ContactTable.TAB_NAME,null,values);
    }
    // 保存联系人信息
    public void saveContacts(List<UserInfo> contacts, boolean isMyContact) {

        if (contacts == null || contacts.size() <= 0) {
            return;
        }

        for (UserInfo contact : contacts) {
            saveContact(contact, isMyContact);
        }
    }

    // 删除联系人信息
    public void deleteContactByHxId(String hxId){

        if(hxId == null) {
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        db.delete(ContactTable.TAB_NAME,ContactTable.COL_HXID+"=?",new String[]{hxId});
    }

}
