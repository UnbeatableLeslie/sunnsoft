package com.pengheng.model;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ResultVo implements Serializable {

    private static final long serialVersionUID = -7535228184843182772L;
    private boolean encrypt = false;
    private String code  = "";//返回的code
    private String msg  = ""; //返回的信息
    private Object data  = null;//返回的结果集合
    private Object info  = new HashMap();//返回的分页集合
    private String timestamp = String.valueOf(System.currentTimeMillis());
    private String signature = "";
    private String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(this.timestamp)));

    public ResultVo()
    {
    }

    public ResultVo(String paramString){
        this(paramString, "", null, null);
    }

    public ResultVo(String paramString1, String paramString2)
    {
        this.code = paramString1;
        this.msg = paramString2;

    }

    public ResultVo(String paramString1, String paramString2, Object paramObject1, Object paramObject2)
    {
        this.code = paramString1;
        this.msg = paramString2;
        this.data = paramObject1;
        this.info = paramObject2;
    }

    public ResultVo(String paramString1, String paramString2, Object paramObject)
    {
        this.code = paramString1;
        this.msg = paramString2;
        this.data = paramObject;
    }



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCreateTime() {
        return this.createTime;
    }

}