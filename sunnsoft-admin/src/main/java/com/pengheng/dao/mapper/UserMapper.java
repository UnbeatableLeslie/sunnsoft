package com.pengheng.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pengheng.dao.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface UserMapper extends BaseMapper<SysUser> {

    Map<Object,Object> checkuserById(Map<Object, Object> paramMap);

	List<Map<Object,Object>> getUserList(SysUser sysUser);

    /**
     * 查询指定用户的RoleKey
     *
     * @param userId 用户ID
     * @return 角色Key集合
     */
    @Select("SELECT r.role_key FROM sys_user_role AS ur RIGHT JOIN sys_role r ON (ur.role_id = r.id AND ur.user_id = #{userId})")
    Set<String> findRoleByUserId(@Param("userId") int userId);
}
