package com.shancaolv.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author duhn
 * @Description 统一返回类
 * @date 2021/9/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}
