package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;

/**
 * @Author xiaokaixin
 * @Date 2021/9/23 10:21
 * @Version 1.0
 */
public class CustomerOrderQuery extends BaseQuery {
    private Integer cusId;  // 客户ID

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }
}
