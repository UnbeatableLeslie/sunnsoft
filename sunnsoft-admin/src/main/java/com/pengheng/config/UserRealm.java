package com.pengheng.config;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.pengheng.model.CriterionVo;
import com.pengheng.service.IDynamicSqlService;
import com.pengheng.util.Toolkits;

public class UserRealm extends AuthorizingRealm {

	@Autowired
	private IDynamicSqlService dynamicSqlService;
	
	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String password = null;// 数据库返回密码

		CriterionVo criterionVo = new CriterionVo();
		criterionVo.addCondition("login_name", usernamePasswordToken.getUsername());
		//查询用户信息
		Map<Object, Object> userMap = dynamicSqlService.dynamicSelectUnique("sys_user", criterionVo);
		if (MapUtils.isEmpty(userMap))// 如果没找到集合对象
		{
			throw new UnknownAccountException("账号或密码不正确");// 表示用户不存在
		}
		password = Toolkits.defaultString(userMap.get("password"));

		return new SimpleAuthenticationInfo(userMap, password, "");
	}

	/**
	 * 执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Subject subject = SecurityUtils.getSubject();
		System.out.println(Toolkits.toJson(subject.getPrincipal()));
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Map<Object,Object> userMap = (Map<Object, Object>) subject.getPrincipal();
		//查询用户的所有权限
		StringBuffer sb = new StringBuffer();
		sb.append("select sys_menu.perms from sys_menu"); 
		sb.append(" left join sys_role_menu on sys_menu.id = sys_role_menu.menu_id ");
		sb.append(" left join sys_role on sys_role_menu.role_id = sys_role.id"); 
		sb.append(" left join sys_user_role on sys_role.id = sys_user_role.role_id"); 
		sb.append(" left join sys_user on sys_user_role.user_id = sys_user.id"); 
		sb.append(" where sys_user.id = "+Toolkits.defaultString(userMap.get("id"))+" and sys_menu.perms <> ''"); 
		List<Map<Object, Object>> permsList = dynamicSqlService.dynamicSelect("("+sb+") as tbl");
		for (Map<Object, Object> map : permsList) {
			authorizationInfo.addStringPermission(Toolkits.defaultString(map.get("perms")));
		}
		return authorizationInfo;
	}

}
