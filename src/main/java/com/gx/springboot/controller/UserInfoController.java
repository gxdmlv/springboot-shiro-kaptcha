package com.gx.springboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author guoxing
 * @version V1.0
 * @Package com.gx.springboot.controller
 * @date 2021/1/28 15:02
 */
@Controller
@ResponseBody
public class UserInfoController {

    @GetMapping("/cat")
    @RequiresRoles("cat")
    public String cat(){
        return "cat";
    }
    @GetMapping("/dog")
    @RequiresRoles("dog")
    public String dog(){
        return "dog";
    }
    @GetMapping("/sing")
    @RequiresPermissions("sing")
    public String sing(){
        return "sing";
    }
    @GetMapping("/jump")
    @RequiresPermissions("jump")
    public String jump(){
        return "jump";
    }
    @GetMapping("/rap")
    @RequiresPermissions("rap")
    public String rap(){
        return "rap";
    }
    @GetMapping("/basketball")
    @RequiresPermissions("basketball")
    public String basketball(){
        return "basketball";
    }


}
