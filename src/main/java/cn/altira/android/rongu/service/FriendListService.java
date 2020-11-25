package cn.altira.android.rongu.service;

import cn.altira.android.rongu.bean.FriendList;
import org.springframework.web.bind.annotation.RestController;


public interface FriendListService {
    public Boolean add(Integer number1,Integer number2,String groupName1,String groupName2);
    public FriendList query(Integer accountNumber);
    public boolean isFriend(Integer a,Integer b);
    public void update(FriendList f);
}
