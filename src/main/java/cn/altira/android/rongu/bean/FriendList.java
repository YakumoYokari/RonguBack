package cn.altira.android.rongu.bean;

import java.util.List;

public class FriendList {
    private Integer accountNumber;
    private List<FriendGroup> groups;

    public FriendList(Integer accountNumber, List<FriendGroup> groups) {
        this.accountNumber = accountNumber;
        this.groups = groups;
    }

    public FriendList() {
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public List<FriendGroup> getGroups() {
        return groups;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setGroups(List<FriendGroup> groups) {
        this.groups = groups;
    }
}
