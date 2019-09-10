package com.pengheng.common;

import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoSuccess;
import com.pengheng.service.StorageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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


    /**
     * 可设置调用权限, 防止用户恶意上传文件
     *
     * @param multipartFile 文件流对象
     */
    @ApiOperation(value = "文件上传", notes = "文件上传接口", produces = "application/json", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParam(name = "file", value = "文件对象", required = true, dataType = "file", paramType = "form")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResultVo uploadFile(@RequestParam(name = "file") MultipartFile multipartFile) {
        String filePath = storageService.transferTo(multipartFile);
        return new ResultVo("200", "成功", Collections.singletonMap("path", filePath));
    }
}
