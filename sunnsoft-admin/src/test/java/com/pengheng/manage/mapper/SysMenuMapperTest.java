package com.pengheng.manage.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author AlanViast
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuMapperTest {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Test
    public void findMenuByUserId() {
        System.out.println(sysMenuMapper.findMenuByUserId(100));
    }
}