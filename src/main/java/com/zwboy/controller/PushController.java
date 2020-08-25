package com.zwboy.controller;

import cn.hutool.core.util.StrUtil;
import com.zwboy.VO.ResultVO;
import com.zwboy.enums.ResultEnum;
import com.zwboy.exceptions.PusherException;
import com.zwboy.model.PushTask;
import com.zwboy.model.WorkerStatus;
import com.zwboy.service.PushService;
import com.zwboy.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/24 14:54
 */

@Slf4j
@RestController
public class PushController {

    @Autowired
    private PushService pushService;

    @PostMapping(value = "/pushers")
    public ResultVO<WorkerStatus> pusher(@RequestBody PushTask data) {
        if (StrUtil.hasBlank(data.getPushDescUrl(), data.getPushDescUrl())) {
            throw new PusherException(ResultEnum.PARAMES_ERROR);
        }
        WorkerStatus status = pushService.createPusher(data);
        return ResultUtil.success(status);
    }

    /**
     * 获取指定pusher的工作状态
     */
    @GetMapping(value = "/pushers/status")
    public ResultVO<WorkerStatus> pusherStatus(@RequestParam("id") String id ){
        if(StrUtil.isBlank(id)){
            throw new PusherException(ResultEnum.PARAMES_ERROR);
        }
        WorkerStatus status = pushService.pusherStatus(id);
        return ResultUtil.success(status);
    }

    /**
     * 停止指定pusher的工作
     */
    @DeleteMapping(value = "/pushers")
    public ResultVO<WorkerStatus> stopPusher(@RequestParam("id") String id){
        if(StrUtil.isBlank(id)){
            throw new PusherException(ResultEnum.PARAMES_ERROR);
        }
        WorkerStatus status = pushService.stopPusher(id);
        return ResultUtil.success(status);
    }

}
