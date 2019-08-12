package com.pengheng.quartz.service;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.pengheng.quartz.job.AsyncJob;
import com.pengheng.quartz.job.CronJob;

/**
 * @author pengheng
 * @date 2019年8月12日19:00:07
 */
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 获取所有的定时任务
     */
    @Override
    public List<Map<Object, Object>> getAllCronJob() {
        List<Map<Object, Object>> list = new ArrayList<>();
        try {
            // 获取Scheduler
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            // 再获取Scheduler下的所有group
            List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
            for (String groupName : triggerGroupNames) {
                // 组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
                GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
                // 获取所有的triggerKey
                Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
                for (TriggerKey triggerKey : triggerKeySet) {
                    // 通过triggerKey在scheduler中获取trigger对象
                    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                    // 获取trigger拥有的Job
                    JobKey jobKey = trigger.getJobKey();
                    JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
                    // 组装页面需要显示的数据
                    Map<Object, Object> cronJobs = new HashMap<Object, Object>();
                    cronJobs.put("jobName", jobDetail.getName());
                    cronJobs.put("jobDataMap", jobDetail.getJobDataMap());
                    cronJobs.put("cronExpression", trigger.getCronExpression());
                    cronJobs.put("groupName", groupName);
                    cronJobs.put("jobKey", jobDetail.getKey());
                    cronJobs.put("description", jobDetail.getDescription());

                    Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
                    cronJobs.put("triggerState", triggerState);

                    list.add(cronJobs);
                }
            }
        } catch (Exception e) {
        }
        return list;
    }

    /**
     * 立即执行任务
     */
    @Override
    public void executeCronJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 创建一个定时任务
     *
     * @param jobName
     * @param jobGroup
     */
    @Override
    public void addCronJob(String jobName, String jobGroup, String cron) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                System.out.println("job:" + jobName + " 已存在");
            } else {
                // 构建job信息
                jobDetail = JobBuilder.newJob(CronJob.class).withIdentity(jobName, jobGroup).build();
                // 用JopDataMap来传递数据
                jobDetail.getJobDataMap().put("sid", "userService.getUser");// 通过这里动态设置需要传递的参数

                // 表达式调度构建器(即任务执行的时间,每5秒执行一次)
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

                // 按新的cronExpression表达式构建一个新的trigger
                CronTrigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(jobName + "_trigger", jobGroup + "_trigger").withSchedule(scheduleBuilder)
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAsyncJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                System.out.println("job:" + jobName + " 已存在");
            } else {
                // 构建job信息,在用JobBuilder创建JobDetail的时候，有一个storeDurably()方法，可以在没有触发器指向任务的时候，将任务保存在队列中了。然后就能手动触发了
                jobDetail = JobBuilder.newJob(AsyncJob.class).withIdentity(jobName, jobGroup).storeDurably().build();
                jobDetail.getJobDataMap().put("asyncData", "this is a async task");
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName + "_trigger", jobGroup + "_trigger") // 定义name/group
                        .startNow()// 一旦加入scheduler，立即生效
                        .withSchedule(simpleSchedule())// 使用SimpleTrigger
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pauseJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");

            scheduler.pauseTrigger(triggerKey);
            System.out.println("=========================pause job:" + jobName + " success========================");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pauseAllJob() {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            // 再获取Scheduler下的所有group
            List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
            for (String groupName : triggerGroupNames) {
                // 组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
                GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
                // 获取所有的triggerKey
                Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
                for (TriggerKey triggerKey : triggerKeySet) {
                    scheduler.pauseTrigger(triggerKey);
                }
            }
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 恢复任务
     *
     * @param jobName
     * @param jobGroup
     */
    @Override
    public void resumeJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");
            scheduler.resumeTrigger(triggerKey);
            System.out.println("=========================resume job:" + jobName + " success========================");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resumeAllJob() {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            // 再获取Scheduler下的所有group
            List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
            for (String groupName : triggerGroupNames) {
                // 组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
                GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
                // 获取所有的triggerKey
                Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
                for (TriggerKey triggerKey : triggerKeySet) {
                    scheduler.resumeTrigger(triggerKey);
                }
            }
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void deleteJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            scheduler.deleteJob(jobKey);
            System.out.println("=========================delete job:" + jobName + " success========================");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

}