package com.pengheng.quartz.job;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.pengheng.core.SpringContextUtil;
import com.pengheng.util.Toolkits;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author pengheng
 * @date 2019年8月12日18:59:25
 */
public class CronJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("=========================定时任务每5秒执行一次===============================1");
        System.out.println("jobName=====:" + jobExecutionContext.getJobDetail().getKey().getName());
        System.out.println("jobGroup=====:" + jobExecutionContext.getJobDetail().getKey().getGroup());
        System.out.println("taskData=====:" + jobExecutionContext.getJobDetail().getJobDataMap().get("taskData"));
        Map<String, Object> paramMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String sid = Toolkits.defaultString(paramMap.get("sid"));
        String service = StringUtils.substringBefore(sid, ".");
        String method = StringUtils.substringAfter(sid, ".");

        try {
            Object localService = SpringContextUtil.getBean(service);
            Method localMethod = localService.getClass().getDeclaredMethod(method, new Class[]{Map.class});
            localMethod.invoke(localService, new Object[]{paramMap});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}