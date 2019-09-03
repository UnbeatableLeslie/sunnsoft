package com.pengheng.service;

import com.pengheng.dao.entity.SysUser;
import com.pengheng.model.ResultVo;

import java.util.Map;

public interface IUserService {
   void getUser(Map<Object, Object> paramMap);

	ResultVo getUserList(SysUser sysUser);

    ResultVo getUserListByPage(SysUser sysUser);

    ResultVo getUserListByPlusPage(SysUser sysUser);

}
