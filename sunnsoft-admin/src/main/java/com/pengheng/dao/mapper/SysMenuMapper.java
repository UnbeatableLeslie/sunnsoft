package com.pengheng.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pengheng.dao.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-08-26
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    /**
     * 查询指定用户所拥有的权限
     *
     * @param userId 用户ID
     * @return 权限的Key
     */
    @Select("SELECT r.perms FROM sys_user_role AS ur RIGHT JOIN sys_role_menu AS rm ON (ur.role_id = rm.role_id AND ur.user_id = #{userId}) RIGHT JOIN sys_menu AS r ON (r.id = rm.menu_id)")
    Set<String> findMenuByUserId(@Param("userId") int userId);

}
