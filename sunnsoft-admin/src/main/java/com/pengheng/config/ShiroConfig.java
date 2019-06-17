package com.pengheng.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	/**
	 * ShiroFilterFactoryBean  
	 * Shiro过滤器，针对IP地址进行拦截是否需要对应权限
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());

		// 设置需要拦截的路径
		Map<String, String> filterChain = new HashMap<>();
		// 设置登出拦截
		filterChain.put("/logout", "anon");
		// 登录后就可以直接访问
		filterChain.put("/test-RMBM", "user");
		// 拦截指定方法
		filterChain.put("/demo/list", "user");
		// 拦截授权
		// 通过加载数据库设置方法需要的权限
		filterChain.put("/demo/add", "perms[user:add]");
		// 过滤指定连接不用登录
		filterChain.put("/demo/error", "anon");
		filterChain.put("/kaptchaGet", "anon");
		filterChain.put("/login", "anon");

		filterChain.put("/**", "authc");// 拦截所有方法
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
	@Bean
	public DefaultWebSecurityManager defaultWebSecurityManager() {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		// 关联ream
		defaultWebSecurityManager.setRealm(authorizingRealm());
		return defaultWebSecurityManager;
	}
	
	@Bean
	public AuthorizingRealm authorizingRealm() {
		UserRealm userRealm = new UserRealm();
		userRealm.setCredentialsMatcher(hashedCredentialsMatcher());//设置密码加密规则
//		userRealm.setCredentialsMatcher(new CredentialsMatcher() {
//			@Override
//			public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
//				UsernamePasswordToken userToken = (UsernamePasswordToken) token;
//				//要验证的明文密码
//	            String plaintext = new String(userToken.getPassword());
//	            //数据库中的加密后的密文
//	            String hashed = info.getCredentials().toString();
//	            return BCrypt.checkpw(plaintext, hashed);
//			}
//		});
		
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

}
