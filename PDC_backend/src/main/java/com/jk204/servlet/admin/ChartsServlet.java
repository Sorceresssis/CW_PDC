package com.jk204.servlet.admin;

import com.jk204.dao.ContestDao;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ChartsServlet", value = "/admin/ChartsServlet")
public class ChartsServlet extends HttpServlet {
    private final ContestDao contestDao = new ContestDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 分类统计
        List<Object> charts = new ArrayList<>();
        charts.add(contestDao.queryCountContestGroupByCategory());
        response.getWriter().print(Result.success(charts));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
