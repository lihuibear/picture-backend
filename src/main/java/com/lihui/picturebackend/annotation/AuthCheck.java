package com.lihui.picturebackend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {


    /**
     * 必须有某个角色
     */
    String mustRole() default "";

    /**
     * 需要的多个权限（满足其中一个即可）
     */
    String[] mustRoles() default {};



}
