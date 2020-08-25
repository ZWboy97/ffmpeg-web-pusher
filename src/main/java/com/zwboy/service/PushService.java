package com.zwboy.service;

import com.zwboy.model.PushTask;
import com.zwboy.model.WorkerStatus;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/24 10:40
 */
public interface PushService {

    /**
     * 启动一个推流器
     *
     * @param pushTask
     * @return
     */
    WorkerStatus createPusher(PushTask pushTask);

    /**
     * 停止指定的推流
     *
     * @param pushId
     * @return
     */
    WorkerStatus stopPusher(String pushId);

    /**
     * 获取指定推流器的工作状态
     *
     * @param pushId
     * @return
     */
    WorkerStatus pusherStatus(String pushId);

}
