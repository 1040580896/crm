package com.xxxx.crm.service;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.dao.ModuleMapper;
import com.xxxx.crm.dao.PermissionMapper;
import com.xxxx.crm.model.TreeModel;
import com.xxxx.crm.vo.Module;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xiaokaixin
 * @Date 2021/9/16 10:37
 * @Version 1.0
 */
@Service
public class ModuleService extends BaseService<Module,Integer> {

    @Resource
    private ModuleMapper moduleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 查询所有的资源列表
     * @return
     */
    public List<TreeModel> queryAllModules(Integer roleId){
        //查询所有的资源列表
        List<TreeModel> treeModelList = moduleMapper.queryAllModules();
        //查询指定的角色已经授权过的资源列表(查询角色拥有的资源ID)
        List<Integer> permissionIds = permissionMapper.queryRoleHasMoudleIdByRoleId(roleId);
        //判断角色是否拥有资源ID
        if(permissionIds!=null&&permissionIds.size()>0){
            //循环所有的资源列表，判断用户拥有的资源ID中是否有匹配的，如果有，设置为true
            treeModelList.forEach(treeModel -> {
                //判断角色拥有的资源ID是否有当前遍历的资源ID
                if(permissionIds.contains(treeModel.getId())){
                    //如果包含,则说明角色授权过，设置为checked为true
                    treeModel.setChecked(true);
                }
            });
        }
        return treeModelList;
    }
}
