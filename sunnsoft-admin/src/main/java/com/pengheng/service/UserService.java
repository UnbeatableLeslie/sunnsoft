package com.pengheng.service;

import java.util.List;
import java.util.Map;

public interface UserService {
   void getUser(Map<Object,Object> paramMap);

	List<Map<Object,Object>> getUserList(Map<Object, Object> paramMap);

}
