package cn.altira.android.rongu.service.impl;

import cn.altira.android.rongu.bean.FriendGroup;
import cn.altira.android.rongu.bean.FriendList;
import cn.altira.android.rongu.bean.User;
import cn.altira.android.rongu.dao.FriendListDao;
import cn.altira.android.rongu.dao.UserDao;
import cn.altira.android.rongu.service.FriendListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("FriendListService")
public class FriendListServiceImpl implements FriendListService {

    UserDao userDao;
    FriendListDao friendListDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setFriendListDao(FriendListDao friendListDao) {
        this.friendListDao = friendListDao;
    }

    @Override
    public Boolean add(Integer number1,Integer number2,String groupName1,String groupName2) {
        Boolean isSuccess = false;

        if(friendListDao == null){
            friendListDao = new FriendListDao();
        }
        if(userDao == null){
            userDao = new UserDao();
        }

        FriendList friendList1 = friendListDao.query(number1);
        List<FriendGroup> friendGroups1 = friendList1.getGroups();
        List<FriendGroup> newFriendGroups1 = new ArrayList<>();
        System.out.println(number1);
        for (FriendGroup friendGroup1:
             friendGroups1) {

            System.out.println(friendGroup1.getGroupName());
            for (User f1://
                    friendGroup1.getFriends()) {
                System.out.println(f1.getUserName());
            }

            if(friendGroup1.getGroupName().equals(groupName1)){
                friendGroup1.getFriends().add(userDao.findUserById(number2));
            }
            newFriendGroups1.add(friendGroup1);
        }
        friendList1.setGroups(newFriendGroups1);
        friendListDao.update(friendList1);

        FriendList friendList2 = friendListDao.query(number2);
        List<FriendGroup> friendGroups2 = friendList2.getGroups();
        List<FriendGroup> newFriendGroups2 = new ArrayList<>();
        System.out.println(number2);
        for (FriendGroup friendGroup2:
                friendGroups2) {

            System.out.println(friendGroup2.getGroupName());
            for (User f2://
                 friendGroup2.getFriends()) {
                System.out.println(f2.getUserName());
            }

            if(friendGroup2.getGroupName().equals(groupName2)){
                friendGroup2.getFriends().add(userDao.findUserById(number1));
                isSuccess = true;
            }
            newFriendGroups2.add(friendGroup2);
        }
        friendList2.setGroups(newFriendGroups2);
        friendListDao.update(friendList2);



        return isSuccess;
    }

    public boolean isFriend(Integer a,Integer b){
        if(friendListDao == null){
            friendListDao = new FriendListDao();
        }
        FriendList friendListA = friendListDao.query(a);

        for (FriendGroup friendGroupA:
             friendListA.getGroups()) {
            for (User friend:
                 friendGroupA.getFriends()) {
                if(friend.getAccountNumber().equals(b)){
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public FriendList query(Integer accountNumber) {
        return friendListDao.query(accountNumber);
    }

    @Override
    public void update(FriendList f) {

    }
}
