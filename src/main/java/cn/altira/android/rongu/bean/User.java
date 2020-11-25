package cn.altira.android.rongu.bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class User {
    @JSONField(name = "userName")
    private String userName;

    @JSONField(name = "passWord")
    private String passWord;

    @JSONField(name = "accountNumber")
    private Integer accountNumber;

    @JSONField(name = "friendCount")
    private int friendCount;

    public User(String userName, String passWord, Integer accountNumber, int friendCount) {
        this.userName = userName;
        this.passWord = passWord;
        this.accountNumber = accountNumber;
        this.friendCount = friendCount;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }
}
