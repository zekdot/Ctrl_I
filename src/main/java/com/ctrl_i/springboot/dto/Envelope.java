package com.ctrl_i.springboot.dto;


import net.sf.json.JSONObject;

public class Envelope {
    private int code;//代码
    private String message;//提示信息
    private Object obj;//携带对象
    public static Envelope dbError=new Envelope(-1,"数据库错误！",null);//数据库错误
    public static Envelope systemError=new Envelope(-2,"系统错误！",null);;//系统错误
        public static Envelope success = new Envelope(0, "success", null);//成功
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getObj() {
        return obj;
    }
    public void setObj(Object obj) {
        this.obj = obj;
    }
    public Envelope(int code,String message,Object obj) {
        // TODO Auto-generated constructor stub
        this.code=code;
        this.message=message;
        this.obj=obj;
    }
    public Envelope() {
        // TODO Auto-generated constructor stub
    }

        public Envelope(Object obj) {
            this.code = 0;
            this.message = "success";
            this.obj = obj;
        }
    @Override
    public String toString(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",this.code);
        jsonObject.put("message",message);
        jsonObject.put("object",this.obj);
        return jsonObject.toString();
    }

}