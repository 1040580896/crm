package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.CustomerLinkmanQuery;
import com.xxxx.crm.service.CustomerContactService;
import com.xxxx.crm.service.CustomerService;
import com.xxxx.crm.vo.CustomerContact;
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
 * @Date 2021/9/27 18:09
 * @Version 1.0
 */
@Controller
@RequestMapping("customer_contact")
public class CustomerContactController extends BaseController {

    @Resource
    CustomerContactService customerContactService;

    @Resource
    CustomerService customerService;

    /**
     * 打开交往记录的页面
     * @return
     */
    @RequestMapping("toCustomerContactPage")
    public String toCustomerOrderPage(Integer customerId, Model model){
        //通过客户ID查询客户记录，设置到请求域中
        model.addAttribute("customer",customerService.selectByPrimaryKey(customerId));
        return "customerContact/customer_contact";
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
        return customerContactService.queryCustomerLinkByParams(customerLinkmanQuery);
    }



    /**
     * 添加联系人信息
     * @param customerContact
     * @return
     */
    @PostMapping("add")
    @ResponseBody()
    public ResultInfo addCustomerContact(CustomerContact customerContact){
        customerContactService.addCustomerContact(customerContact);
        return success("添加交往信息成功");
    }

    /**
     * 更新交往信息
     * @param customerContact
     * @return
     */
    @PostMapping("update")
    @ResponseBody()
    public ResultInfo updateCustomerContact(CustomerContact customerContact){
        customerContactService.updateCustomerContact(customerContact);
        return success("更新交往信息成功");
    }


    /**
     * 删除交往信息
     * @param id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody()
    public ResultInfo deleteCustomerContact(Integer id){
        customerContactService.deleteCustomerContact(id);
        return success("删除联系人成功");
    }


    /**
     * 打开添加或修改交往记录的对话框
     * @return
     */
    @RequestMapping("toAddOrUpdateCustomerContactPage")
    public String toAddOrUpdateCustomerPage(Integer cusId,Integer id, HttpServletRequest request){
        if(id!=null){
            CustomerContact customerContact = customerContactService.selectByPrimaryKey(id);
            request.setAttribute("customerContact",customerContact);
        }
        request.setAttribute("cusId",cusId);
        return "customerContact/add_update_link";
    }


}
