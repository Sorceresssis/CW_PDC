package com.jk204.servlet;

import com.jk204.dao.ContestDao;
import com.jk204.pojo.ContestDetails;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ContestDetailServlet", value = "/ContestDetailServlet")
public class ContestDetailServlet extends HttpServlet {
    private final ContestDao contestDao = new ContestDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            int contestId = Integer.parseInt(request.getParameter("contest_id"));
            ContestDetails contestd = contestDao.ContestDetailById(contestId);
            out.print(contestd == null ? Result.error("数据不存在") : Result.success(contestd));
        } catch (Exception e) {
            out.print(Result.error("系统错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
