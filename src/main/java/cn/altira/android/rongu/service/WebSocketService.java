package cn.altira.android.rongu.service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import cn.altira.android.rongu.bean.User;
import cn.altira.android.rongu.bean.UserMessage;
import cn.altira.android.rongu.dao.FriendRequestDao;
import cn.altira.android.rongu.dao.MakeFriendDao;
import cn.altira.android.rongu.dao.UserDao;
import cn.altira.android.rongu.dao.UserMessageDao;
import cn.altira.android.rongu.service.impl.FriendListServiceImpl;
import cn.altira.android.rongu.service.impl.UserServiceImpl;
import cn.altira.android.rongu.util.ResultUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@ServerEndpoint(value = "/websocket/{accountNumber}/{passWord}")
@Component
public class WebSocketService {

    private static UserDao userDao = new UserDao();
    private static UserMessageDao userMessageDao = new UserMessageDao();
    private static MakeFriendDao makeFriendDao = new MakeFriendDao();
    private static FriendListService friendListService = new FriendListServiceImpl();
    private static FriendRequestDao friendRequestDao = new FriendRequestDao();

    private static ConcurrentHashMap<Integer, Session> sessionPools = new ConcurrentHashMap<>();

    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
//                System.out.println("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("accountNumber") Integer accountNumber,@PathParam("passWord") String passWord){
        if(accountNumber != null && passWord != null){
            if(loginByNumberAndPassword(accountNumber,passWord)){
                sessionPools.put(accountNumber, session);
                System.out.println(session);
                try {
                    sendMessage(session, JSON.toJSONString(ResultUtil.success(205,userMessageDao.get(accountNumber))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    sendMessage(session, JSON.toJSONString(ResultUtil.error(601,"密码错误")));
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam("accountNumber") Integer accountNumber){
        if(accountNumber != null){
            if(sessionPools.get(accountNumber)!=null){
                sessionPools.remove(accountNumber);
            }
        }
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message, @PathParam("accountNumber") Integer accountNumber) throws IOException{
        System.out.println(message);
        Session session = sessionPools.get(accountNumber);
        JSONObject jsonObject = (JSONObject) JSONObject.parse(message);
        Integer code = jsonObject.getInteger("code");
        if(code == 225){
            sendMessage(session,JSON.toJSONString(ResultUtil.success(205,userMessageDao.get(accountNumber))));
            sendMessage(session,JSON.toJSONString(ResultUtil.success(205,friendRequestDao.get(accountNumber))));
        }else if(code == 221){
            userMessageDao.clear(accountNumber);
        }else if(code == 223){
            UserMessage userMessage = jsonObject.getObject("data",UserMessage.class);
            if(userMessage.getType() == 2){
                Session tosession = sessionPools.get(userMessage.getTo());
                if(tosession != null){
                    sendMessage(tosession,JSON.toJSONString(ResultUtil.success(205,userMessage)));
                    System.out.println("在线");
                }else {
                    userMessageDao.put(userMessage);
                    System.out.println("不在线");
                }
            }else if(userMessage.getType()==1) {
                if(!friendListService.isFriend(userMessage.getFrom(),userMessage.getTo())){
                    if(!makeFriendDao.isSend(userMessage.getFrom(),userMessage.getTo())){
                        if(!makeFriendDao.isGet(userMessage.getFrom(),userMessage.getTo())){
                            Integer key = makeFriendDao.add(userMessage.getFrom(),userMessage.getTo());
                            userMessage.setMsg(key.toString());
                            friendRequestDao.put(userMessage);
                        }else {
                            friendListService.add(userMessage.getFrom(),userMessage.getTo(),"我的朋友","我的朋友");
                            friendRequestDao.delete(userMessage.getTo(), userMessage.getFrom());
                        }

                    }else {
                        System.out.println("发过好友请求了");
                    }
                }
            }else if(userMessage.getType()==4){
                if(!friendListService.isFriend(userMessage.getFrom(),userMessage.getTo())) {
                    Integer key = makeFriendDao.find(userMessage.getFrom(), userMessage.getTo());
                    if(key != null){
                        if (key.equals(Integer.parseInt(userMessage.getMsg()))) {
                            friendListService.add(userMessage.getFrom(), userMessage.getTo(), "我的朋友", "我的朋友");
                            friendRequestDao.delete(userMessage.getFrom(), userMessage.getTo());
                        }
                    }

                }
            }else if(userMessage.getType()==5){
                Integer key = makeFriendDao.find(userMessage.getFrom(),userMessage.getTo());
                if(key != null){
                    if(key.equals(Integer.parseInt(userMessage.getMsg()))){
                        makeFriendDao.delete(userMessage.getFrom(),userMessage.getTo());
                        friendRequestDao.delete(userMessage.getFrom(), userMessage.getTo());
                    }
                }

            }
        }
        try {
            sendMessage(session, message);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }


}
