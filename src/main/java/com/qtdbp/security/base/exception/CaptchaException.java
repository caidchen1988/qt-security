package com.qtdbp.security.base.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义系统授权认证异常
 *
 * @author: caidchen
 * @create: 2017-05-11 11:14
 * To change this template use File | Settings | File Templates.
 */
public class CaptchaException extends AuthenticationException {

    private Integer code ;

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Integer code, String message) {
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
