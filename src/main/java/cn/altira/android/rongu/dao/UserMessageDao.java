package cn.altira.android.rongu.dao;

import cn.altira.android.rongu.bean.UserMessage;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserMessageDao {
    private static Map<Integer, List<UserMessage>> map;

    static {
        map = new HashMap<>();

        map.put(10000,new ArrayList<>());
        map.put(10001,new ArrayList<>());
        map.put(10002,new ArrayList<>());

    }

    public void build(Integer integer){
        map.put(integer,new ArrayList<>());
    }

    public Boolean put(UserMessage userMessage){
        List<UserMessage> list = map.get(userMessage.getTo());
        if(list != null){
            list.add(userMessage);
            map.put(userMessage.getTo(),list);
            return true;
        }else {
            return false;
        }
    }

    public List<UserMessage> get(Integer integer) {
        return map.get(integer);
    }

    public void clear(Integer integer){
        map.put(integer,new ArrayList<>());
    }
}
