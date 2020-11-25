package cn.altira.android.rongu.dao;

import cn.altira.android.rongu.bean.FriendGroup;
import cn.altira.android.rongu.bean.FriendList;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FriendListDao {

    private static Map<Integer, FriendList> map;

    static {
        Integer i=10000;
        map = new HashMap<Integer, FriendList>();

        List<FriendGroup> groupList1 = new ArrayList<FriendGroup>();
        FriendGroup friendGroup1 = new FriendGroup();
        friendGroup1.setGroupName("我的朋友");
        friendGroup1.setFriends(new ArrayList<>());
        groupList1.add(friendGroup1);

        List<FriendGroup> groupList2 = new ArrayList<FriendGroup>();
        FriendGroup friendGroup2 = new FriendGroup();
        friendGroup2.setGroupName("我的朋友");
        friendGroup2.setFriends(new ArrayList<>());
        groupList2.add(friendGroup2);

        List<FriendGroup> groupList3 = new ArrayList<FriendGroup>();
        FriendGroup friendGroup3 = new FriendGroup();
        friendGroup3.setGroupName("我的朋友");
        friendGroup3.setFriends(new ArrayList<>());
        groupList3.add(friendGroup3);

        map.put(i,new FriendList(i,groupList1));
        i++;
        map.put(i,new FriendList(i,groupList2));
        i++;
        map.put(i,new FriendList(i,groupList3));
        i++;
    }



    public void add(Integer accountNumber){
        map.put(accountNumber,new FriendList(accountNumber,new ArrayList<FriendGroup>()));
    }

    public FriendList query(Integer accountNumber){
        return map.get(accountNumber);
    }

    public void update(FriendList f){
       map.put(f.getAccountNumber(),f);
    }
}
