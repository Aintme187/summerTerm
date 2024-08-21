package com.example.manageapi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//在类的内部为构造函数自动生成参数
@AllArgsConstructor
public class Result {
    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }

    public static Result fail(ErrorCode errorCode) {
        return new Result(false, errorCode.getCode(), errorCode.getMsg(), null);
    }
}
