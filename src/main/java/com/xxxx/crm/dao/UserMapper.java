package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.User;

public interface UserMapper extends BaseMapper<User,Integer> {

    //通过用户名查询用户对象，返回用户对象
    public User queryUserByName(String userName);


}