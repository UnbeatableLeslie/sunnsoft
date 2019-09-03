package com.pengheng.common;

import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoSuccess;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    /**
     * 登出方法
     * @return
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultVoSuccess("登出成功");
    }

    /**
     * shiro过滤未登录跳转提示
     * @return
     */
    @RequestMapping(value = "/unlogin", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo unlogin() {
        return new ResultVo("401","用户未登录");
    }

    /**
     * shiro过滤无权限跳转
     * @return
     */
    @RequestMapping(value = "/unauth", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo unauth() {
        return new ResultVoSuccess("401","用户无权限");
    }
}
