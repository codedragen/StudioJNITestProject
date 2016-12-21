package com.example.cl.model.bean;

/**
 * Created by sks on 2016/12/21.
 */
public class GroupInfo {

    private String groupName;   // 群名称
    private String groupId;     // 群id
    private String invatePerson;// 邀请人

    public GroupInfo() {
    }

    public GroupInfo(String groupName,String groupId,String invatePerson) {
        this.invatePerson = invatePerson;
        this.groupId=groupId;
        this.groupName=groupName;
    }

    public String getInvatePerson() {
        return invatePerson;
    }

    public void setInvatePerson(String invatePerson) {
        this.invatePerson = invatePerson;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }



    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    @Override
    public String toString() {
        return "GroupInfo{" +
                "groupName='" + groupName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", invatePerson='" + invatePerson + '\'' +
                '}';
    }


}
