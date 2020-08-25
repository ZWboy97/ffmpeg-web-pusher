package com.zwboy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/24 23:31
 */
@Data
public class PushTask implements Serializable {

    private static final long serialVersionUID = 1095351988501724233L;

    /**
     * PushTask的id，需要保持唯一性
     */
    @JsonProperty("id")
    private String Id = "";

    /**
     * 推流源地址
     */
    @JsonProperty("pushSrcUrl")
    private String pushSrcUrl = "";

    /**
     * 推流目的地址
     */

    @JsonProperty("pushDescUrl")
    private String pushDescUrl = "";


}
