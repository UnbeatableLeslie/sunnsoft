package com.pengheng.controller.system;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pengheng.core.BaseController;
import com.pengheng.util.Toolkits;

@RestController
public class RedisLockController extends BaseController{

	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@RequestMapping("/redisLock")
	public void redisLock(Model model) throws Exception {
		Map<Object,Object> paramMap = getParameterMap(model);
		String key = String.format("redis:lock:id:%", Toolkits.defaultString(paramMap.get("id")));
		
		Boolean res = false;
		while(res) {
			String value = UUID.randomUUID().toString()+System.nanoTime();
			res = redisTemplate.opsForValue().setIfAbsent(key,value,5,TimeUnit.SECONDS);
			if(res) {
				try {
					res=false;
					//TODO 处理数据库操作 删除数据
				}catch (Exception e) {
					// TODO: handle exception
				}finally{
					String redisValue = redisTemplate.opsForValue().get(key);
					if(value.equals(redisValue)) {
						redisTemplate.delete(key);
					}
				}
			}else {
				Thread.sleep(1000);
			}
		}
		
		
	}
}
