package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.CustomerContactMapper;
import com.xxxx.crm.query.CustomerLinkmanQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CustomerContact;
import com.xxxx.crm.vo.CustomerLinkman;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xiaokaixin
 * @Date 2021/9/27 18:07
 * @Version 1.0
 */
@Service
public class CustomerContactService extends BaseService<CustomerContact,Integer> {

    @Resource
    private CustomerContactMapper customerContactMapper;


    public Map<String, Object> queryCustomerLinkByParams(CustomerLinkmanQuery customerLinkmanQuery) {
        Map<String, Object> map = new HashMap<>();
        // 开启分页
        PageHelper.startPage(customerLinkmanQuery.getPage(), customerLinkmanQuery.getLimit());
        // 得到对应分页对象
        PageInfo<CustomerContact> pageInfo = new PageInfo<>(customerContactMapper.selectByParams(customerLinkmanQuery));

        // 设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());

        return map;

    }

    /**
     * 1。参数校验
     * 2。设置默认值
     * 3。执行添加操作
     * @param customerContact
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCustomerContact(CustomerContact customerContact) {
        //1。参数校验
        AssertUtil.isTrue(StringUtils.isBlank(customerContact.getAddress()),"地址不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(customerContact.getOverview()),"交往内容不能为空");

        //2。设置默认值
        customerContact.setIsValid(1);
        customerContact.setContactTime(new Date());
        customerContact.setUpdateDate(new Date());
        customerContact.setCreateDate(new Date());

        // 3。执行添加操作
        AssertUtil.isTrue(customerContactMapper.insertSelective(customerContact)<1,"添加交往记录失败");


    }

    /**
     * 更新
     * 1。参数校验
     * 2。设置默认值
     * 3。执行添加操作
     * @param customerContact
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerContact(CustomerContact customerContact) {
        //1。参数校验
        AssertUtil.isTrue(StringUtils.isBlank(customerContact.getAddress()),"地址不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(customerContact.getOverview()),"交往内容不能为空");

        //2。设置默认值

        customerContact.setUpdateDate(new Date());

        // 3。执行添加操作
        AssertUtil.isTrue(customerContactMapper.updateByPrimaryKeySelective(customerContact)<1,"更新交往记录失败");

    }

    /**
     * 删除交往信息
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCustomerContact(Integer id) {
        //获取要删除的交往信息
        CustomerContact customerContact = customerContactMapper.selectByPrimaryKey(id);
        //判断是否存在
        AssertUtil.isTrue(customerContact==null,"交往记录不存在");
        //设置is_valid = 0
        customerContact.setIsValid(0);
        //执行更新操作，判断受影响的行数
        AssertUtil.isTrue(customerContactMapper.updateByPrimaryKeySelective(customerContact)<1,"删除交往记录失败");




    }
}
