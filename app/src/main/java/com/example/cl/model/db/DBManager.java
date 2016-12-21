package com.example.cl.model.db;

import android.content.Context;

import com.example.cl.model.dao.ContactTableDao;
import com.example.cl.model.dao.InviteTableDao;

/**
 * Created by sks on 2016/12/21.
 */
public class DBManager {


    private final DBHelper dbHelper;
    private final ContactTableDao contactTableDao;
    private final InviteTableDao inviteTableDao;

    public DBManager(Context context, String name) {
        dbHelper=new DBHelper(context,name);

        // 创建该数据库中两张表的操作类
        contactTableDao = new ContactTableDao(dbHelper);
        inviteTableDao = new InviteTableDao(dbHelper);
    }


    public ContactTableDao getContactTableDao() {
        return contactTableDao;
    }

    public InviteTableDao getInviteTableDao() {
        return inviteTableDao;
    }

    public void close() {
        dbHelper.close();
    }
}
