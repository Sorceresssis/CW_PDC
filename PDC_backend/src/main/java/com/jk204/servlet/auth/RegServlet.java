package com.jk204.servlet.auth;

import com.jk204.dao.UserDao;
import com.jk204.pojo.Result;
import com.jk204.util.SaltedHashEncryptionUtils;
import com.jk204.util.SnowflakeIdUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegServlet", value = "/auth/RegServlet")
@MultipartConfig
public class RegServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            // 验证验证码
            String captcha = request.getParameter("captcha").toUpperCase();
            // 如果在这里报了空指针异常，有可能是前端在请求时没有带上cookies
            String captchaSession = ((String) request.getSession().getAttribute("captcha")).toUpperCase();
            // 移除验证码
            request.getSession().removeAttribute("captcha");

            if (!captcha.equals(captchaSession)) {
                out.print(Result.error("验证码错误或验证码失效").toJsonString());
                return;
            }

            // 准备好注册数据
            String username = request.getParameter("username");
            String salt = SaltedHashEncryptionUtils.generateSalt();
            String pwd = SaltedHashEncryptionUtils.hashPassword(
                    request.getParameter("pwd"), salt);
            String phone = request.getParameter("phone");
            // 执行注册操作
            if (userDao.addUser(0, (new SnowflakeIdUtil(1, 1).generateId()),
                    username, pwd, salt, phone)) {
                out.print(Result.success(null));
            } else {
                // 注册失败，查找原因
                if (userDao.getUserByUsername(username) != null) {
                    out.print(Result.error("用户名已存在"));
                } else if (userDao.getUserByUsername(phone) != null) {
                    out.print(Result.error("手机号已存在"));
                } else {
                    out.print(Result.error("未知错误"));
                }
            }
        } catch (Exception e) {
            out.print(Result.error("参数错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
