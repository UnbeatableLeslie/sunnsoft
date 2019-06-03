package com.pengheng.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.CacheManager;

@Configuration
public class ShiroConfig {

	/**
	 * 缓存管理器 使用Ehcache实现
	 */
	@Bean
	public EhCacheManager ehCacheManager() {
		CacheManager cacheManager = CacheManager.getCacheManager("shiro-ehcache");
		EhCacheManager em = new EhCacheManager();
		if (cacheManager == null) {
			em.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");
			return em;
		} else {
			em.setCacheManager(cacheManager);
			return em;
		}
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setShared(true);
		return cacheManagerFactoryBean;
	}

	/**
	 * SimpleCookie
	 * Cookie 记住我功能
	 * @return
	 */
	public SimpleCookie rememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie();
		int maxAge = 60 * 60 * 24 * 30;// 设置有效期为30天/或者从配置中读取
		simpleCookie.setMaxAge(maxAge);
		simpleCookie.setName("rememberMe-ehcache");
		simpleCookie.setHttpOnly(true);//设置只能http方式防止xss攻击
		simpleCookie.setPath("/");
		return simpleCookie;
	}

	/**
	 * CookieRememberMe管理器
	 */
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
		return cookieRememberMeManager;
	}

	/**
	 * shiro session的管理
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(1 * 60 * 1000);// 30分钟
		// 设置sessionDao对session查询，在查询在线用户service中用到了
		sessionManager.setSessionDAO(sessionDAO());
		// 设置在cookie中的sessionId名称
		sessionManager.setSessionIdCookie(rememberMeCookie());
		return sessionManager;
	}

	@Bean
	public SessionDAO sessionDAO() {
		return new MemorySessionDAO();
	}

	/**
	 * ShiroFilterFactoryBean  
	 * Shiro过滤器，针对IP地址进行拦截是否需要对应权限
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(
			@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		/**
		 * Shiro内置过滤器，可以实现权限相关拦截 常用过滤器： 
		 * anon:无需认证（登录）可以访问 
		 * authc:必须认证才可以访问
		 * user:如果使用rememberme的功能可以直接访问 
		 * perms:必须得到资源权限才可以访问 
		 * role:必须得到角色权限才可以访问
		 */

		// 设置需要拦截的路径
		Map<String, String> filterChain = new HashMap<>();
		// 设置登出拦截
		filterChain.put("/logout", "anon");
		// 登录后就可以直接访问
		filterChain.put("/test-RMBM", "user");
		// 拦截指定方法
		filterChain.put("/demo/list", "authc");
		// 拦截授权
		// 通过加载数据库设置方法需要的权限
		filterChain.put("/demo/add", "perms[user:add]");
		// 过滤指定连接不用登录
		filterChain.put("/demo/error", "anon");
		filterChain.put("/kaptchaGet", "anon");
		filterChain.put("/login", "anon");

		filterChain.put("/*", "authc");// 拦截所有方法
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChain);

		// 设置拦截返回跳转的路径
		shiroFilterFactoryBean.setLoginUrl("/demo/error");//登录失败跳转页面
//		shiroFilterFactoryBean.setSuccessUrl("/");//登录成功跳转页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/demo/unauth");// 未授权跳转页面
		return shiroFilterFactoryBean;
	}

	/**
	 * DefaultWebSecurityManager 默认web安全管理器
	 */
	@Bean("securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(
			@Qualifier("authorizingRealm") AuthorizingRealm authorizingRealm) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		// 关联ream
		defaultWebSecurityManager.setRealm(authorizingRealm);
		// 设置缓存管理器
		defaultWebSecurityManager.setCacheManager(ehCacheManager());
		// 注入记住我管理器
		defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
		return defaultWebSecurityManager;
	}

	/**
	 * Realm
	 */
	@Bean("authorizingRealm")
	public AuthorizingRealm authorizingRealm() {
		UserRealm userRealm = new UserRealm();
		userRealm.setCredentialsMatcher(hashedCredentialsMatcher());//设置密码加密规则
		userRealm.setCachingEnabled(true);// 设置开启缓存查询
//		// 启用身份验证缓存开启
//		userRealm.setAuthenticationCachingEnabled(true);
//		// 缓存在ehcache.xml对应的名字
//		userRealm.setAuthenticationCacheName("users");
		// 设置权限缓存开启
		userRealm.setAuthorizationCachingEnabled(true);
		// 权限在缓存中存的名字
		userRealm.setAuthorizationCacheName("users-perms");
		return userRealm;
	}
	 //密码管理
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5"); //散列算法使用md5
//        credentialsMatcher.setHashIterations(2);        //散列次数，2表示md5加密两次
        credentialsMatcher.setStoredCredentialsHexEncoded(true);//启用十六进制存储
        return credentialsMatcher;
    }

	/**
	 * 开启shiro aop注解支持.使用代理方式;所以需要开启代码支持;
	 */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }

}
