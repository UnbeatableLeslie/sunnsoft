package com.pengheng;

import com.pengheng.dao.entity.SysUser;
import com.pengheng.dao.mapper.UserMapper;
import com.pengheng.service.ISysMenuService;
import com.pengheng.service.ISysUserService;
import com.pengheng.util.Toolkits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
//@WebAppConfiguration
public class MyBatis_Plus_Test {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ISysMenuService sysMenuService;

    @Autowired
    ISysUserService sysUserService;

//    @Before
//    public void init() {
//        System.out.println("开始测试-----------------");
//    }
//
//    @After
//    public void after() {
//        System.out.println("测试结束-----------------");
//    }

    @Test
    public void testJunit(){
//        System.out.println("测试Junit");
//        List<SysUser> sysUsers = userMapper.selectList(null);
//        System.out.println(Toolkits.toJson(sysUsers));

//        SysMenu sysMenu = new SysMenu();
//        sysMenu.setMenuName("测试目录");
//        sysMenu.setOrderNum(1);
//        sysMenu.setMenuType("1");
//        boolean save = sysMenuService.save(sysMenu);
//        System.out.println(save);
//        System.out.println(sysMenu.getId());


        List<SysUser> list = sysUserService.list(null);
        System.out.println(Toolkits.toJson(list));
    }
}
