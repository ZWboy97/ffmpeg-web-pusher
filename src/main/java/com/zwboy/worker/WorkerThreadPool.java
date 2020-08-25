package com.zwboy.worker;


import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.zwboy.cache.CacheUtil;
import com.zwboy.enums.WorkStatusEnum;
import com.zwboy.model.PushTask;
import com.zwboy.model.WorkerStatus;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/25 14:24
 */

@Component
public class WorkerThreadPool {

    /**
     * java提供的线程池技术
     */
    private ThreadPoolExecutor poolExecutor = null;


    WorkerThreadPool() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNamePrefix("push-thread-").build();
        poolExecutor = new ThreadPoolExecutor(2, 10, 200000,
                TimeUnit.MICROSECONDS, new LinkedBlockingDeque<Runnable>(), threadFactory);
    }

    /**
     * 提交任务到线程池
     *
     * @param pushTask
     */
    public WorkerStatus execPushTask(PushTask pushTask) {
        WorkerStatus status = new WorkerStatus();
        PushWorker pushWorker = null;
        if(CacheUtil.PUSHWORKERMAP.containsKey(pushTask.getId())){
            pushWorker = CacheUtil.PUSHWORKERMAP.get(pushTask.getId());
            status.setRunningTime(pushWorker.getRunningTime());
            if(pushWorker.isRunning()){
                status.setWorkerStatus(WorkStatusEnum.RUNNING.getStatus());
            }else{
                status.setWorkerStatus(WorkStatusEnum.WAITING.getStatus());
            }
            return status;
        }
        pushWorker = new PushWorker(pushTask);
        CacheUtil.PUSHWORKERMAP.put(pushTask.getId(),pushWorker);
        poolExecutor.execute(pushWorker);
        if(pushWorker.isRunning()){
            status.setWorkerStatus(WorkStatusEnum.RUNNING.getStatus());
        }else if(!CacheUtil.PUSHWORKERMAP.containsKey(pushTask.getId())){
            status.setWorkerStatus(WorkStatusEnum.STOPPED.getStatus());
        }else{
            status.setWorkerStatus(WorkStatusEnum.WAITING.getStatus());
        }
        status.setRunningTime(pushWorker.getRunningTime());
        return status;
    }

    /**
     * 基于id，停止指定的推流
     *
     * @param taskId
     * @return true, 停止成功，false，该worker进程不存在
     */
    public WorkerStatus stopPushWorker(String taskId) {
        WorkerStatus status = new WorkerStatus();
        if (!CacheUtil.PUSHWORKERMAP.containsKey(taskId)) {
            status.setWorkerStatus(WorkStatusEnum.NOTEXIST.getStatus());
        }else{
            PushWorker worker = CacheUtil.PUSHWORKERMAP.get(taskId);
            worker.stop();
            status.setRunningTime(worker.getRunningTime());
            status.setWorkerStatus(WorkStatusEnum.STOPPED.getStatus());
        }
        return status;
    }

    /**
     * 获取指定推流的推流状态
     * @param taskId
     * @return
     */
    public WorkerStatus getPushWorkerStatus(String taskId){
        WorkerStatus status = new WorkerStatus();
        if(!CacheUtil.PUSHWORKERMAP.containsKey(taskId)){
            status.setWorkerStatus(WorkStatusEnum.NOTEXIST.getStatus());
        }else{
            PushWorker worker = CacheUtil.PUSHWORKERMAP.get(taskId);
            if(worker.isRunning()){
                status.setWorkerStatus(WorkStatusEnum.RUNNING.getStatus());
                status.setRunningTime(worker.getRunningTime());
            }else{
                status.setWorkerStatus(WorkStatusEnum.WAITING.getStatus());
            }
        }
        return status;
    }


}
