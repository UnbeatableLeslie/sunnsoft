package com.pengheng.controller.manage;

import com.google.code.kaptcha.Constants;
import com.pengheng.config.shiro.LoginAuthToken;
import com.pengheng.core.annotation.RedisLock;
import com.pengheng.core.exception.Assert;
import com.pengheng.dao.entity.SysUser;
import com.pengheng.model.ReplyCode;
import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoFailure;
import com.pengheng.model.ResultVoSuccess;
import com.pengheng.util.ExcelUtil;
import com.pengheng.util.Toolkits;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录
 * @author 彭恒
 *
 */
@RestController
public class LoginController {


	/**
	 * 登录方法
	 */
	@RedisLock
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public ResultVo login(SysUser sysUser, boolean rememberMe, HttpServletRequest request, Model model) {
		String userType = sysUser.getUserType();
		String username = sysUser.getUserName();
		Assert.notEmpty(ReplyCode.PARAMETER_FAILURE,"用户名不能为空",username);
		String password = sysUser.getPassword();
		String session_captcha = Toolkits.defaultString(request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY));
		String captcha = request.getParameter("captcha");
//		if (!session_captcha.equals(captcha)) {
//			return new ResultVoFailure("验证码错误");
//		}

		//获取subject对象
		Subject subject = SecurityUtils.getSubject();
		//封装用户数据
		LoginAuthToken token = new LoginAuthToken(username, password,rememberMe,userType);

		try {
			//执行Shiro配置的拦截方法
			subject.login(token);
			//登录失败：用户名不存在
		} catch (UnknownAccountException e) {
			e.printStackTrace();
			return new ResultVoFailure("用户名不存在");
			//登录失败：密码错误
		} catch (IncorrectCredentialsException e) {
			return new ResultVoFailure("密码错误");
		}
		return new ResultVoSuccess("登录成功",subject.getSession().getId());

	}

	@GetMapping("/export")
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