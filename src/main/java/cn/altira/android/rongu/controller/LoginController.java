package cn.altira.android.rongu.controller;

import cn.altira.android.rongu.bean.Result;
import cn.altira.android.rongu.bean.User;
import cn.altira.android.rongu.service.FriendListService;
import cn.altira.android.rongu.service.UserService;
import cn.altira.android.rongu.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
public class LoginController {


    private UserService userService;
    private FriendListService friendListService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired
    public void setFriendListService(FriendListService friendListService) {
        this.friendListService = friendListService;
    }


    @RequestMapping("/login")
    public Result login(@RequestParam("accountNumber") Integer accountNumber, @RequestParam("passWord") String passWord) {
        if (userService.loginByNumberAndPassword(accountNumber, passWord)) {
            return ResultUtil.success(userService.findUserById(accountNumber));
        }
        return ResultUtil.error(601,"密码错误");
    }

    @RequestMapping("/findfriend")
    public Result findFriend(@RequestParam("accountNumber") Integer accountNumber, @RequestParam("passWord") String passWord){
        if (userService.loginByNumberAndPassword(accountNumber, passWord)) {
            return ResultUtil.success(friendListService.query(accountNumber));
        }
        return ResultUtil.error(601,"密码错误");
    }

    @RequestMapping("/registered")
    public Result registered(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord){
        Integer numb = userService.registeredByUserNameAndPassword(userName,passWord);
        if(numb !=  null){
            return ResultUtil.success(numb);
        }
        return ResultUtil.error(602,"注册失败");
    }

    @RequestMapping("/search")
    public Result findUserByName(@RequestParam("userName")String userName){
        List<User> users = userService.findUserByName(userName);
        if(userName.matches("[0-9]+")){
            User user = userService.findUserById(Integer.parseInt(userName));
            if(users == null){
                users = new ArrayList<>();
            }
            users.add(user);
        }
        if(users == null){
            return ResultUtil.error(602,"查询失败");
        }
        return ResultUtil.success(users);
    }

    @RequestMapping("/makeFriend")
    public Result makeFriend(@RequestParam("friendId") Integer friendId,@RequestParam("userId") Integer userId){
        return ResultUtil.error(602,"添加失败");
    }

}
