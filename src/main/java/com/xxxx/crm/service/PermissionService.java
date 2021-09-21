package com.xxxx.crm.service;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xiaokaixin
 * @Date 2021/9/17 16:29
 * @Version 1.0
 */
@Service
public class PermissionService extends BaseService<Permission,Integer> {

    @Resource
    PermissionMapper permissionMapper;

    /**
     * 通过查询用户的角色，角色拥有的资源，得到用户拥有的资源列表,(资源权限码)
     * @param userId
     * @return
     */
    public List<String> queryUserHasRoleHasPermissionByUserId(int userId) {
        return permissionMapper.queryUserHasRoleHasPermissionByUserId(userId);
    }
}
