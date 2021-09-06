package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.SaleChanceMapper;
import com.xxxx.crm.enums.DevResult;
import com.xxxx.crm.enums.StateStatus;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xiaokaixin
 * @Date 2021/9/3 08:47
 * @Version 1.0
 */
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    /**
     * 多条件分页查询营销机会 (返回的数据格式必须满足LayUi中数据表格要求的格式)
     * @param saleChanceQuery
     * @return
     */
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery){
        Map<String ,Object> map = new HashMap<>();

        //开始分页
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getLimit());
        //得到对应的分页对象
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(saleChanceQuery));

        //设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        //设置分页好的列表
        map.put("data",pageInfo.getList());

        return  map;
    }


    /**
     * 添加营销机会
     *  1. 参数校验
     *      customerName客户名称    非空
     *      linkMan联系人           非空
     *      linkPhone联系号码       非空，手机号码格式正确
     *  2. 设置相关参数的默认值
     *      createMan创建人        当前登录用户名
     *      assignMan指派人
     *          如果未设置指派人（默认）
     *              state分配状态 （0=未分配，1=已分配）
     *                  0 = 未分配
     *              assignTime指派时间
     *                  设置为null
     *              devResult开发状态 （0=未开发，1=开发中，2=开发成功，3=开发失败）
     *                  0 = 未开发 （默认）
     *          如果设置了指派人
     *               state分配状态 （0=未分配，1=已分配）
     *                  1 = 已分配
     *               assignTime指派时间
     *                  系统当前时间
     *               devResult开发状态 （0=未开发，1=开发中，2=开发成功，3=开发失败）
     *                  1 = 开发中
     *      isValid是否有效  （0=无效，1=有效）
     *          设置为有效 1= 有效
     *      createDate创建时间
     *          默认是系统当前时间
     *      updateDate
     *          默认是系统当前时间
     *  3. 执行添加操作，判断受影响的行数
     *
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSaleChance(SaleChance saleChance){
        /* 1.校验参数*/
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        /* 2.设置相关的默认值*/
        //设置为有效 1= 有效
        saleChance.setIsValid(1);
        //createDate创建时间,默认是系统当前时间
        saleChance.setCreateDate(new Date());
        //updateDate创建时间,默认是系统当前时间
        saleChance.setUpdateDate(new Date());
        //判断是否设置了指派人
        if(StringUtils.isBlank(saleChance.getAssignMan())){
            //如果为空  则表示未设置指派人
            //state分配状态 （0=未分配，1=已分配） 0=未分配
            saleChance.setState(StateStatus.UNSTATE.getType());
            //assignTime指派时间 设置为null
            saleChance.setAssignMan(null);
            //devResult开发状态 （0=未开发，1=开发中，2=开发成功，3=开发失败）  0 = 未开发 （默认）
            saleChance.setDevResult(DevResult.UNDEV.getStatus());

        }else {
            //如果不为空，则表示设置了指派人
            // state分配状态 （0=未分配，1=已分配）1 = 已分配
            saleChance.setState(StateStatus.STATED.getType());
            //assignTime指派时间 系统当前时间
            saleChance.setAssignTime(new Date());
            //devResult开发状态 （0=未开发，1=开发中，2=开发成功，3=开发失败） 1 = 开发中
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }

        //3. 执行添加操作，判断受影响的行数
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance)!=1,"添加营销机会失败");

    }

    /**
     *  1. 参数校验
     *      customerName客户名称    非空
     *      linkMan联系人           非空
     *      linkPhone联系号码       非空，手机号码格式正确
     * @param customerName
     * @param linkMan
     * @param linkPhone
     */
    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone) {
        //customerName客户名称    非空
        AssertUtil.isTrue(StringUtils.isBlank(customerName), "客户名称不能为空");
        //linkMan联系人           非空
        AssertUtil.isTrue(StringUtils.isBlank(linkMan), "联系人不能为空");
        //linkPhone联系号码       非空，手机号码格式正确
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone), "联系人号码不能为空");
        //手机号码格式正确
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone), "联系人号码格式不正确");

    }


}
