package com.pengheng.manage.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pengheng.manage.entity.SysUser;
import com.pengheng.manage.service.ISysUserService;
import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoFailure;
import com.pengheng.model.ResultVoNotFound;
import com.pengheng.model.ResultVoSuccess;
import com.pengheng.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-08-30
 */
@RestController
@Slf4j
@Api(value = "sys_userCRUD接口")
@RequestMapping("/manage/sys-user")
public class SysUserController {

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "获取sys_user列表", notes = "")
    @ApiImplicitParam(name = "sys_user", value = "sys_user实体", required = false, dataType = "sys_user")
    @GetMapping("/")
    public ResultVo list() {
        Wrapper<SysUser> wrapper = new QueryWrapper<>();
        Collection<SysUser> sysUserList = iSysUserService.list(wrapper);
        ResultVo resultVo = new ResultVoSuccess();
        resultVo.setData(sysUserList);
        return resultVo;
    }


    @ApiOperation(value = "添加sys_user", notes = "新增一条sys_user")
    @ApiImplicitParam(name = "sys_user", value = "sys_user实体", required = true, dataType = "sys_user")
    @PostMapping("/add")
    public ResultVo add(SysUser sysUser) {
        boolean flag = iSysUserService.save(sysUser);
        return flag ? new ResultVoSuccess("添加成功") : new ResultVoFailure("添加失败");
    }

    @ApiOperation(value = "删除sys_user", notes = "根据id删除sys_user")
    @ApiImplicitParam(name = "id", value = "sys_userid", required = true, dataType = "Integer")
    @DeleteMapping("/{id}")
    public ResultVo delete(@PathVariable Integer id) {
        boolean flag = iSysUserService.removeById(id);
        return flag ? new ResultVoSuccess("删除成功") : new ResultVoFailure("删除失败");
    }

    @ApiOperation(value = "修改sys_user", notes = "根据id修改sys_user")
    @ApiImplicitParam(name = "sys_user", value = "sys_user实体", required = true, dataType = "SysUser")
    @PutMapping("/update")
    public ResultVo update(SysUser sysUser) {

        boolean flag = iSysUserService.updateById(sysUser);
        return flag ? new ResultVoSuccess("修改成功") : new ResultVoFailure("修改失败");
    }

    @ApiOperation(value = "查询sys_user", notes = "查询sys_user详细信息")
    @ApiImplicitParam(name = "id", value = "sys_userid", required = true, dataType = "Integer")
    @PostMapping("get/{id}")
    public ResultVo get(@PathVariable Integer id) {

        SysUser sysUser = iSysUserService.getById(id);
        if (sysUser != null) {
            return new ResultVoSuccess("获取对象成功", sysUser);
        } else {
            return new ResultVoNotFound("未找到对应信息");
        }
    }

    @ApiOperation(value = "查询当前登录的用户", notes = "查询当前登录的用户")
    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public ResultVo getRole() {
        SysUser currentUser = sysUserService.requireCurrentUser();
        return new ResultVo("200", "Success", currentUser);
    }

}
