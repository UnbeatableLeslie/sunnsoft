package com.pengheng.controller.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pengheng.core.BaseController;
import com.pengheng.model.CriterionVo;
import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoSuccess;
import com.pengheng.service.IUserService;
import com.pengheng.service.impl.DynamicSqlService;

@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController {

	@Autowired
	private DynamicSqlService dynamicSqlService;
	@Autowired
	private IUserService userService;
	
	@RequiresPermissions("system:user:add")
	@RequestMapping("/add")
	public ResultVo addUser(Model model,HttpServletRequest request,HttpServletResponse response) {
		Map<Object,Object> paramMap = getParameterMap(model);
		CriterionVo criterionVo = new CriterionVo();
		criterionVo.addNormalResult("login_name",paramMap.get("login_name"));
		criterionVo.addNormalResult("user_name",paramMap.get("user_name"));
		criterionVo.addNormalResult("user_type",paramMap.get("user_type"));
		criterionVo.addNormalResult("email",paramMap.get("email"));
		criterionVo.addNormalResult("phonenumber",paramMap.get("phonenumber"));
		criterionVo.addNormalResult("sex",paramMap.get("sex"));
		criterionVo.addNormalResult("password",paramMap.get("password"));
		criterionVo.addNormalResult("status",paramMap.get("status"));
		criterionVo.addNormalResult("login_ip","192.168.1.1");
		criterionVo.addNormalResult("login_date",new Date());
		criterionVo.addNormalResult("create_time",new Date());
		criterionVo.addNormalResult("update_time",new Date());
		criterionVo.addNormalResult("remark","xxxx");
		String id = dynamicSqlService.dynamicInsert("sys_user", criterionVo);
		System.out.println(id);
		return new ResultVoSuccess("添加成功");
	}

	@RequiresPermissions("system:user:update")
	@RequestMapping("/update")
	public ResultVo updateUser(Model model,HttpServletRequest request,HttpServletResponse response) {
		Map<Object,Object> paramMap = getParameterMap(model);
		CriterionVo criterionVo = new CriterionVo();
		criterionVo.addCondition("id", paramMap.get("id"));
		criterionVo.addNormalResult("user_name",paramMap.get("user_name"));
		int dynamicUpdate = dynamicSqlService.dynamicUpdate("sys_user", criterionVo);
		return new ResultVoSuccess("修改成功");
	}

	@RequiresPermissions("system:user:delete")
	@RequestMapping("/delete")
	public ResultVo deleteUser(Model model,HttpServletRequest request,HttpServletResponse response) {
		Map<Object,Object> paramMap = getParameterMap(model);
		CriterionVo criterionVo = new CriterionVo();
		criterionVo.addCondition("id", paramMap.get("id"));
		int dynamicDelete = dynamicSqlService.dynamicDelete("sys_user", criterionVo);
		return new ResultVoSuccess("删除成功");
	}

	@RequiresPermissions("system:user:list")
	@RequestMapping("/list")
	public ResultVo listUser(Model model,HttpServletRequest request,HttpServletResponse response) {
		List<Map<Object, Object>> dynamicSelect = dynamicSqlService.dynamicSelect("sys_user");
		return new ResultVoSuccess("查询成功",dynamicSelect);
	}
	
	@RequestMapping("/list2")
	public ResultVo listUser2(Model model,HttpServletRequest request,HttpServletResponse response) {
		Map<Object,Object> paramMap = getParameterMap(model);
		List<Map<Object, Object>> userList = userService.getUserList(paramMap);
		return new ResultVoSuccess("查询成功",userList);
	}
}
