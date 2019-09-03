package com.pengheng.config.shiro;

import com.pengheng.manage.entity.SysUser;
import com.pengheng.manage.mapper.SysMenuMapper;
import com.pengheng.manage.mapper.UserMapper;
import com.pengheng.util.Toolkits;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * @author 彭恒
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;


    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        LoginAuthToken usernamePasswordToken = (LoginAuthToken) token;

        List<SysUser> sysUserList = userMapper.selectByMap(Collections.singletonMap("login_name", usernamePasswordToken.getUsername()));

        // 表示用户不存在
        if (sysUserList.size() != 1) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        // 获取用户所有权限和角色

        SysUser sysUser = sysUserList.stream().findFirst().get();
        // 清除掉密码
        sysUser.setPermsList(sysMenuMapper.findMenuByUserId(sysUser.getId()));
        sysUser.setRoleList(userMapper.findRoleByUserId(sysUser.getId()));

        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), sysUser.getLoginName());
    }

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Subject subject = SecurityUtils.getSubject();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser sysUser = (SysUser) subject.getPrincipal();

        sysUser.getRoleList().stream().filter(item -> !item.isEmpty()).forEach(item -> authorizationInfo.addRole(Toolkits.defaultString(item)));
        sysUser.getPermsList().stream().filter(item -> !item.isEmpty()).forEach(item -> authorizationInfo.addStringPermission(Toolkits.defaultString(item)));

        return authorizationInfo;
    }

}
