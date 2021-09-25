package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;

/**
 * @Author xiaokaixin
 * @Date 2021/9/24 12:58
 * @Version 1.0
 */
public class CustomerLossQuery extends BaseQuery {

    private String customerNo;//客户编号
    private String customerName; //客户名称
    private String state; //流失状态 0=暂缓流失 1=确认流失

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
