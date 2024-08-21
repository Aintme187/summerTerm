package com.example.blogapi.common.aop;

import java.lang.annotation.*;

//TYPE放在类上，METHOD放在方法上
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default "";
    String operator() default "";
}
