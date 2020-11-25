package cn.altira.android.rongu.dao;

import cn.altira.android.rongu.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class UserDao {
    @Autowired
    FriendListDao friendListDao;

    private static Map<Integer, User> map;
    private static Integer i = 10000;
    static {
        map = new HashMap<Integer, User>();

        map.put(i,new User("李昊", DigestUtils.md5DigestAsHex("123456".getBytes()),i,0));
        i++;
        map.put(i,new User("薛汇涛",DigestUtils.md5DigestAsHex("123456".getBytes()),i,0));
        i++;
        map.put(i,new User("郑炜焜",DigestUtils.md5DigestAsHex("123456".getBytes()),i,0));
        i++;
    }

    public User findUserById(Integer id){
        return map.get(id);
    }

    public  Integer registeredByUserNameAndPassword(String name,String password){
        map.put(i,new User(name,password,i,0));
        return i++;
    }

    public List<User> findUserByName(String name){
        User user = null;
        List<User> users = null;
        Iterator iterator = map.values().iterator();
        while (iterator.hasNext()){
            user =(User) iterator.next();
            if(user.getUserName().equals(name)||user.getAccountNumber().equals(name)){
                user.setPassWord(null);
                user.setFriendCount(0);
                if(users == null){
                    users = new ArrayList<User>();
                }
                users.add(user);
            }
        }
        return users;
    }

}
