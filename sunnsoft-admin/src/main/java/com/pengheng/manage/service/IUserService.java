package com.pengheng.manage.service;

import java.util.List;
import java.util.Map;

public interface IUserService {
   void getUser(Map<Object, Object> paramMap);

	List<Map<Object,Object>> getUserList(Map<Object, Object> paramMap);

}
