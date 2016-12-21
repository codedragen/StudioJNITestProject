package com.example.cl.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cl.model.dao.UserAccountTable;

/**
 * Created by sks on 2016/12/21.
 */
public class UserAccountDB extends SQLiteOpenHelper {
    public UserAccountDB(Context context) {
            super(context, "account.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserAccountTable.CREATE_TAB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
