package com.pengheng.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pengheng.manage.entity.SysUser;
import com.pengheng.manage.mapper.UserMapper;
import com.pengheng.manage.service.IUserService;
import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoFailure;
import com.pengheng.model.ResultVoSuccess;
import com.pengheng.util.Toolkits;
import org.apache.commons.collections.CollectionUtils;
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
	public ResultVo getUserList(SysUser sysUser) {
		List<Map<Object, Object>> userList = userMapper.getUserList(sysUser);
		if(CollectionUtils.isNotEmpty(userList)) {
			return new ResultVoSuccess("获取对象成功",userList);
		}else{
			return new ResultVoFailure("未找到对应数据");
		}
	}

	@Override
	public ResultVo getUserListByPage(SysUser sysUser) {
    	Toolkits.useQueryPaging();
		List<Map<Object, Object>> userList = userMapper.getUserList(sysUser);

        if(CollectionUtils.isNotEmpty(userList)) {
			return new ResultVoSuccess("获取分页对象成功",userList);
		}else{
			return new ResultVoFailure("未找到对应数据");
		}
	}


    @Override
    public ResultVo getUserListByPlusPage(SysUser sysUser) {
        IPage iPage = Toolkits.usePlusQueryPage();
        iPage = userMapper.selectMapsPage(iPage, null);
        if(iPage!=null) {
            return new ResultVoSuccess("获取分页对象成功",iPage);
        }else{
            return new ResultVoFailure("未找到对应数据");
        }
    }
}
