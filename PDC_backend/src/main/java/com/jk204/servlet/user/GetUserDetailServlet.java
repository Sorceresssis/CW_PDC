package com.jk204.servlet.user;

import com.jk204.dao.UserDao;
import com.jk204.domain.User;
import com.jk204.pojo.Result;
import com.jk204.pojo.UserNormalDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetUserDetailServlet", value = "/user/GetUserDetailServlet")
public class GetUserDetailServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户普通信息
        PrintWriter out = response.getWriter();
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                out.print(Result.error("未登录"));
                return;
            }
//            out.print(Result.success(userDao.getUserDetail(userId)));
            User user = userDao.queryUserById(userId);
            out.print(Result.success(new UserNormalDetails(
                    user.getNickname(), user.getUsername(), user.getPhone(), user.getUid()
            )));
        } catch (Exception e) {
            out.print(Result.error("服务器错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
