package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.CustomerLinkmanMapper;
import com.xxxx.crm.query.CustomerLinkmanQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.vo.CustomerLinkman;
import com.xxxx.crm.vo.CustomerOrder;
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
 * @Date 2021/9/26 08:25
 * @Version 1.0
 */
@Service
public class CustomerLinkmanService extends BaseService<CustomerLinkman,Integer> {

    @Resource
    CustomerLinkmanMapper customerLinkmanMapper;

    public Map<String, Object> queryCustomerLinkByParams(CustomerLinkmanQuery customerLinkmanQuery) {
        Map<String, Object> map = new HashMap<>();

        // 开启分页
        PageHelper.startPage(customerLinkmanQuery.getPage(), customerLinkmanQuery.getLimit());
        // 得到对应分页对象
        PageInfo<CustomerLinkman> pageInfo = new PageInfo<>(customerLinkmanMapper.selectByParams(customerLinkmanQuery));

        // 设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        // 设置分页好的列表
        map.put("data",pageInfo.getList());

        return map;
    }

    /**
     * 添加联系人
     * 1.参数校验
     *      1。联系人不能为空
     *      2。电话号码
     * 2.设置参数的默认值
     * 3。执行添加操作判断受影响的行数
     * @param customerLinkman
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCustomerLinkman(CustomerLinkman customerLinkman) {
        /* 1.参数校验 */
        checkCustomerLinkmanParams(customerLinkman.getLinkName(),customerLinkman.getSex(),customerLinkman.getPhone(),customerLinkman.getOfficePhone());
        /* 2.设置参数的默认值 */
        customerLinkman.setCeateDate(new Date());
        customerLinkman.setUpdateDate(new Date());
        customerLinkman.setIsValid(1);

        /* 3.执行添加操作判断受影响的行数*/
        AssertUtil.isTrue(customerLinkmanMapper.insertSelective(customerLinkman)<1,"添加联系人失败");

    }


    /**
     * 更新联系人
     * 1.参数校验
     *      1。联系人不能为空
     *      2。电话号码
     *      3。等等
     * 2.设置参数的默认值
     * 3。执行添加操作判断受影响的行数
     * @param customerLinkman
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerLinkman(CustomerLinkman customerLinkman) {
        /* 1.参数校验 */
        checkCustomerLinkmanParams(customerLinkman.getLinkName(),customerLinkman.getSex(),customerLinkman.getPhone(),customerLinkman.getOfficePhone());
        /* 2.设置参数的默认值 */
        customerLinkman.setUpdateDate(new Date());

        /* 3.执行添加操作判断受影响的行数*/
        AssertUtil.isTrue(customerLinkmanMapper.updateByPrimaryKeySelective(customerLinkman)<1,"更新联系人失败");
    }




    //参数校验
    private void checkCustomerLinkmanParams(String linkName, String sex, String phone, String officePhone) {
        //联系人不能为空
        AssertUtil.isTrue(StringUtils.isBlank(linkName),"联系人不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(sex),"联系人性别不能为空");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone), "手机号码格式不正确！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(officePhone), "公司号码格式不正确！");
    }

    /**
     * 删除联系人
     * @param id
     */
    public void deleteCustomerLinkman(Integer id) {
        //查找要删除的联系人
        CustomerLinkman customerLinkman = customerLinkmanMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(customerLinkman==null,"待删除待联系人不存在");

        //设置状态
        customerLinkman.setIsValid(0);
        customerLinkman.setUpdateDate(new Date());

        //执行更新操作
        AssertUtil.isTrue(customerLinkmanMapper.updateByPrimaryKeySelective(customerLinkman)<1,"删除失败");


    }
}
