package com.pengheng.common;

import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoSuccess;
import com.pengheng.quartz.service.JobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author pengheng
 * @Remark 定时任务处理类
 * @date 2019年7月23日15:37:13
 */
@RestController
@RequestMapping("/common/quartz")
public class JobController {
    @Autowired
    private JobService jobService;

    /**
     * 获取所有cron任务
     *
     * @return
     */
//    @RequiresPermissions("common:quartz:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo list() {
        List<Map<Object, Object>> allCronJob = jobService.getAllCronJob();
        return new ResultVoSuccess("获取定时任务成功",allCronJob);
    }

    /**
     * 立即执行任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @RequestMapping(value = "/execute", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo executeCronJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        jobService.executeCronJob(jobName, jobGroup);
        return new ResultVoSuccess("启动定时任务成功");
    }

    /**
     * 创建cron任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @RequestMapping(value = "/cron", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo createCronJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup,
                                  @RequestParam("cron") String cron,
                                  @RequestParam("sid") String sid) {
        jobService.addCronJob(jobName, jobGroup, cron, sid);
        return new ResultVoSuccess("创建同步定时任务成功");
    }

    /**
     * 创建异步任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @RequestMapping(value = "/async", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo startAsyncJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        jobService.addAsyncJob(jobName, jobGroup);
        return new ResultVoSuccess("创建异步定时任务成功");
    }

    /**
     * 暂停任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @RequestMapping(value = "/pause", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo pauseJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        jobService.pauseJob(jobName, jobGroup);
        return new ResultVoSuccess("暂停定时任务成功");
    }

    /**
     * 暂停所有任务
     *
     * @return
     */
    @RequestMapping(value = "/pauseAll", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo pauseAll() {
        jobService.pauseAllJob();
        return new ResultVoSuccess("暂停全部定时任务成功");
    }

    /**
     * 恢复任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @RequestMapping(value = "/resume", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo resumeJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        jobService.resumeJob(jobName, jobGroup);
        return new ResultVoSuccess("恢复定时任务成功");
    }

    /**
     * 所有任务
     *
     * @return
     */
    @RequestMapping(value = "/resumeAll", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultVo resumeAllJob() {
        jobService.resumeAllJob();
        return new ResultVoSuccess("恢复全部定时任务成功");
    }

    /**
     * 删除务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
    public ResultVo deleteJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        jobService.deleteJob(jobName, jobGroup);
        return new ResultVoSuccess("删除定时任务成功");
    }
}