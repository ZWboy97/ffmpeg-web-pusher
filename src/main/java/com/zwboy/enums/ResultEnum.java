package com.zwboy.enums;

import lombok.Getter;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/24 14:47
 */
@Getter
public enum ResultEnum implements CodeEnum {
    /**
     * 操作成功
     */
    SUCCESS(0, "ok"),
    /**
     * 操作失败
     */
    ERROR(2, "失败"),
    /**
     * 参数错误
     */
    PARAMES_ERROR(3, "参数错误"),
    /**
     * 服务错误
     */
    SERVICE_ERROR(4,"服务错误"),

    ;


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
