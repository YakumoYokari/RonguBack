package cn.altira.android.rongu.bean;

public class MakeFriend {
    Integer from;
    Integer to;
    String password;

    public MakeFriend(Integer to, Integer from,String password) {
        this.from = from;
        this.to = to;
        this.password = password;
    }

    public MakeFriend() {
    }

    public Integer getTo() {
        return to;
    }

    public Integer getFrom() {
        return from;
    }

    public String getPassword() {
        return password;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
