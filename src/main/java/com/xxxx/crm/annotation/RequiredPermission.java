package com.xxxx.crm.annotation;

import java.lang.annotation.*;

/**
 * 定义方法需要的对应的资源权限码
 * @Author xiaokaixin
 * @Date 2021/9/17 18:48
 * @Version 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {

    //权限码
    String code() default "";
}
