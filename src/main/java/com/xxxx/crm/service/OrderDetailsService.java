package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.OrderDetailsMapper;
import com.xxxx.crm.query.OrderDetailsQuery;
import com.xxxx.crm.vo.OrderDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xiaokaixin
 * @Date 2021/9/23 15:56
 * @Version 1.0
 */
@Service
public class OrderDetailsService extends BaseService<OrderDetails,Integer> {

    @Resource
    private OrderDetailsMapper orderDetailsMapper;

    /**
     * 分页条件查询订单详情列表
     * @param orderDetailQuery
     * @return
     */
    public Map<String, Object> queryOrderDetailsByParams(OrderDetailsQuery orderDetailQuery) {
        Map<String ,Object> map = new HashMap<>();

        //开始分页
        PageHelper.startPage(orderDetailQuery.getPage(),orderDetailQuery.getLimit());
        //得到对应的分页对象
        PageInfo<OrderDetails> pageInfo = new PageInfo<>(orderDetailsMapper.selectByParams(orderDetailQuery));

        //设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        //设置分页好的列表
        map.put("data",pageInfo.getList());

        return  map;
    }
}
