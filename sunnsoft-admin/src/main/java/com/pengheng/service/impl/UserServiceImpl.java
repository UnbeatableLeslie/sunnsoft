package com.pengheng.service.impl;

import com.pengheng.mapper.UserMapper;
import com.pengheng.service.UserService;
import com.pengheng.util.Toolkits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
	
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
