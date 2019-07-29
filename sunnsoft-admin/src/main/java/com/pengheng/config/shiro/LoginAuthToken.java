package com.pengheng.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Remark 处理登录请求的参数
 * @Author pengheng
 * @Date 2019年7月26日14:42:20
 */
public class LoginAuthToken extends UsernamePasswordToken {
    private String userType;

    public LoginAuthToken(String username,String password,boolean rememberMe,String userType) {
        super(username,password,rememberMe);
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
