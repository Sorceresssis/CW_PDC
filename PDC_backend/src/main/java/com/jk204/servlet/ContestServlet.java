package com.jk204.servlet;

import com.jk204.dao.ContestDao;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ContestServlet", value = "/ContestServlet")
public class ContestServlet extends HttpServlet {
    ContestDao contestDao = new ContestDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            int categoryId = Integer.parseInt(request.getParameter("category_id"));
            int pageNo = Integer.parseInt(request.getParameter("page_no"));
            int pageSize = Integer.parseInt(request.getParameter("page_size"));
            out.print(Result.success(categoryId == 0 ?
                    contestDao.queryAllContest(pageNo, pageSize)
                    : contestDao.queryContestsByCategoryId(categoryId, pageNo, pageSize)));
        } catch (Exception e) {
            out.print(Result.error("参数传递错误或服务器内部错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
