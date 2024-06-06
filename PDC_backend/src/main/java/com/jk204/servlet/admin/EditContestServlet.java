package com.jk204.servlet.admin;

import com.jk204.dao.ContestDao;
import com.jk204.domain.Contest;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EditContestServlet", value = "/admin/EditContestServlet")
@MultipartConfig
public class EditContestServlet extends HttpServlet {
    private final ContestDao contestDao = new ContestDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            int contestId = Integer.parseInt(request.getParameter("contest_id"));
            // 接受参数
            String name = request.getParameter("name");
            String year = request.getParameter("year");
            String topic = request.getParameter("topic");
            String intro = request.getParameter("intro");
            String startingTime = request.getParameter("starting_time");
            String registrationDeadline = request.getParameter("registration_deadline");
            String resultAnnouncementTime = request.getParameter("result_announcement_time");
            int categoryId = Integer.parseInt(request.getParameter("category_id"));
            Contest contest = new Contest(name, year, topic, intro, startingTime, registrationDeadline, resultAnnouncementTime, categoryId);
            if (contestId == 0) {
                out.print(contestDao.addContest(contest) ? new Result(1, "添加成功", null) : Result.error("添加失败"));
            } else {
                out.print(contestDao.editContest(contestId, contest) ? new Result(1, "修改成功", null) : Result.error("修改失败"));
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
