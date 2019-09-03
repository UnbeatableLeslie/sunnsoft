package com.pengheng.service;

import com.pengheng.core.exception.ApplicationException;
import com.pengheng.manage.entity.SysUser;
import com.pengheng.model.ReplyCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * @author AlanViast
 */
@Service
public class SysUserService {


    /**
     * 必须回去当前用户, 否则抛出ApplicationException异常
     *
     * @return 当前用户信息
     */
    public SysUser requireCurrentUser() {
        Subject subject = SecurityUtils.getSubject();

        Object obj = subject.getPrincipal();
        if (obj instanceof SysUser) {
            SysUser sysUser = (SysUser) obj;
            sysUser.setPassword(null);
            return sysUser;
        }
        throw new ApplicationException(ReplyCode.AUTHOR_ERROR, "当前用户未登录!");
    }

}
