package com.pengheng.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengheng.model.CriterionVo;
import com.pengheng.service.IDynamicSqlService;
import com.pengheng.util.Toolkits;

@Configuration
public class ShiroConfig {

	@Autowired
	private IDynamicSqlService dynamicSqlService;

	/**
	 * ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(
			@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		/**
		 * Shiro内置过滤器，可以实现权限相关拦截 常用过滤器： anon:无需认证（登录）可以访问 authc:必须认证才可以访问
		 * user:如果使用rememberme的功能可以直接访问 perms:必须得到资源权限才可以访问 role:必须得到角色权限才可以访问
		 */

		// 设置需要拦截的路径
		Map<String, String> filterChainDefinitionMap = new HashMap<>();
		// 拦截指定方法
		filterChainDefinitionMap.put("/demo/list", "authc");
		// 拦截授权
		// 通过加载数据库设置方法需要的权限
		filterChainDefinitionMap.put("/demo/add", "perms[user:add]");
		// 过滤指定连接不用登录
		filterChainDefinitionMap.put("/demo/error", "anon");
		filterChainDefinitionMap.put("/kaptchaGet", "anon");
		filterChainDefinitionMap.put("/login", "anon");

		filterChainDefinitionMap.put("/*", "authc");// 拦截所有方法
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		// 设置拦截返回跳转的路径
		shiroFilterFactoryBean.setLoginUrl("/demo/error");
		shiroFilterFactoryBean.setUnauthorizedUrl("/demo/unauth");// 未授权跳转页面
		return shiroFilterFactoryBean;
	}

	/**
	 * DefaultWebSecurityManager
	 */
	@Bean("securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(
			@Qualifier("authorizingRealm") AuthorizingRealm authorizingRealm) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		// 关联ream
		defaultWebSecurityManager.setRealm(authorizingRealm);
		// 设置缓存管理器
//		defaultWebSecurityManager.setCacheManager(cacheManager);
		// 会话管理器
		
		return defaultWebSecurityManager;
	}

	/**
	 * Realm
	 */
	@Bean("authorizingRealm")
	public AuthorizingRealm authorizingRealm() {
		return new AuthorizingRealm() {

			/**
			 * 执行认证逻辑
			 */
			@Override
			protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
					throws AuthenticationException {
				UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
				String password = null;// 数据库返回密码

				CriterionVo criterionVo = new CriterionVo();
				criterionVo.addCondition("username", usernamePasswordToken.getUsername());
				Map<Object, Object> userMap = dynamicSqlService.dynamicSelectUnique("tb_user", criterionVo);
				if (MapUtils.isEmpty(userMap))// 如果没找到集合对象
				{
					return null;// 表示用户不存在
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
				;
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				authorizationInfo.addStringPermission("user:add");
				return authorizationInfo;
			}
		};
	}

}
