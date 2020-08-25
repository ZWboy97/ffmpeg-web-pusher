package com.zwboy.service.impl;

import com.zwboy.model.PushTask;
import com.zwboy.model.WorkerStatus;
import com.zwboy.service.PushService;
import com.zwboy.worker.WorkerThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/24 23:34
 */
@Service
public class PushServiceImpl implements PushService {

    @Autowired
    private WorkerThreadPool workerThreadPool;

    @Override
    public WorkerStatus createPusher(PushTask pushTask) {
        return workerThreadPool.execPushTask(pushTask);
    }

    @Override
    public WorkerStatus stopPusher(String taskId) {
        return workerThreadPool.stopPushWorker(taskId);
    }

    @Override
    public WorkerStatus pusherStatus(String taskId) {
        return workerThreadPool.getPushWorkerStatus(taskId);
    }
}
