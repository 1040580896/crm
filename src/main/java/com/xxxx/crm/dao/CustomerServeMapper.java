package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.CustomerServe;

import java.util.List;
import java.util.Map;

public interface CustomerServeMapper extends BaseMapper<CustomerServe,Integer> {

    // 查询客户构成
    List<Map<String,Object>> countServerMake();
}