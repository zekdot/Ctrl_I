package com.ctrl_i.springboot.filter;

import com.ctrl_i.springboot.ConfigConstant;
import com.ctrl_i.springboot.dto.Envelope;
import com.ctrl_i.springboot.entity.UserEntity;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 评论相关的过滤器
 * Create by zekdot on 19-5-21.
 */
@CrossOrigin
@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/comment/*")
public class CommentsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("进入到过滤器中");
        String contain[] = {"getComByaId"};//过滤白名单
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", new StringBuilder().append("http://").append(ConfigConstant.ip).append(":").append(ConfigConstant.fport).toString());
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        // 允许跨域请求中携带cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
//        String origin = request.getHeader("Origin");
//        if(origin == null) {
//            origin = request.getHeader("Referer");
//        }
//        response.setHeader("Access-Control-Allow-Origin", origin);            // 允许指定域访问跨域资源
//        response.setHeader("Access-Control-Allow-Credentials", "true");       // 允许客户端携带跨域cookie，此时origin值不能为“*”，只能为指定单一域名
//
//        if(RequestMethod.OPTIONS.toString().equals(request.getMethod())) {
//            String allowMethod = request.getHeader("Access-Control-Request-Method");
//            String allowHeaders = request.getHeader("Access-Control-Request-Headers");
//            response.setHeader("Access-Control-Max-Age", "86400");            // 浏览器缓存预检请求结果时间,单位:秒
//            response.setHeader("Access-Control-Allow-Methods", allowMethod);  // 允许浏览器在预检请求成功之后发送的实际请求方法名
//            response.setHeader("Access-Control-Allow-Headers", allowHeaders); // 允许浏览器发送的请求消息头
//            return;
//        }
        UserEntity user = (UserEntity) session.getAttribute("user");    //从session中获取user对象
        String path = request.getRequestURI();
        if (!path.contains("comment")) {   //如果不是评论相关的URL
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        for (int i = 0; i < contain.length; i++) {  //检测是否位于白名单中
            if (path.contains(contain[i])) {    //如果位于白名单中
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        if (user == null) {//如果未登录
            response.getWriter().println(new Envelope(1024, "请先登录！", null));  //回复信息
            return;
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
