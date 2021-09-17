package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    //通过角色ID查询记录
    Integer countPermissionByRoleId(Integer roleId);

    //通过角色ID删除权限记录
    void deletePermissionByRoleId(Integer roleId);

    //查询角色拥有的所有资源ID的集合
    List<Integer> queryRoleHasMoudleIdByRoleId(Integer roleId);
}