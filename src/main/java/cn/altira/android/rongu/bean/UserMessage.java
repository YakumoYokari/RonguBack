package cn.altira.android.rongu.bean;

public class UserMessage {
    Integer to;
    Integer type; //1.makefriend 2.chat 3.deletefriend 4.agree 5.refuse
    Integer from;
    String msg;

    public UserMessage() {
    }

    public UserMessage(Integer to, Integer type, Integer from, String msg) {
        this.to = to;
        this.type = type;
        this.from = from;
        this.msg = msg;
    }

    public Integer getTo() {
        return to;
    }

    public Integer getType() {
        return type;
    }

    public Integer getFrom() {
        return from;
    }

    public String getMsg() {
        return msg;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
