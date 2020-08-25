package com.zwboy.model;

import cn.hutool.core.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zwboy.enums.WorkStatusEnum;
import lombok.Data;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/25 9:33
 */
@Data
public class WorkerStatus {

    /**
     * 当前推理worker的工作时长
     */
    private Integer workerStatus = WorkStatusEnum.NOTEXIST.getStatus();

    /**
     * 当前推流worker工作时长
     */
    private long runningTime = 0L;

    @JsonIgnore
    WorkStatusEnum getWorkStatusEnum() {
        return EnumUtil.getEnumAt(WorkStatusEnum.class, workerStatus);
    }

}
