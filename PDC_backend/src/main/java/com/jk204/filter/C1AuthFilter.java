package com.jk204.filter;

import com.jk204.pojo.Result;
import com.jk204.util.JwtUtils;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "C1AuthFilter", urlPatterns = {"/admin/*", "/user/*"})
public class C1AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        /* * * * * * * * * * 筛选用户 * * * * * * * * * */
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            // 解析jwtToken
            Claims claims = JwtUtils.parseJwt(req.getHeader("Authorization"));
            // 用户id,用户名,权限信息。写入session
            HttpSession session = req.getSession();
            session.setAttribute("userId", claims.get("id"));
            session.setAttribute("username", claims.get("username"));
            session.setAttribute("uid", claims.get("uid"));
            session.setAttribute("rule", claims.get("rule"));
            // 如果是去/admin/*，则判断权限
            if (req.getRequestURI().contains("/admin/")) {
                if ((int) claims.get("rule") == 1) {
                    chain.doFilter(request, response);
                } else {
                    // 没有权限重定向到首页
                    resp.setHeader("Redirect", "/PDC/");
                }
                return;
            }
            // 普通用户直接放行
            chain.doFilter(request, response);
        } catch (Exception e) {
            /*
             *  踩坑：filter 的catch能够捕获的到chain.doFilter放行到servlet，servlet产生的的异常，
             *  用来捕获jwt解析异常却捕获到了servlet中的异常，导致进行了错误的异常处理发送了重定向要求，
             *  所以在servlet中要自己处理异常，不能偷懒。
             */
            // jwtToken解析失败说明token过期或遭到了修改。重定向到登录页面
            resp.getWriter().print(Result.error("NOT_LOGIN"));
        }
    }
}