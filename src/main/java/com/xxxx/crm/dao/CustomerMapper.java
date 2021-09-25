package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.Customer;

import java.util.List;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    // 通过客户名称查询客户对象
    Customer queryCustomerByName(String name);

    //查询待流失的客户数据
    List<Customer> queryLossCustomers();

    // 通过客户ID批量更新客户流失状态
    int updateCustomerStateByIds(List<Integer> lossCustomerIds);
}