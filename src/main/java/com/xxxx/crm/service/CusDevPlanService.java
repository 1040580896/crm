package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.CusDevPlanMapper;
import com.xxxx.crm.dao.SaleChanceMapper;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CusDevPlan;
import com.xxxx.crm.vo.SaleChance;
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
 * @Date 2021/9/9 08:03
 * @Version 1.0
 */

@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {

    @Resource
    private CusDevPlanMapper cusDevPlanMapper;

    @Resource
    private SaleChanceMapper saleChanceMapper;


    /**
     * 多条件分页查询客户开发计划 (返回的数据格式必须满足LayUi中数据表格要求的格式)
     * @param cusDevPlanQuery
     * @return
     */
    public Map<String,Object> queryCusDevPlanParams(CusDevPlanQuery cusDevPlanQuery){
        Map<String ,Object> map = new HashMap<>();

        //开始分页
        PageHelper.startPage(cusDevPlanQuery.getPage(),cusDevPlanQuery.getLimit());
        //c
        PageInfo<CusDevPlan> pageInfo = new PageInfo<>(cusDevPlanMapper.selectByParams(cusDevPlanQuery));

        //设置map对象
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        //设置分页好的列表
        map.put("data",pageInfo.getList());

        return  map;
    }


    /**
     * 添加客户开发计划
     *  1.参数校验
     *      营销机会ID 非空  数据存在
     *      计划项内容  为空
     *      计划时间 非空
     *  2.设置参数的默认值
     *      是否有效  默认有效
     *      创建时间  系统当前时间
     *      修改时间  系统当前时间
     *  3.执行添加操作，判断受影响的行数
     *
     *
     * @param cusDevPlan
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCusDevPlan(CusDevPlan cusDevPlan){
        /* 1. 参数校验  */
        checkCusDevPlanParams(cusDevPlan);

        /* 2. 设置参数的默认值 */
        // 是否有效    默认有效
        cusDevPlan.setIsValid(1);
        // 创建时间    系统当前时间
        cusDevPlan.setCreateDate(new Date());
        // 修改时间    系统当前时间
        cusDevPlan.setUpdateDate(new Date());

        /* 3. 执行添加操作，判断受影响的行数 */
        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan) != 1, "计划项数据添加失败！");


    }

    /**
     * 更新客户开发计划
     *  1.参数校验
     *      计划项ID   非空 数据存在
     *      营销机会ID 非空  数据存在
     *      计划项内容  为空
     *      计划时间 非空
     *  2.设置参数的默认值
     *      修改时间  系统当前时间
     *
     *  3.执行更新操作，判断受影响的行数
     * @param cusDevPlan
     */
    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        /* 1. 参数校验  */
        // 计划项ID     非空，数据存在
        AssertUtil.isTrue(null == cusDevPlan.getId()
                || cusDevPlanMapper.selectByPrimaryKey(cusDevPlan.getId()) == null, "数据异常，请重试！");
        checkCusDevPlanParams(cusDevPlan);

        /* 2. 设置参数的默认值 */
        // 修改时间    系统当前时间
        cusDevPlan.setUpdateDate(new Date());

        /* 3. 执行更新操作，判断受影响的行数 */
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan) != 1, "计划项更新失败！");

    }



    /**
     *  参数校验
     *      营销机会ID 非空  数据存在
     *      计划项内容  为空
     *      计划时间 非空
     * @param cusDevPlan
     */
    private void checkCusDevPlanParams(CusDevPlan cusDevPlan) {
        // 营销机会ID 非空  数据存在
        Integer sId = cusDevPlan.getSaleChanceId();
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(sId);
        System.out.println(saleChance);

        AssertUtil.isTrue(null==sId || saleChanceMapper.selectByPrimaryKey(sId)==null,"数据异常，请重试");

        //计划项内容  为空
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()),"计划项内容不能为空");

        //计划时间  非空
        AssertUtil.isTrue(null==cusDevPlan.getPlanDate(),"计划时间不能为空");
    }


    /**
     * 删除计划项
     *      1.判断参数
     *      2.判断ID是否为空，且数据存在
     *      3.修改对应的is_valid属性
     *      4.执行更新操作
     * @param id
     */
    public void deleteCusDevPlan(Integer id) {
        //判断ID是否为空，且数据存在
        AssertUtil.isTrue(id==null,"带删除的记录不存");
        //通过ID查询计划对象
        CusDevPlan cusDevPlan = cusDevPlanMapper.selectByPrimaryKey(id);
        //设置记录无效
        cusDevPlan.setIsValid(0);
        cusDevPlan.setUpdateDate(new Date());

        //执行更新操作
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)!=1,"计划项数据参数失败");


    }
}
