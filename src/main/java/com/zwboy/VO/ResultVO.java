package com.zwboy.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: LiJiaChang
 * @Date: 2020/8/24 10:55
 */
@Data
public class ResultVO<T> implements Serializable {


    private static final long serialVersionUID = 4330810419929109013L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回结果
     */
    private T data;
}
