package com.pengheng.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengheng.service.IUserService;
import com.pengheng.util.ExcelUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.pengheng.core.BaseController;
import com.pengheng.core.annotation.RedisLock;
import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoFailure;
import com.pengheng.model.ResultVoSuccess;
import com.pengheng.util.Toolkits;

/**
 * 登录
 * @author 彭恒
 *
 */
@RestController
public class LoginController extends BaseController{

	@Autowired
	private IUserService userService;


	/**
	 * 登录方法
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RedisLock
	@RequestMapping("/login")
	public ResultVo login(Model model,HttpServletRequest request) throws Exception {
//
		Map<Object,Object> paramMap = getParameterMap(model);
		
		String username = Toolkits.defaultString(paramMap.get("username"));
		String password = Toolkits.defaultString(paramMap.get("password"));
		boolean rememberme = Boolean.parseBoolean(Toolkits.defaultString(paramMap.get("rememberme")));
		String session_captcha = Toolkits.defaultString(request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY));
		String captcha = request.getParameter("captcha");
//		if (!session_captcha.equals(captcha)) {
//			return new ResultVoFailure("验证码错误");
//		}
		
		//获取subject对象
		Subject subject = SecurityUtils.getSubject();
		//封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberme);
		
		try {
			subject.login(token);//执行Shiro配置的拦截方法
		} catch (UnknownAccountException e) {//登录失败：用户名不存在
			e.printStackTrace();
			return new ResultVoFailure("用户名不存在");
		} catch (IncorrectCredentialsException e) {//登录失败：密码错误
			return new ResultVoFailure("密码错误");
		}
		return new ResultVoSuccess("登录成功");
//		return invokeService;
		
	}

	/**
	 * 登出方法
	 * @return
	 */
	@RequestMapping("/logout")
	public ResultVo logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return new ResultVoSuccess("登出成功");
	}

	/**
	 * shiro过滤未登录跳转提示
	 * @return
	 */
	@RequestMapping("/unlogin")
	public ResultVo unlogin() {
		return new ResultVo("403","用户未登录");
	}

	/**
	 * shiro过滤无权限跳转
	 * @return
	 */
	@RequestMapping("/unauth")
	public ResultVo unauth() {
		return new ResultVoSuccess("403","用户无权限");
	}

	@RequestMapping("/export")
	public void export(HttpServletResponse response,HttpServletRequest request) throws Exception {
		String excelName = "导出数据";
		List<Object> titleList = new ArrayList<>();
		Map<String, String> titleMap = new HashMap<>();
		titleMap.put("field", "id");
		titleMap.put("title", "标题");
		titleList.add(titleMap);
		titleMap = new HashMap<>();
		titleMap.put("field", "name");
		titleMap.put("title", "名字");
		titleList.add(titleMap);
		titleMap = new HashMap<>();
		titleMap.put("field", "age");
		titleMap.put("title", "年龄");
		titleList.add(titleMap);
		titleMap = new HashMap<>();
		titleMap.put("field", "hobby");
		titleMap.put("title", "爱好");
		titleList.add(titleMap);
		titleMap = new HashMap<>();
		titleMap.put("field", "international");
		titleMap.put("title", "国籍");
		titleList.add(titleMap);
		List dataList = new ArrayList<>();

		Map<Object, Object> userMap = new HashMap<>();
		userMap.put("id", "1");
		userMap.put("name", "张三");
		userMap.put("age", "18");
		userMap.put("hobby", "篮球");
		userMap.put("aa", "篮球");
		dataList.add(userMap);
		userMap = new HashMap<>();
		userMap.put("id", "2");
		userMap.put("name", "蔡徐坤");
		userMap.put("age", "18");
		userMap.put("hobby", "rap 篮球");
		userMap.put("aa", "篮球");
		dataList.add(userMap);
		userMap = new HashMap<>();
		userMap.put("id", "3");
		userMap.put("name", "TFBOYS");
		userMap.put("age", "18");
		userMap.put("hobby", "掏粪");
		userMap.put("aa", "篮球");
		dataList.add(userMap);
		ExcelUtil.exportExcel(response,excelName, titleList, dataList);
	}
}
