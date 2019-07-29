package com.pengheng.manage.controller.system;

import com.pengheng.core.BaseController;
import com.pengheng.domain.SysUser;
import com.pengheng.manage.service.IUserService;
import com.pengheng.model.CriterionVo;
import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoSuccess;
import com.pengheng.service.impl.DynamicSqlService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/user")
@RequiresRoles(logical = Logical.OR, value = {"admin", "admin2"})
public class UserController extends BaseController {

    @Autowired
    private DynamicSqlService dynamicSqlService;
    @Autowired
    private IUserService userService;

    @RequiresPermissions("system:user:add")
    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    //Controller 手动添加事物控制
    @Transactional
    public ResultVo addUser(SysUser sysUser) {
        String id = add(sysUser);
        return new ResultVoSuccess("添加成功");
    }

    private String add(SysUser sysUser) {
        CriterionVo criterionVo = new CriterionVo();
        criterionVo.addNormalResult("login_name", sysUser.getLoginName());
        criterionVo.addNormalResult("user_name", sysUser.getUserName());
        criterionVo.addNormalResult("user_type", sysUser.getUserType());
        criterionVo.addNormalResult("email", sysUser.getEmail());
        criterionVo.addNormalResult("phonenumber", sysUser.getPhonenumber());
        criterionVo.addNormalResult("sex", sysUser.getSex());
        criterionVo.addNormalResult("password", sysUser.getPassword());
        criterionVo.addNormalResult("status", sysUser.getStatus());
        criterionVo.addNormalResult("login_ip", "192.168.1.1");
        criterionVo.addNormalResult("login_date", new Date());
        criterionVo.addNormalResult("create_time", new Date());
        criterionVo.addNormalResult("update_time", new Date());
        criterionVo.addNormalResult("remark", "xxxx");
        return dynamicSqlService.dynamicInsert("sys_user", criterionVo);
    }

    @RequiresPermissions("system:user:update")
    @RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo updateUser(SysUser sysUser) {
        CriterionVo criterionVo = new CriterionVo();
        criterionVo.addCondition("id", sysUser.getId());
        criterionVo.addNormalResult("user_name", sysUser.getUserName());
        int dynamicUpdate = dynamicSqlService.dynamicUpdate("sys_user", criterionVo);
        return new ResultVoSuccess("修改成功");
    }

    @RequiresPermissions("system:user:delete")
    @RequestMapping(value = "/delete/{id}",method = {RequestMethod.GET, RequestMethod.DELETE})
    public ResultVo deleteUser(@PathVariable("id") String id) {
        CriterionVo criterionVo = new CriterionVo();
        criterionVo.addCondition("id", id);
        int dynamicDelete = dynamicSqlService.dynamicDelete("sys_user", criterionVo);
        return new ResultVoSuccess("删除成功");
    }

    @RequiresPermissions("system:user:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo listUser() {
        List<Map<Object, Object>> dynamicSelect = dynamicSqlService.dynamicSelect("sys_user");
        return new ResultVoSuccess("查询成功", dynamicSelect);
    }

    @RequestMapping(value = "/list2", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo listUser2(SysUser sysUser) {
        ResultVo resultVo = userService.getUserList(sysUser);
        return resultVo;
    }
}
