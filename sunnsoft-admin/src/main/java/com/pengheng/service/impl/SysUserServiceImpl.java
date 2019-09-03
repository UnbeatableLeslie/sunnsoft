package com.pengheng.service.impl;

import com.pengheng.dao.entity.SysUser;
import com.pengheng.dao.mapper.SysUserMapper;
import com.pengheng.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-08-30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
