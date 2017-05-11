package com.qtdbp.security.base.exception;

/**
 * 时间格式异常
 *
 * @author: caidchen
 * @create: 2017-05-11 10:35
 * To change this template use File | Settings | File Templates.
 */
public class DateFormatException extends Exception {

    private Integer code ;

    public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(Integer code, String message) {
        super(message);
        this.code = code ;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
