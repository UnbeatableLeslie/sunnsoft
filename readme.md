## 框架预计内置功能

- [x] SpringBoot+Mybatis+CriteriaVo处理三层业务逻辑
- [x] ManuelTransaction手动事务控制处理工具类 
- [x] 全局事物控制管理 
- [x] 全局异常处理	 
- [x] Shiro 安全验证功能
- [x] Shiro ehcache 缓存设置，保存权限信息避免反复查询数据库  
- [x] SpringSession + Redis 实现session共享功能，支持水平扩展  
- [x] 定时任务的动态添加暂停删除功能  
- [ ] 全局日志控制（通过aop方式实现）
- [ ] 文件上传公用方法 - 完成图片上传公用方法 
- [ ] 文件上传公用方法 - 添加虚拟路径映射，显示图片
- [ ] Excel文件导出 (Excel解析公用方法easyexcel/easypoi 选择一个)
- [ ] 国际化支持（待定）		参考：https://blog.csdn.net/mr_zhangs/article/details/79788618

 
#### Maven安装本地包方式

> mvn install:install-file -Dfile=sdk-java-1.3.jar -DgroupId=com.fuiou -DartifactId=fuiou -Dversion=1.3 -Dpackaging=jar
