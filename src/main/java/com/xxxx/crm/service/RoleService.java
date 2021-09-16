package com.xxxx.crm.service;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.RoleMapper;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author xiaokaixin
 * @Date 2021/9/13 13:05
 * @Version 1.0
 */
@Service
public class RoleService extends BaseService<Role,Integer> {

    @Autowired
    private RoleMapper roleMapper;


    /**
     * 查询所有的角色列表
     * @return
     */
    public List<Map<String,Object>> queryAllRoles(Integer id){
        return roleMapper.queryAllRoles(id);
    }

    /**
     * 添加角色
     *  1.参数校验
     *      角色名称    非空 名称唯一
     *  2.设置参数的默认值
     *      是否有效
     *      创建时间
     *      修改时间
     *  3.执行添加操作，判断受影响的行数
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRole(Role role){
        /* 1.参数校验 */
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名称不能为空");
        // 通过角色名查询角色记录
        Role temp = roleMapper.selectByRoleName(role.getRoleName());
        // 判断角色记录是否存在(添加操作时，如果角色记录存在则表示名称不可用)
        AssertUtil.isTrue(temp!=null,"角色名称已存在，请重新输入");

        /* 2.设置参数的默认值 */
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());

        /*3.执行添加操作，判断受影响的行数*/
        AssertUtil.isTrue(roleMapper.insertSelective(role)<1,"角色添加失败");



    }


    /**
     * 修改角色
     *  1.参数校验
     *      角色ID，非空判断，且数据存在
     *      角色名称    非空 名称唯一
     *  2.设置参数的默认值
     *      修改时间
     *
     *   3.执行更新操作，判断受影响的行数
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(Role role){
        /* 1.参数校验 */
        //角色ID，非空判断，且数据存在
        AssertUtil.isTrue(null==role.getId(),"待更新记录不存在");
        //通过角色ID查询角色记录
        Role temp = roleMapper.selectByPrimaryKey(role.getId());
        //判断角色记录是否存在
        AssertUtil.isTrue(null == temp,"待更新记录不存在");

        //角色名称    非空 名称唯一
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名称不能为空");
        //通过角色名称查询角色记录
        temp = roleMapper.selectByRoleName(role.getRoleName());
        //判断角色记录是否存在(如果不存在，表示可用，如果存在，且角色iD与当前更新角色ID不一致,表示角色名称不可用)
        AssertUtil.isTrue(null!=temp&&(!temp.getId().equals(role.getId())),"角色名称已经存在，不可使用");

        /*2.设置参数的默认值 */
        role.setUpdateDate(new Date());

        /*3.执行更新操作，判断受影响的行数*/

        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"修改角色失败");


    }

    /**
     * 删除角色
     *      1.参数校验
     *          角色ID  非空，数据存在
     *      2.设置相关参数的默认值
     *          是否有效   0(删除记录）
     *          修改时间 系统默认时间
     *      3.执行更新操作，判断受影响的行数
     * @param roleId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRole(Integer roleId){
        // 1.参数校验
        // 判断角色ID是否为空
        AssertUtil.isTrue(null==roleId,"待删除的记录不存在");
        //通过角色ID查询角色记录
        Role role = roleMapper.selectByPrimaryKey(roleId);
        //判断角色记录是否存在
        //设置删除状态
        role.setIsValid(0);
        role.setUpdateDate(new Date());

        //执行更新操作
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"角色删除失败");


    }



}
