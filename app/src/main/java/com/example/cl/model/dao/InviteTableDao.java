package com.example.cl.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cl.model.bean.GroupInfo;
import com.example.cl.model.bean.InvationInfo;
import com.example.cl.model.bean.UserInfo;
import com.example.cl.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sks on 2016/12/21.
 */
public class InviteTableDao {
    private final DBHelper dbhelper;

    public InviteTableDao(DBHelper dbHelper) {
        this.dbhelper=dbHelper;

    }
    // 添加邀请
    public void addInvitation(InvationInfo invitationInfo) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(InviteTable.COL_REASON,invitationInfo.getReason());
        values.put(InviteTable.COL_STATUS,invitationInfo.getStatus().ordinal());
        UserInfo user = invitationInfo.getUser();
        if (user!=null){
            values.put(InviteTable.COL_USER_HXID, invitationInfo.getUser().getHxid());
            values.put(InviteTable.COL_USER_NAME, invitationInfo.getUser().getName());
        } else {// 群组
            values.put(InviteTable.COL_GROUP_HXID, invitationInfo.getGroup().getGroupId());
            values.put(InviteTable.COL_GROUP_NAME, invitationInfo.getGroup().getGroupName());
            values.put(InviteTable.COL_USER_HXID, invitationInfo.getGroup().getInvatePerson());
        }

        db.replace(InviteTable.TAB_NAME,null,values);
        }


    // 获取所有邀请信息
    public List<InvationInfo> getInvitations() {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        List<InvationInfo> list=new ArrayList<>();
        Cursor cursor = db.query(InviteTable.TAB_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            InvationInfo info=new InvationInfo();
            info.setReason(cursor.getString(cursor.getColumnIndex(InviteTable.COL_REASON)));
            info.setStatus(int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InviteTable.COL_STATUS))));
            String groupId = cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID));
            if (groupId==null){
                UserInfo user=new UserInfo();
                user.setHxid(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));
                user.setName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_NAME)));
                user.setNick(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_NAME)));
                info.setUser(user);
            }else{
                GroupInfo groupInfo = new GroupInfo();

                groupInfo.setGroupId(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_HXID)));
                groupInfo.setGroupName(cursor.getString(cursor.getColumnIndex(InviteTable.COL_GROUP_NAME)));
                groupInfo.setInvatePerson(cursor.getString(cursor.getColumnIndex(InviteTable.COL_USER_HXID)));

                info.setGroup(groupInfo);
            }
            list.add(info);


        }

        cursor.close();
        return list;
    }





    // 将int类型状态转换为邀请的状态
    private InvationInfo.InvitationStatus int2InviteStatus(int intStatus) {

        if (intStatus == InvationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InvationInfo.InvitationStatus.INVITE_ACCEPT;
        }

        if (intStatus == InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        }

        if (intStatus == InvationInfo.InvitationStatus.NEW_GROUP_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_GROUP_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.NEW_GROUP_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_INVITE_DECLINED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_ACCEPT_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_ACCEPT_INVITE;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_REJECT_APPLICATION.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_REJECT_APPLICATION;
        }

        if (intStatus == InvationInfo.InvitationStatus.GROUP_REJECT_INVITE.ordinal()) {
            return InvationInfo.InvitationStatus.GROUP_REJECT_INVITE;
        }

        return null;
    }
    // 删除邀请
    public void removeInvitation(String hxId) {
        if (hxId == null) {
            return;
        }

        // 获取数据库链接
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        // 执行删除语句
        db.delete(InviteTable.TAB_NAME, InviteTable.COL_USER_HXID + "=?", new String[]{hxId});
    }

    // 更新邀请状态
    public void updateInvitationStatus(InvationInfo.InvitationStatus invitationStatus, String hxId) {
        if (hxId == null) {
            return;
        }

        // 获取数据库链接
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        // 执行更新操作
        ContentValues values = new ContentValues();
        values.put(InviteTable.COL_STATUS, invitationStatus.ordinal());

        db.update(InviteTable.TAB_NAME, values, InviteTable.COL_USER_HXID + "=?", new String[]{hxId});
    }

}
