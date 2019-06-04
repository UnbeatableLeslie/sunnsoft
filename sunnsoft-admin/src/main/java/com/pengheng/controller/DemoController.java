package com.pengheng.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.pengheng.core.BaseController;
import com.pengheng.model.CriterionVo;
import com.pengheng.service.IDynamicSqlService;
import com.pengheng.util.Toolkits;

@RestController
public class DemoController extends BaseController {

	@Autowired
	private IDynamicSqlService dynamicSqlService;


	@RequestMapping("/demo/{id}")
	public Map<Object, Object> getDepartment(@PathVariable("id") Integer id) {

		CriterionVo criterionVo = new CriterionVo();
		criterionVo.addCondition("balance_id", id);
		Map<Object, Object> map = dynamicSqlService.dynamicSelectUnique("infra_balance_tbl", criterionVo);
		return map;
	}

	@RequestMapping("/demo/add")
	public void addTest() {
		try {
			CriterionVo criterionVo = new CriterionVo();
			criterionVo.addNormalResult("username", "test"+new Random().nextInt());
			criterionVo.addNormalResult("password",new Random().nextInt());
			criterionVo.addNormalResult("createtime",new Date());
			criterionVo.addNormalResult("updatetime",new Date());
			dynamicSqlService.dynamicInsert("tb_user", criterionVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/demo")
	public List getAll() {
		CriterionVo criterionVo = new CriterionVo();
		List list = null;
		list = dynamicSqlService.dynamicSelect("infra_balance_tbl", criterionVo);
		return list;
	}

	@RequestMapping("/demo/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		CriterionVo criterionVo = new CriterionVo();
		criterionVo.addCondition("balance_id", id);
		dynamicSqlService.dynamicDelete("infra_balance_tbl", criterionVo);
	}

	@RequestMapping("/demo/list")
	public Object list(Model model) {
		Map<Object, Object> parameterMap = getParameterMap(model);
		System.out.println(Toolkits.toJson(parameterMap));
		PageHelper.startPage(7, 5);
		List list = dynamicSqlService.dynamicSelect("tb_user");
		return list;

	}

	@RequestMapping("/demo/error")
	public Object error(Model model) {
		return "无登录无法访问";
	}

	@RequestMapping("/demo/unauth")
	public Object unauth(Model model) {
		return "未授权";
	}
	
	@RequestMapping("/test-RMBM")
	public Object rememberMe() {
		return "jizhuwo";
	}
}
