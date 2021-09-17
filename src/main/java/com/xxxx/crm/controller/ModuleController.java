package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.model.TreeModel;
import com.xxxx.crm.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author xiaokaixin
 * @Date 2021/9/16 10:38
 * @Version 1.0
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    @ResponseBody
    @RequestMapping("queryAllModules")
    private List<TreeModel> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }

    /**
     * 进入授权页面
     * @param roleId
     * @return
     */
    @RequestMapping("toAddGrantPage")
    public String toAddGrantPage(Integer roleId, HttpServletRequest request){
        // 将需要授权的角色ID，设置到请求域中
        request.setAttribute("roleId",roleId);
        return "role/grant";
    }
}
