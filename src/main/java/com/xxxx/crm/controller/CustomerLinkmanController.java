package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.CustomerLinkmanQuery;
import com.xxxx.crm.query.CustomerOrderQuery;
import com.xxxx.crm.service.CustomerLinkmanService;
import com.xxxx.crm.service.CustomerService;
import com.xxxx.crm.vo.Customer;
import com.xxxx.crm.vo.CustomerLinkman;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author xiaokaixin
 * @Date 2021/9/26 08:27
 * @Version 1.0
 */
@Controller
@RequestMapping("customer_link")
public class CustomerLinkmanController extends BaseController {

    @Resource
    CustomerLinkmanService customerLinkmanService;

    @Resource
    CustomerService customerService;

    /**
     * 打开客户的的订单页面
     * @return
     */
    @RequestMapping("toCustomerLinkPage")
    public String toCustomerOrderPage(Integer customerId, Model model){
        //通过客户ID查询客户记录，设置到请求域中
        model.addAttribute("customer",customerService.selectByPrimaryKey(customerId));
        return "customer/customer_link";
    }

    /**
     * 分页多条件查询客户订单列表
     *
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerLinkByParams(CustomerLinkmanQuery customerLinkmanQuery) {
        return customerLinkmanService.queryCustomerLinkByParams(customerLinkmanQuery);
    }

    /**
     * 添加联系人信息
     * @param customerLinkman
     * @return
     */
    @PostMapping("add")
    @ResponseBody()
    public ResultInfo addCustomerLinkman(CustomerLinkman customerLinkman){
        customerLinkmanService.addCustomerLinkman(customerLinkman);
        return success("添加联系人信息成功");
    }

    /**
     * 更新联系人信息
     * @param customerLinkman
     * @return
     */
    @PostMapping("update")
    @ResponseBody()
    public ResultInfo updateCustomerLinkman(CustomerLinkman customerLinkman){
        customerLinkmanService.updateCustomerLinkman(customerLinkman);
        return success("添加联系人信息成功");
    }


    /**
     * 删除联系人信息
     * @param id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody()
    public ResultInfo deleteCustomerLinkman(Integer id){
        customerLinkmanService.deleteCustomerLinkman(id);
        return success("删除联系人成功");
    }



    /**
     * 打开添加或修改客户联系人信息的对话框
     * @return
     */
    @RequestMapping("toAddOrUpdateCustomerLinkPage")
    public String toAddOrUpdateCustomerPage(Integer cusId,Integer id, HttpServletRequest request){
        if(id!=null){
            CustomerLinkman customerLinkman = customerLinkmanService.selectByPrimaryKey(id);
            request.setAttribute("customerLinkman",customerLinkman);
        }
        request.setAttribute("cusId",cusId);
        return "customer/add_update_link";
    }

}
