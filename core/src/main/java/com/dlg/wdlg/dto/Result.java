package com.dlg.wdlg.dto;

import com.dlg.wdlg.exception.BusinessException;
import lombok.Data;

/**
 * API 响应
 * @param <T>
 */
@Data
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> fail(String msg) {
        Result<T> r = new Result<>();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }

    public static <T> Result<T> busFail(BusinessException e) {
        Result<T> r = new Result<>();
        r.setCode(1000);
        r.setMsg(e.getMessage());
        return r;
    }

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

}