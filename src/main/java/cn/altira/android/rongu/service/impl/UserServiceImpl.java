package cn.altira.android.rongu.service.impl;

import cn.altira.android.rongu.bean.User;
import cn.altira.android.rongu.bean.UserMessage;
import cn.altira.android.rongu.dao.FriendListDao;
import cn.altira.android.rongu.dao.MakeFriendDao;
import cn.altira.android.rongu.dao.UserDao;
import cn.altira.android.rongu.dao.UserMessageDao;
import cn.altira.android.rongu.service.UserService;
import cn.altira.android.rongu.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private FriendListDao friendListDao;

    private UserDao userDao;

    private UserUtil userUtil;

    private UserMessageDao userMessageDao;

    private MakeFriendDao makeFriendDao;

    @Autowired
    public void setFriendListDao(FriendListDao friendListDao){
        this.friendListDao = friendListDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Autowired
    public void setUserUtil(UserUtil userUtil){
        this.userUtil = userUtil;
    }

    @Autowired
    public void setMakeFriendDao(MakeFriendDao makeFriendDao) {
        this.makeFriendDao = makeFriendDao;
    }

    @Autowired
    public void setUserMessageDao(UserMessageDao userMessageDao) {
        this.userMessageDao = userMessageDao;
    }

    @Override
    public Boolean loginByNumberAndPassword(Integer accountNumber, String passWord) {
        if(accountNumber!=null&&passWord!=null){
            User user = userDao.findUserById(accountNumber);

            if(user!=null){
                if(user.getPassWord().equals(passWord)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Integer registeredByUserNameAndPassword(String username, String password) {

        if(username !=null&&password!=null){
            if(userUtil.isLegalUserName(username)&&userUtil.isLowerTenCharacter(username)){
                if(userUtil.isOnlyCharAndNum(password)&&userUtil.isThanSixCharacter(password)&&userUtil.isLowerSixthCharacter(password)){
                    Integer numb = userDao.registeredByUserNameAndPassword(username,
                            DigestUtils.md5DigestAsHex(password.getBytes())
                            );
                    if(numb != null){
                        friendListDao.add(numb);
                        userMessageDao.build(numb);
                        makeFriendDao.bulid(numb);
                        return numb;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Integer findFriendCountByAccountNumber(Integer id) {
        User user = userDao.findUserById(id);
        if (user!=null){
            return user.getFriendCount();
        }else {
            return null;
        }
    }

    public User findUserById(Integer integer){
        return userDao.findUserById(integer);
    }

    public List<User> findUserByName(String name){
        List<User> users = null;
        users = userDao.findUserByName(name);
        return users;
    }
}
