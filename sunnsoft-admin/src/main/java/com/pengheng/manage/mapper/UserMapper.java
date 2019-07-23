package com.pengheng.manage.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

    Map<Object,Object> checkuserById(Map<Object, Object> paramMap);

	List<Map<Object,Object>> getUserList(Map<Object, Object> paramMap);
}