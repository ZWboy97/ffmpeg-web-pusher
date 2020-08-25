package com.zwboy.util;

import com.zwboy.VO.ResultVO;
import com.zwboy.enums.ResultEnum;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/24 11:02
 */
public class ResultUtil {

    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(data);
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(ResultEnum resultEnum) {
        return error(resultEnum.getCode(), resultEnum.getMessage());
    }

    public static ResultVO error(Integer code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setMessage(message);
        resultVO.setCode(code);
        return resultVO;
    }

}
