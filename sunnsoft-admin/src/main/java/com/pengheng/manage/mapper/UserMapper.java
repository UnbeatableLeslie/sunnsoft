package com.pengheng.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pengheng.manage.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<SysUser> {

    Map<Object,Object> checkuserById(Map<Object, Object> paramMap);

	List<Map<Object,Object>> getUserList(SysUser sysUser);
}
