package com.gx.springboot.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.gx.springboot.domain.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author guoxing
 * @version V1.0
 * @Package com.gx.springboot.controller
 * @date 2021/1/27 15:57
 */
@Controller
public class LoginController {

    /**
     * session中的验证码
     */
    private String SHIRO_VERIFY_SESSION = "verifySessionCode";
    /**
     * 错误后的跳转地址
     */
    private String ERROR_CODE_URL = "login";
    /**
     * 成功后的跳转地址
     */
    private String SUCCESS_CODE_URL = "/success";
    /**
     * 验证失败提示
     */
    private String ERROR_PASSWORD = "密码不正确";
    private String ERROR_ACCOUNT = "账户不存在";
    private String ERROR_STATUS = "状态不正常";
    private String ERROR_KAPTCHA = "验证码不正确";

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @GetMapping("/login")
    public String login() {
        return ERROR_CODE_URL;
    }

    @GetMapping({"/","/success"})
    public String success(Model model){
        Subject currentUser = SecurityUtils.getSubject();
        UserInfo user = (UserInfo) currentUser.getPrincipal();
        model.addAttribute("username", user.getUsername());
        return "success";
    }

    @GetMapping("/403")
    public String page_403() {
        return "403";
    }


    @PostMapping("/login")
    public String login(String username, String password,String verifyCode, boolean rememberMe, Model model) {

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();

        // 获取session中的验证码
        String verCode = (String) currentUser.getSession().getAttribute(SHIRO_VERIFY_SESSION);
        if("".equals(verifyCode)||(!verCode.equals(verifyCode))){
            model.addAttribute("msg",ERROR_KAPTCHA);
            return ERROR_CODE_URL;
        }
        try {
            //主体提交登录请求到SecurityManager
            token.setRememberMe(rememberMe);
            currentUser.login(token);
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg", ERROR_PASSWORD);
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg", ERROR_ACCOUNT);
        } catch (AuthenticationException ae) {
            model.addAttribute("msg", ERROR_STATUS);
        }
        if (currentUser.isAuthenticated()) {
            model.addAttribute("username", username);
            return SUCCESS_CODE_URL;
        } else {
            token.clear();
            return ERROR_CODE_URL;
        }
    }

    /**
     * 获取验证码
     * @param response
     */
    @GetMapping("/getCode")
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        byte[] verByte = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            request.getSession().setAttribute(SHIRO_VERIFY_SESSION,createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge,"jpg",jpegOutputStream);
        } catch (IllegalArgumentException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (IOException e){
            e.printStackTrace();
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        verByte = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(verByte);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
