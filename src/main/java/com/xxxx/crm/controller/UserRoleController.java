package com.xxxx.crm.controller;

import com.xxxx.crm.service.UserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author xiaokaixin
 * @Date 2021/9/14 10:41
 * @Version 1.0
 */
@Controller
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;
}
