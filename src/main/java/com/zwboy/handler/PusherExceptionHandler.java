package com.zwboy.handler;

import com.zwboy.VO.ResultVO;
import com.zwboy.exceptions.PusherException;
import com.zwboy.util.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/24 23:55
 */
@ControllerAdvice
public class PusherExceptionHandler {

    @ExceptionHandler(value = PusherException.class)
    @ResponseBody
    public ResultVO handlerPusherException(PusherException e){
        return ResultUtil.error(e.getCode(),e.getMessage());
    }

}
