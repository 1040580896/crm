package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.CustomerReprieveQuery;
import com.xxxx.crm.service.CustomerReprieveService;
import com.xxxx.crm.vo.CustomerReprieve;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author xiaokaixin
 * @Date 2021/9/24 18:50
 * @Version 1.0
 */
@Controller
@RequestMapping("customer_rep")
public class CustomerReprieveController extends BaseController {

    @Resource
    private CustomerReprieveService customerReprieveService;


    /**
     * 分页条件查询流失客户暂缓操作的列表
     * @param customerReprieveQuery
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public Map<String,Object> queryCustomerReprieveByParams(CustomerReprieveQuery customerReprieveQuery){
        return customerReprieveService.queryCustomerReprieveByParams(customerReprieveQuery);
    }

    /**
     * 添加暂缓数据
     * @param customerReprieve
     * @return
     */
    @ResponseBody
    @PostMapping("add")
    public ResultInfo addCustomerRepr(CustomerReprieve customerReprieve){
         customerReprieveService.addCustomerRepr(customerReprieve);
         return success("添加暂缓数据成功");
    }

    /**
     * 更新暂缓数据
     * @param customerReprieve
     * @return
     */
    @ResponseBody
    @PostMapping("update")
    public ResultInfo updateCustomerRepr(CustomerReprieve customerReprieve){
         customerReprieveService.updateCustomerRepr(customerReprieve);
        return success("修改暂缓数据成功");
    }


    /**
     * 删除暂缓数据
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("delete")
    public ResultInfo deleteCustomerRepr(Integer id){
        customerReprieveService.deleteCustomerRepr(id);
        return success("删除暂缓数据成功");
    }

    /**
     * 打开添加或者修改暂缓数据页面
     * @return
     */
    @RequestMapping("toAddOrUpdateCustomerReprPage")
    public String toAddOrUpdateCustomerReprPage(Integer lossId, HttpServletRequest request,Integer id){
        //将流失客户ID存到作用域中
        request.setAttribute("lossId",lossId);

        //判断id是否为空
        if(id!=null){
            //通过主键ID查询暂缓数据
            CustomerReprieve customerRep = customerReprieveService.selectByPrimaryKey(id);
            //设置到请求域中
            request.setAttribute("customerRep",customerRep);

        }


        return "customerLoss/customer_rep_add_update";
    }
}
