package com.pengheng.quartz.service;

import java.util.List;
import java.util.Map;

/**
 * @author pengheng
 * @date 2019年8月12日18:59:47
 */
public interface JobService {
	/**
	 * 获取所有任务
	 * 
	 * @return
	 */
	List<Map<Object, Object>> getAllCronJob();

	/**
	 * 立即执行任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 */
	void executeCronJob(String jobName, String jobGroup);

	/**
	 * 添加一个定时任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 */
	void addCronJob(String jobName, String jobGroup, String cron);

	/**
	 * 添加异步任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 */
	void addAsyncJob(String jobName, String jobGroup);

	/**
	 * 暂停任务
	 * 
	 * @param jobName
	 * @param jobGroup
	 */
	void pauseJob(String jobName, String jobGroup);

	/**
	 * 暂停所有任务
	 * 
	 */
	void pauseAllJob();

	/**
	 * 恢复任务
	 * 
	 * @param triggerName
	 * @param triggerGroup
	 */
	void resumeJob(String triggerName, String triggerGroup);

	/**
	 * 恢复所有任务
	 * 
	 */
	void resumeAllJob();

	/**
	 * 删除job
	 * 
	 * @param jobName
	 * @param jobGroup
	 */
	void deleteJob(String jobName, String jobGroup);



}