package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;

/**
 * @Author xiaokaixin
 * @Date 2021/9/24 18:55
 * @Version 1.0
 */
public class CustomerReprieveQuery extends BaseQuery {

    //流失客户ID
    private Integer lossId;

    public Integer getLossId() {
        return lossId;
    }

    public void setLossId(Integer lossId) {
        this.lossId = lossId;
    }
}
