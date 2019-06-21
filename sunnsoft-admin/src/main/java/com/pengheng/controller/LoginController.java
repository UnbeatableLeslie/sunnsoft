package com.pengheng.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
}
