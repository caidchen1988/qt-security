package com.qtdbp.security.base.exception;

/**
 * 自定义加载异常
 *
 * @author: caidchen
 * @create: 2017-05-11 10:35
 * To change this template use File | Settings | File Templates.
 */
public class InitializationException extends Exception {

    private Integer code ;

    public InitializationException(String message) {
        super(message);
    }

    public InitializationException(Integer code, String message) {
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
