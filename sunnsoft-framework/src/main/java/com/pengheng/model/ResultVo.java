package com.pengheng.model;

import com.github.pagehelper.PageInfo;
import com.pengheng.util.Toolkits;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class ResultVo implements Serializable {

    private static final long serialVersionUID = -7535228184843182772L;
    private boolean encrypt = false;
    private String code = "";//返回的code
    private String msg = ""; //返回的信息
    private Object data = null;//返回的结果集合
    private Object pageInfo = new HashMap<Object, Object>();//返回的分页集合
    private String timestamp = String.valueOf(System.currentTimeMillis());
    private String signature = "";
    private String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(this.timestamp)));

    public ResultVo() {
    }

    public ResultVo(String code) {
        this(code, "", null, null);
    }

    public ResultVo(String code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    public ResultVo(String code, String msg, Object data, Object pageInfo) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public ResultVo(String paramString1, String paramString2, Object paramObject) {
        this.code = paramString1;
        this.msg = paramString2;
        this.data = paramObject;

        //如果是列表的话 判断是否回传分页信息
        if (paramObject instanceof List) {
            Map<Object, Object> pageMap = Toolkits.getPageMap();
            long total = new PageInfo((List) paramObject).getTotal();

            long pageSize = Long.parseLong(Toolkits.defaultString(pageMap.get("pageSize"), "20"));
            long totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize + 1);

            pageMap.put("total",total);
            pageMap.put("totalPage",totalPage);
            this.pageInfo = pageMap;
        }

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

    public Object getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Object pageInfo) {
        this.pageInfo = pageInfo;
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