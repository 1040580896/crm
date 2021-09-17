package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.model.TreeModel;
import com.xxxx.crm.vo.Module;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    public List<TreeModel> queryAllModules();
}