package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.CustomerLossMapper;
import com.xxxx.crm.dao.CustomerMapper;
import com.xxxx.crm.dao.CustomerOrderMapper;
import com.xxxx.crm.query.CustomerQuery;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.vo.Customer;
import com.xxxx.crm.vo.CustomerLoss;
import com.xxxx.crm.vo.CustomerOrder;
import com.xxxx.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author xiaokaixin
 * @Date 2021/9/22 11:32
 * @Version 1.0
 */
@Service
public class CustomerService extends BaseService<Customer,Integer> {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private CustomerOrderMapper customerOrderMapper;

    @Resource
    private CustomerLossMapper customerLossMapper;


    /**
     * 多条件分页查询客户 (返回的数据格式必须满足LayUi中数据表格要求的格式)
     * @param customerQuery
     * @return
     */
    public Map<String,Object> queryCustomerByParams(CustomerQuery customerQuery){
        Map<String ,Object> map = new HashMap<>();

        //开始分页
        PageHelper.startPage(customerQuery.getPage(),customerQuery.getLimit());
        //得到对应的分页对象
        PageInfo<Customer> pageInfo = new PageInfo<>(customerMapper.selectByParams(customerQuery));

        //设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        //设置分页好的列表
        map.put("data",pageInfo.getList());

        return  map;
    }

    /**
     * 添加客户
     *  1. 参数校验
     *      客户名称 name
     *          非空，名称唯一
     *      法人代表 fr
     *          非空
     *      手机号码 phone
     *          非空，格式正确
     *  2. 设置参数的默认值
     *      是否有效 isValid    1
     *      创建时间 createDate 系统当前时间
     *      修改时间 updateDate 系统当前时间
     *      流失状态 state      0
     *          0=正常客户  1=流失客户
     *      客户编号 khno
     *          系统生成，唯一 （uuid | 时间戳 | 年月日时分秒 | 雪花算法）
     *          格式：KH + 时间戳
     *  3. 执行添加操作，判断受影响的行数
     *
     *
     * @param customer
     * @return void
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCustomer(Customer customer) {
        /* 1. 参数校验 */
        checkCustomerParams(customer.getName(), customer.getFr(), customer.getPhone());
        // 判断客户名的唯一性
        Customer temp = customerMapper.queryCustomerByName(customer.getName());
        // 判断名客户名称是否存在
        AssertUtil.isTrue(null != temp, "客户名称已存在，请重新输入！");

        /* 2. 设置参数的默认值 */
        customer.setIsValid(1);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        customer.setState(0);
        // 客户编号
        String khno = "KH" + System.currentTimeMillis();
        customer.setKhno(khno);

        /* 3. 执行添加操作，判断受影响的行数 */
        AssertUtil.isTrue(customerMapper.insertSelective(customer) < 1, "添加客户信息失败！");
    }

    /**
     * 添加客户
     *  1. 参数校验
     *      客户ID id
     *          非空，数据存在
     *      客户名称 name
     *          非空，名称唯一
     *      法人代表 fr
     *          非空
     *      手机号码 phone
     *          非空，格式正确
     *  2. 设置参数的默认值
     *      修改时间 updateDate 系统当前时间
     *  3. 执行更新操作，判断受影响的行数
     *
     *
     * @param customer
     * @return void
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomer(Customer customer){
        /* 1. 参数校验 */
        AssertUtil.isTrue(null==customer.getId(),"待更新的记录不存在");
        //通过客户ID查询客户记录
        Customer temp = customerMapper.selectByPrimaryKey(customer.getId());
        //判断客户记录是否存在
        AssertUtil.isTrue(null==temp,"待更新的记录不存在");
        //参数校验
        checkCustomerParams(customer.getName(),customer.getFr(),customer.getPhone());
        //通过客户名称查询客户记录
        temp = customerMapper.queryCustomerByName(customer.getName());
        // 判断客户记录，是否存在，且客户id是否与更新记录的id一致
        AssertUtil.isTrue(null!=temp&&!(temp.getId().equals(customer.getId())),"客户名称已经存在，请重新输入");

        /* 2. 设置参数的默认值 */
        customer.setUpdateDate(new Date());

        /* 3. 执行更新操作，判断受影响的行数 */
        AssertUtil.isTrue(customerMapper.updateByPrimaryKeySelective(customer)<1,"修改客户信息失败");

    }

    /**
     * 参数校验
     *      客户名称 name
     *          非空
     *      法人代表 fr
     *          非空
     *      手机号码 phone
     *          非空，格式正确
     * @param name
     * @param fr
     * @param phone
     * @return void
     */
    private void checkCustomerParams(String name, String fr, String phone) {
        // 客户名称 name    非空
        AssertUtil.isTrue(StringUtils.isBlank(name), "客户名称不能为空！");
        // 法人代表 fr      非空
        AssertUtil.isTrue(StringUtils.isBlank(fr), "法人代表不能为空！");
        // 手机号码 phone   非空
        // AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号码不能为空！");
        // 手机号码 phone   格式正确
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone), "手机号码格式不正确！");
    }


    /**
     * 删除客户信息
     *  1.参数校验
     *      id
     *          非空 数据存在
     *  2.设置参数的默认值
     *      isValid = 0
     *      updateDate 系统当前实际
     *  3.执行更新操作
     *  3.判断受影响的行数
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCustomer(Integer id) {
        // 判断id是否为空，数据是否存在
        AssertUtil.isTrue(null==id,"待删除的记录不存在");

        //通过id查询客户记录
        Customer customer = customerMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null==customer,"待删除的记录不存在");

        //设置状态为失效
        customer.setUpdateDate(new Date());
        customer.setIsValid(0);

        //执行更新操作
        AssertUtil.isTrue(customerMapper.updateByPrimaryKeySelective(customer)<1,"删除客户信息失败");
    }

    /**
     * 更新客户的流失状态
     *  1. 查询待流失的客户数据
     *  2. 将流失客户数据批量添加到客户流失表中
     *  3. 批量更新客户的流失状态  0=正常客户  1=流失客户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerState(){
        /* 1. 查询待流失的客户数据 */
        List<Customer> lossCustomerList = customerMapper.queryLossCustomers();
        /* 2. 将流失客户数据批量添加到客户流失表中 */
        // 判断流失客户数据是否存在
        if (lossCustomerList != null && lossCustomerList.size() > 0) {
            // 定义集合 用来接收流失客户的ID
            List<Integer> lossCustomerIds = new ArrayList<>();
            // 定义流失客户的列表
            List<CustomerLoss> customerLossList = new ArrayList<>();
            // 遍历查询到的流失客户的数据
            lossCustomerList.forEach(customer -> {
                // 定义流失客户对象
                CustomerLoss customerLoss = new CustomerLoss();
                // 创建时间  系统当前时间
                customerLoss.setCreateDate(new Date());
                // 客户经理
                customerLoss.setCusManager(customer.getCusManager());
                // 客户名称
                customerLoss.setCusName(customer.getName());
                // 客户编号
                customerLoss.setCusNo(customer.getKhno());
                // 是否有效  1=有效
                customerLoss.setIsValid(1);
                // 修改时间  系统当前时间
                customerLoss.setUpdateDate(new Date());
                // 客户流失状态   0=暂缓流失状态  1=确认流失状态
                customerLoss.setState(0);
                // 客户最后下单时间
                // 通过客户ID查询最后的订单记录（最后一条订单记录）
                CustomerOrder customerOrder = customerOrderMapper.queryLossCustomerOrderByCustomerId(customer.getId());
                // 判断客户订单是否存在，如果存在，则设置最后下单时间
                if (customerOrder != null) {
                    customerLoss.setLastOrderTime(customerOrder.getOrderDate());
                }
                // 将流失客户对象设置到对应的集合中
                customerLossList.add(customerLoss);

                // 将流失客户的ID设置到对应的集合中
                lossCustomerIds.add(customer.getId());
            });
            // 批量添加流失客户记录
            AssertUtil.isTrue(customerLossMapper.insertBatch(customerLossList) != customerLossList.size(), "客户流失数据转移失败！");

            /* 3. 批量更新客户的流失状态 */
            AssertUtil.isTrue(customerMapper.updateCustomerStateByIds(lossCustomerIds) != lossCustomerIds.size(), "客户流失数据转移失败！");
        }
    }
}
