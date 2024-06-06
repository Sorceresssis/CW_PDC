package com.jk204.servlet;

import com.jk204.dao.UserDao;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AutoCompleteUserByUidServlet", value = "/AutoCompleteUserByUidServlet")
public class AutoCompleteUserByUidServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print(Result.success(
                userDao.queryUserProfilesByUidLikeSearch(request.getParameter("uid"),
                        Integer.parseInt(request.getParameter("page_no")),
                        Integer.parseInt(request.getParameter("page_size")))));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
