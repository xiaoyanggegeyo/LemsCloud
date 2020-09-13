package com.msgcloud.config.Interceptor;

import com.msgcloud.config.jwt.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * 作用zuul路由网关(base模块中)  后端拦截器
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {

    //    处理request之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("============> 拦截器  处理request之前");
        //JWT验证 (已验证了是否过期)
        String tokenStr = request.getHeader("Authorization");
        boolean verifyRes = JwtUtils.verify(tokenStr);
        if (verifyRes == true) {
            return true;
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("登录过期 请重新登录!");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
