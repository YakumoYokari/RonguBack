package cn.altira.android.rongu.dao;

import cn.altira.android.rongu.bean.MakeFriend;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class MakeFriendDao {
    static Map<Integer,List<MakeFriend>> map;
    static {
        map = new HashMap<>();
        map.put(10000,new ArrayList<>());
        map.put(10001,new ArrayList<>());
        map.put(10002,new ArrayList<>());
    }

    public void bulid(Integer integer){
        map.put(integer,new ArrayList<>());
    }

    public Integer find(Integer from,Integer to){
        List<MakeFriend> list = map.get(from);
        List<MakeFriend> newlist = new ArrayList<>();
        Integer key = null;
        for (MakeFriend m:
                list) {
            if(m.getTo().equals(to)){
                key = Integer.parseInt(m.getPassword());
            }else {
                newlist.add(m);
            }
        }
        return key;
    }

    public boolean isSend(Integer from,Integer to){
        System.out.println(to);
        List<MakeFriend> list = map.get(from);
        for (MakeFriend makeFriend:
             list) {
            if(makeFriend.getTo().equals(to)){
                System.out.println(makeFriend.getTo());
                return true;
            }
        }
        return false;
    }

    public boolean isGet(Integer from,Integer to){
        List<MakeFriend> list = map.get(to);
        for (MakeFriend makeFriend:
                list) {
            if(makeFriend.getTo().equals(from)){
                return true;
            }
        }
        return false;
    }

    public Integer add(Integer from,Integer to){
        Integer key = new Random().nextInt();
        MakeFriend makeFriend = new MakeFriend(to,from, key.toString());
        List<MakeFriend> list = map.get(from);
        list.add(makeFriend);
        map.put(from,list);
        return key;
    }

    public void delete(Integer from,Integer to){
        List<MakeFriend> list = map.get(from);
        List<MakeFriend> newlist = new ArrayList<>();
        for (MakeFriend m:
             list) {
            if(m.getTo()!=to){
                newlist.add(m);
            }
        }
        map.put(from,newlist);
    }
}
