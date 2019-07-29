package com.pengheng.manage.service;

import com.pengheng.domain.SysUser;
import com.pengheng.model.ResultVo;

import java.util.List;
import java.util.Map;

public interface IUserService {
   void getUser(Map<Object, Object> paramMap);

	ResultVo getUserList(SysUser sysUser);

    ResultVo getUserListByPage(SysUser sysUser);
}
