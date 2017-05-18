package com.qtdbp.security.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * @author: caidchen
 * @create: 2017-05-12 17:01
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class IndexController {

    @RequestMapping("/home")
    public String home() {
        return "home";

    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public String toAdminGet(){
        return "helloAdmin";
    }

    @PreAuthorize("hasRole('admin')")
    @RequestMapping(value = "/admin",method = RequestMethod.POST)
    public String toAdmin(){
        return "redirect:admin";
    }

    @PreAuthorize("hasRole('user')")
    @RequestMapping(value = "/hellouser",method = RequestMethod.GET)
    public String toUser(){
        return "helloUser";
    }

    @RequestMapping("/hello")
    public String hello() {

        return "hello";

    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/")
    public String root() {
        return "index";

    }

    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @RequestMapping("/timeout")
    public String timeout() {
        return "session_timeout";
    }

    @RequestMapping("/403")
    public String error(){
        return "403";
    }


    @RequestMapping(value = "/products",method = RequestMethod.GET)
    public String products(){
        return "products";
    }

    @RequestMapping(value = "/products/add",method = RequestMethod.GET)
    public String addProducts(){
        return "products/add";
    }

    /*===============================验证码=================================*/

    @Autowired
    DefaultKaptcha defaultKaptcha;

    @RequestMapping("/login/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            httpServletRequest.getSession().setAttribute("vrifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
