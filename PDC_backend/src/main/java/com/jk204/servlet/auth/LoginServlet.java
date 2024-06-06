package com.jk204.servlet.auth;

import com.jk204.dao.UserDao;
import com.jk204.domain.User;
import com.jk204.pojo.Result;
import com.jk204.util.JwtUtils;
import com.jk204.util.SaltedHashEncryptionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet", value = "/auth/LoginServlet")
@MultipartConfig
public class LoginServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            // 用户输入的验证码
            String captcha = request.getParameter("captcha").toUpperCase();
            // 储存再session的验证码，如果在这里报了空指针异常，那么就是前端在请求是没有带上cookies
            String captchaSession = ((String) request.getSession().getAttribute("captcha")).toUpperCase();
            // 移除验证码，防止重复使用
            request.getSession().removeAttribute("captcha");

            if (!captcha.equals(captchaSession)) {
                out.print(Result.error("验证码错误").toJsonString());
                return;
            }
            // 从前端获取加密后的用户名和密码，解密后进行登录操作
            String username = request.getParameter("username");
            String pwd = request.getParameter("pwd");

            // 执行登陆操作
            User user = this.userDao.getUserByUsername(username);

            // 判断用户名和密码是否正确
            if (user != null && user.getPwd().equals(SaltedHashEncryptionUtils.hashPassword(pwd, user.getSalt()))) {
                //保存一个用户名，保存一个uid，保存一个权限rule
                // 登录成功，把用户凭证存入claims转化成token发送给前端
                Map<String, Object> claims = new HashMap<>();
                claims.put("id", user.getId());
                claims.put("username", user.getUsername());
                claims.put("uid", user.getUid());
                claims.put("rule", user.getRule());

                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(10 * 24 * 60 * 60);  // 设置session过期时间为10天。
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", username);
                session.setAttribute("uid", user.getUid());
                session.setAttribute("rule", user.getRule());

                out.print(Result.success(JwtUtils.generateJwt(claims)));
                // 如果是管理员登录，那么就重定向到管理员页面
                if (user.getRule() == 1) {
                    response.setHeader("Redirect", "./backstage/");
                }
            } else {
                // 登录失败返回错误信息
                out.print(Result.error("用户名或密码错误"));
            }
        } catch (Exception e) {
            out.print(Result.error("验证码失效"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
