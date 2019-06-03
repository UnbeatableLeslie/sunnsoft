package com.pengheng.model;

/**
 * enum 枚举
 * ReplyCode 返回信息类
 * code：编码   txtCode：中文解释
 * dubo 2018年4月11日12:44:34
 */
public enum ReplyCode {

    /** 操作成功  **/
    ALL_SUCCESS("200", "操作成功"),

    /** 非法的token  **/
    ILLEGAL_FAILURE("403","非法的token"),

    /** 参数异常  **/
    PARAMETER_FAILURE("400","参数异常"),

    /** 账号或密码错误 **/
    ALL_FAILURE("500", "操作失败");

//  /** 其他客户端登录  **/
//  OTHERLOGIN_FAILURE("50012","其他客户端登录"),



    private String code;// 编码
    private String txtCode;// 中文解释

    private ReplyCode(String code,String txtCode){
        this.code=code;
        this.txtCode=txtCode;
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

    public static String getReplayCodes(String arg){
        for( ReplyCode replyCode : ReplyCode.values()){
            if(arg.equals(replyCode.toString())){
                arg=replyCode.getCode();
                break;
            }else{
                arg="-1";
            }
        }
        return arg;
    }

    public static String  getReplayTexts(String arg){
        for( ReplyCode replyCode : ReplyCode.values()){
            if(arg.equals(replyCode.toString())){
                arg=replyCode.getTxtCode();
                break;
            }else{
                arg="暂未找到传入的字典名称";
            }
        }
        return arg;
    }

//    public static void main(String[] args) {
//
//        //输出某一枚举的值
//        System.out.println( ReplyCode.ALL_SUCCESS.getCode() );
//        System.out.println( ReplyCode.ALL_SUCCESS.getTxtCode() );
//
//        //遍历所有的枚举
//        for( ReplyCode replyCode : ReplyCode.values()){
//            System.out.println( replyCode + "  code: " + replyCode.getCode() + "  TxtCode: " + replyCode.getTxtCode() );
//        }
//        System.out.println(ReplyCode.getReplayCodes("ALL_SUCCESS"));
//        System.out.println(ReplyCode.getReplayTexts("ALL_SUCCESS"));
//    }
}
