package com.qtdbp.security.utils;

import com.qtdbp.security.base.Message;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 工具类
 *
 * @author: caidchen
 * @create: 2017-05-11 14:22
 * To change this template use File | Settings | File Templates.
 */
public class ControllerTools {

    /**
     * 判断是否ajax
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {

        boolean isAjax = false ;
        String requestType = request.getHeader("X-Requested-With");
        if(requestType != null && requestType.equals("XMLHttpRequest")) isAjax = true ;

        return isAjax ;
    }

    /**
     * 是否授权登录
     *
     * @return
     */
    public static boolean hasAuthentication() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth == null ? false : auth.isAuthenticated() ;
    }

    public static void print(HttpServletResponse response, Message msg) {

    }
}
