package com.example.manageapi.handler;

import com.alibaba.fastjson.JSON;
import com.example.manageapi.dao.dto.SysUser;
import com.example.manageapi.service.LoginService;
import com.example.manageapi.utils.UserThreadLocal;
import com.example.manageapi.vo.ErrorCode;
import com.example.manageapi.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，拦截没有登录就访问某些接口得操作，比如说点赞之类的
 *  加上Component注解让spring识别
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    /**
     * 重写preHandle方法，在执行controller方法(handler)之前进行拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1. 需要判断请求的接口路径路径 是否为controller handler
         * 2. 判断token是否为空，如果为空，未登录
         * 3. 如果token不为空，登录验证
         * 4. 认证成功，先获取用户信息，然后放行
         */
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if(token.equals("Token")){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            //往浏览器响应中写入报错的json转字符串的信息
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(result));
            System.out.println("========bug");
            System.out.println("========bug");
            System.out.println("========bug");
            System.out.println("========bug");
            System.out.println("========bug");
            return false;
        }

        SysUser sysUser = loginService.checkToken(token);

        if(sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            //往浏览器响应中写入报错的json转字符串的信息
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /**
         * 如果不删除线程池中用户信息会造成 内存泄露
         */
        UserThreadLocal.remove();
    }
}
