package com.zwboy.exceptions;

import com.zwboy.enums.ResultEnum;
import lombok.Data;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/24 23:53
 */
@Data
public class PusherException extends RuntimeException {

    private Integer code;

    public PusherException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public PusherException(String message){
        super(message);
    }

    public PusherException(Integer code, String message){
        super(message);
        this.code = code;
    }

}
