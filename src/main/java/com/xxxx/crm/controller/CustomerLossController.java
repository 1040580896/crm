package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.CustomerLossQuery;
import com.xxxx.crm.service.CustomerLossService;
import com.xxxx.crm.vo.CustomerLoss;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author xiaokaixin
 * @Date 2021/9/24 12:51
 * @Version 1.0
 */
@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController {

    @Resource
    CustomerLossService customerLossService;

    /**
     * 进入客户流失管理页面
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "customerLoss/customer_loss";
    }

    /**
     * 分页查询流失客户列表
     * @param customerLossQuery
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public Map<String,Object> queryCustomerLossByParams(CustomerLossQuery customerLossQuery){

        return customerLossService.queryCustomerLossByParams(customerLossQuery);
    }

    /**
     * 打开添加展缓/详情页面
     * @param lossId
     * @return
     */
    @RequestMapping("toCustomerLossPage")
    public String toCustomerLossPage(Integer lossId, Model model){
        //通过流失客户的ID，查询对应流失客户的记录
        CustomerLoss customerLoss = customerLossService.selectByPrimaryKey(lossId);
        //将流失的客户对应的数据存到请求域中
        model.addAttribute("customerLoss",customerLoss);

        return "customerLoss/customer_rep";
    }


    /**
     * 更新流失客户的流失状态
     *
     *
     * @param
     * @return com.xxxx.crm.base.ResultInfo
     */
    @PostMapping("updateCustomerLossStateById")
    @ResponseBody
    public ResultInfo updateCustomerLossStateById(Integer id, String lossReason) {
        customerLossService.updateCustomerLossStateById(id, lossReason);
        return success("确认流失成功！");
    }

}
