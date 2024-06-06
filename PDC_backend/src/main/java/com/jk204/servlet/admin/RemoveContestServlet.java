package com.jk204.servlet.admin;

import com.jk204.dao.ContestDao;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RemoveContestServlet", value = "/admin/RemoveContestServlet")
public class RemoveContestServlet extends HttpServlet {
    private final ContestDao contestDao = new ContestDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            //删除竞赛
            out.print(contestDao.deleteContest(
                    Integer.parseInt(request.getParameter("contest_id"))) ?
                    Result.success(null) : Result.error("删除失败"));
        } catch (Exception e) {
            out.print(Result.error("参数错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
