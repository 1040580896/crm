package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.exceptions.ParamsException;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author xiaokaixin
 * @Date 2021/8/31 13:01
 * @Version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 登陆功能
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultInfo userLogin(String userName,String userPwd){

        ResultInfo resultInfo = new ResultInfo();
        //调用service层方法
        UserModel userModel = userService.userLogin(userName, userPwd);
        //设置ResultInfo的result的值  将数据返回给请求)
        resultInfo.setResult(userModel);
        //通过try catch捕获service层的异常，如果service抛出异常，则表示登陆失败，否则失败
        // try {
        //
        //     //调用service层方法
        //     UserModel userModel = userService.userLogin(userName, userPwd);
        //     //设置ResultInfo的result的值  将数据返回给请求)
        //     resultInfo.setResult(userModel);
        //
        // }catch (ParamsException p){
        //     resultInfo.setCode(p.getCode());
        //     resultInfo.setMsg(p.getMsg());
        //     p.printStackTrace();
        // }catch (Exception e){
        //     resultInfo.setCode(500);
        //     resultInfo.setMsg("登陆失败！");
        // }

        return resultInfo;
    }

    /**
     * 跳转到修改密码页面
     * @return
     */
    @RequestMapping("toPasswordPage")
    public String toPasswordPage() {

        return "user/password";
    }

    /**
     * 修改密码
     * @param request
     * @param oldPassword
     * @param newPassword
     * @param repeatPassword
     * @return
     */
    @PostMapping("/updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request,
                                         String oldPassword,String newPassword,String repeatPassword){
        ResultInfo resultInfo = new ResultInfo();
        //获取cookie中userId
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //调用Service层修改
        userService.updatePassword(userId,oldPassword,newPassword,repeatPassword);

        // try {
        //     //获取cookie中userId
        //     Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //     //调用Service层修改
        //     userService.updatePassword(userId,oldPassword,newPassword,repeatPassword);
        //
        // }catch (ParamsException p){
        //     resultInfo.setCode(p.getCode());
        //     resultInfo.setMsg(p.getMsg());
        //     p.printStackTrace();
        // }catch (Exception e){
        //     resultInfo.setCode(500);
        //     resultInfo.setMsg("修改密码失败");
        //     e.printStackTrace();
        // }

        return  resultInfo;
    }

    /**
     * 查询所有的销售人员
     * @return
     */
    @ResponseBody
    @RequestMapping("queryAllSales")
    public List<Map<String,Object>> queryAllSales(){

        return userService.queryAllSales();
    }

    /**
     * 分页多条件查询用户列表
     * @param userQuery
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> selectByParams(UserQuery userQuery){

        return userService.queryByParamsForTable(userQuery);
    }

    /**
     * 进入用户列表页面
     * @return
     */
    @RequestMapping("index")
    public String index(){

        return "user/user";
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addUser(User user){
        userService.addUser(user);
        return success("用户添加成功");
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("用户更新成功");
    }


    /**
     * 打卡添加或修改用户的界面
     * @return
     */
    @RequestMapping("toAddOrUpdateUserPage")
    public String toAddOrUpdateUserPage(Integer id,HttpServletRequest request){

        //判读id是否为空，不为空表示更新操作，查询用户对象
        if(id!=null){
            //通过id查询用户对象
            User user = userService.selectByPrimaryKey(id);
            //将数据设置到请求与中
            request.setAttribute("userInfo",user);

        }
        return "user/add_update";
    }

    /**
     * 用户删除
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteIds(ids);
        return success("用户删除成功");
    }

    /**
     * 查询所有的客户经理
     * @return
     */
    @ResponseBody
    @RequestMapping("queryAllCustomerManagers")
    public List<Map<String,Object>> queryAllCustomerManagers(){

        return userService.queryAllCustomerManagers();
    }


}
