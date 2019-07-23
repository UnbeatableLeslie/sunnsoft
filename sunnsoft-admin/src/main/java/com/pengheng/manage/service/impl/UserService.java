package com.pengheng.manage.service.impl;

import com.pengheng.manage.mapper.UserMapper;
import com.pengheng.manage.service.IUserService;
import com.pengheng.util.Toolkits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserService implements IUserService {
	
	@Autowired
	private UserMapper userMapper;
	
    @Override
    public void getUser(Map<Object, Object> paramMap) {
        System.out.println("定时任务 调用成功");
    }

	@Override
	public List<Map<Object,Object>> getUserList(Map<Object, Object> paramMap) {
		Toolkits.useQueryPaging(paramMap);
		return userMapper.getUserList(paramMap);
	}
}
