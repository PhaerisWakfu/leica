package com.gitee.leica.object;

import lombok.Data;

/**
 * @author wyh
 * @since 2021/1/19 17:36
 */
@Data
public class ResultBase<T> {

    public static final String SUCCEED = "0";

    public static final String FAILURE = "1";

    private static final String SUCCEED_MSG = "ok";

    private String code;

    private String msg;

    private T data;

    public static <T> ResultBase<T> ok(T data) {
        ResultBase<T> resultBase = new ResultBase<>();
        resultBase.setData(data);
        resultBase.setCode(SUCCEED);
        resultBase.setMsg(SUCCEED_MSG);
        return resultBase;
    }

    public static <T> ResultBase<T> ok() {
        return ok(null);
    }

    public static <T> ResultBase<T> fail(String msg) {
        ResultBase<T> resultBase = new ResultBase<>();
        resultBase.setCode(FAILURE);
        resultBase.setMsg(msg);
        return resultBase;
    }

    public static <T> ResultBase<T> fail(String format, Object... args) {
        return fail(String.format(format, args));
    }
}
