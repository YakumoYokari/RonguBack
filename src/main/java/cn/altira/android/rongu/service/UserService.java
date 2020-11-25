package cn.altira.android.rongu.service;

import cn.altira.android.rongu.bean.User;
import cn.altira.android.rongu.util.UserUtil;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public interface UserService {
    public Boolean loginByNumberAndPassword(Integer id,String passWord);
    public  Integer registeredByUserNameAndPassword(String name,String password);
    public Integer findFriendCountByAccountNumber(Integer id);
    public User findUserById(Integer id);
    public List<User> findUserByName(String name);
}
