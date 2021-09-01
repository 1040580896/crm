package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.User;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author xiaokaixin
 * @Date 2021/8/30 17:03
 * @Version 1.0
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;
    /**
     * 系统登录页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    // 系统界面欢迎页
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";

    }
    /**
     * 后端管理主页面
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){

        //获取cookie中的用户Id
        int id = LoginUserUtil.releaseUserIdFromCookie(request);
        //查询用户对象，设置session作用域
        User user = userService.selectByPrimaryKey(id);
        request.getSession().setAttribute("user",user);
        return "main";
    }
}
