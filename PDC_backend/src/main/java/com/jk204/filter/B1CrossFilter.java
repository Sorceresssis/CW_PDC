package com.jk204.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "B1CrossFilter", urlPatterns = "/*")
public class B1CrossFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        /* * * * * * * * * * 解决跨域和编码问题 * * * * * * * * * */
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        /* 编码 */
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        /* 标头 */
        // 允许访问的方式
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        // 解决跨域问题 设置响应头允许axios跨域访问
        // 获取发送请求的域名,把允许的域名设置进去 即运行全部跨域请求
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        // 是否允许请求带有验证信息
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        // 设置超时时间
        resp.setHeader("Access-Control-Max-Age", "3600");
        // Authorization 是jwtToken
        resp.setHeader("Access-Control-Allow-Headers", "Authorization, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        // 设置可以响应头可以暴露的值：Redirect用于重定向, Authorization更新token
        resp.setHeader("Access-Control-Expose-Headers", "Redirect, Authorization");
        chain.doFilter(request, response);
    }
}
