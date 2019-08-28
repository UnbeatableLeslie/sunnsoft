package com.pengheng.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
	@Bean
	public SimpleCookie rememberMeCookie() {
		// 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// <!-- 记住我cookie生效时间30天 ,单位秒;-->
		simpleCookie.setMaxAge(259200);
		return simpleCookie;
	}

	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		// rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
		return cookieRememberMeManager;
	}

	/**
	 * ShiroFilterFactoryBean Shiro过滤器，针对IP地址进行拦截是否需要对应权限
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());

		// 设置需要拦截的路径
		Map<String, String> filterChain = new HashMap<>();
		// 设置登出拦截
		filterChain.put("/logout", "anon");
		// 过滤指定连接不用登录
		filterChain.put("/unlogin", "anon");
		filterChain.put("/unauth", "anon");
		filterChain.put("/login", "anon");
		filterChain.put("/file", "anon");
		//后台管理自定义过滤器配置，验证是否是对应角色
		filterChain.put("/system/**","authc,roles[admin]");
		//门户网站自定义过滤器配置，验证是否是对应角色
//		filterChain.put("/portal/**","portalFilter");
		//APP管理自定义过滤器配置，验证是否是对应角色
//		filterChain.put("/app/**","appFilter");

		filterChain.put("/common/*", "anon");
		filterChain.put("/images/kaptcha.jpg", "anon");
		filterChain.put("/**/*.js", "anon");
		filterChain.put("/**/*.html", "anon");
		filterChain.put("/", "anon");

		// 拦截所有方法
		filterChain.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);

		// 设置拦截返回跳转的路径
		// 未登录跳转页面
		shiroFilterFactoryBean.setLoginUrl("/unlogin");
		//登录成功跳转页面
		// shiroFilterFactoryBean.setSuccessUrl("/");
		// 未授权跳转页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");
		return shiroFilterFactoryBean;
	}

	/**
	 * DefaultWebSecurityManager 默认web安全管理器
	 */
	@Bean
	public DefaultWebSecurityManager defaultWebSecurityManager() {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		// 关联ream
		defaultWebSecurityManager.setRealm(authorizingRealm());
		defaultWebSecurityManager.setRememberMeManager(rememberMeManager());


		return defaultWebSecurityManager;
	}

	@Bean
	public AuthorizingRealm authorizingRealm() {
		UserRealm userRealm = new UserRealm();
		userRealm.setCredentialsMatcher(new CredentialsMatcher() {
			@Override
			public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
				LoginAuthToken userToken = (LoginAuthToken) token;
				// 要验证的明文密码
				String plaintext = new String(userToken.getPassword());
				// 数据库中的加密后的密文
				String hashed = info.getCredentials().toString();
				return BCrypt.checkpw(plaintext, hashed);
			}
		});

		return userRealm;
	}
	/**
	 * 	AOP注入回调授权方法
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
	    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
	    authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager());
	    return authorizationAttributeSourceAdvisor;
	}
}
