package com.pengheng.model;

import java.util.Optional;

/**
 * enum 枚举
 * ReplyCode 返回信息类
 * code：编码   txtCode：中文解释
 */
public enum ReplyCode {

    /**
     * 操作成功
     **/
    SUCCESS("200", "操作成功"),

    /**
     * 表单信息验证失败
     **/
    PARAMETER_FAILURE("400", "参数异常"),

    /**
     * 认证信息失败
     **/
    AUTHOR_ERROR("401", "认证信息失败"),

    /**
     * 非法的token
     **/
    ILLEGAL_TOKEN("403", "非法的token"),

    /**
     * 未找到对应信息
     **/
    NOT_FOUND("404", "未找到对应信息"),

    /**
     * 系统异常或操作逻辑不正确
     **/
    ERROR("500", "操作失败");

//  /** 其他客户端登录  **/
//  OTHERLOGIN_FAILURE("50012","其他客户端登录"),


    // 编码
    private String code;

    // 中文解释
    private String txtCode;

    private ReplyCode(String code, String txtCode) {
        this.code = code;
        this.txtCode = txtCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTxtCode() {
        return txtCode;
    }

    public void setTxtCode(String txtCode) {
        this.txtCode = txtCode;
    }

    public static ReplyCode findByName(String name) {
        try {
            return ReplyCode.valueOf(name);
        } catch (IllegalArgumentException e) {
           return null;
        }
    }

    public static String getReplayCodes(String replyCodeName) {
        return Optional.ofNullable(findByName(replyCodeName)).map(ReplyCode::getCode).orElse("-1");
    }

    public static String getReplayTexts(String replyCodeName) {
        return Optional.ofNullable(findByName(replyCodeName)).map(ReplyCode::getTxtCode).orElse("暂未找到传入的字典名称");
    }

//    public static void main(String[] args) {
//
//        //输出某一枚举的值
//        System.out.println(ReplyCode.SUCCESS.getCode());
//        System.out.println(ReplyCode.SUCCESS.getTxtCode());
//
//        //遍历所有的枚举
//        for (ReplyCode replyCode : ReplyCode.values()) {
//            System.out.println(replyCode + "  code: " + replyCode.getCode() + "  TxtCode: " + replyCode.getTxtCode());
//        }
//        System.out.println(ReplyCode.getReplayCodes("SUCCESS"));
//        System.out.println(ReplyCode.getReplayTexts("SUCCESS"));
//    }
}
