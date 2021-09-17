package com.xxxx.crm.interceptor;

import com.xxxx.crm.dao.UserMapper;
import com.xxxx.crm.exceptions.NoLoginException;
import com.xxxx.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author xiaokaixin
 * @Date 2021/9/2 10:06
 * @Version 1.0
 * 非法访问拦截
 */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    //注入UserMapper
    @Resource
    private UserMapper userMapper;

    /**
     * 拦截用户是否是登陆状态
     *     在目标方法（目标资源）执行前，执行的方法
     * 方法返回布尔类型
     *      true：表示目标方法可以被执行
     *      false：表示阻止目标方法执行
     * 如果判读用户是否是登陆状态
     *      1.判断cookie是否有用户信息(获取用户ID)
     *      2.数据库中是否存在指定用户ID的值
     *
     *  如果用户是登陆状态，则允许目标方法执行，如果用户是非登陆状态(在全局异常中做判断，如果未登陆操作，则跳转到登陆页面)
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取cookie中的用户ID
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 判断用户ID是否为空，且数据库中存在该ID的用户记录
        if (null == userId || userMapper.selectByPrimaryKey(userId) == null) {
            // 抛出未登录异常
            throw new NoLoginException();
        }
        return true;
    }
}
