package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.query.OrderDetailsQuery;
import com.xxxx.crm.service.OrderDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author xiaokaixin
 * @Date 2021/9/23 15:57
 * @Version 1.0
 */
@Controller
@RequestMapping("order_details")
public class OrderDetailsController extends BaseController {

    @Resource
    private OrderDetailsService orderDetailsService;


    /**
     * 分页条件查询订单详情列表
     * @param orderDetailQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryOrderDetailsByParams(OrderDetailsQuery orderDetailQuery){
        return orderDetailsService.queryOrderDetailsByParams(orderDetailQuery);
    }

}
