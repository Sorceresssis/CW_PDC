package com.jk204.servlet.admin;

import com.jk204.dao.WorkDao;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SetAwardServlet", value = "/admin/SetAwardServlet")
@MultipartConfig
public class SetAwardServlet extends HttpServlet {
    private final WorkDao workDao = new WorkDao();
    // 最大 获奖
    private final int[] MAXIMIZE_NUMBER_PRIZES = {0, 1, 2, 3};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            // 要操作的作品id
            int workId = Integer.parseInt(request.getParameter("work_id"));
            // 获奖等级
            int awardGrade = Integer.parseInt(request.getParameter("award"));
            // 判断是否取消获奖
            if (awardGrade == 0) {
                out.print(workDao.removeAwardByWorkId(workId) ? Result.success(null) : Result.error("取消获奖失败"));
            } else {
                // 判断是否小于最大获奖数
                if (workDao.queryCountOfContestAwardByWorkId(workId, awardGrade) < MAXIMIZE_NUMBER_PRIZES[awardGrade]) {
                    out.print(workDao.addAwardByWorkId(workId, awardGrade) ? Result.success(null) : Result.error("设置获奖失败"));
                } else {
                    out.print(Result.error("该奖项已达到最大获奖数"));
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
