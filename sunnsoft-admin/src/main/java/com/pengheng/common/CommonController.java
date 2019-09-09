package com.pengheng.common;

import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoSuccess;
import com.pengheng.service.StorageService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@RestController
public class CommonController {


    @Autowired
    private StorageService storageService;

    /**
     * 登出方法
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultVoSuccess("登出成功");
    }

    /**
     * shiro过滤未登录跳转提示
     */
    @RequestMapping(value = "/unlogin", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo unlogin() {
        return new ResultVo("401", "用户未登录");
    }

    /**
     * shiro过滤无权限跳转
     */
    @RequestMapping(value = "/unauth", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo unauth() {
        return new ResultVoSuccess("401", "用户无权限");
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResultVo uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String filePath = storageService.transferTo(multipartFile);
        return new ResultVo("200", "成功", Collections.singletonMap("path", filePath));
    }
}
