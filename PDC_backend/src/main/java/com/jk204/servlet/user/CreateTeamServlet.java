package com.jk204.servlet.user;

import com.jk204.dao.ContestDao;
import com.jk204.dao.TeamDao;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CreateTeamServlet", value = "/user/CreateTeamServlet")
public class CreateTeamServlet extends HttpServlet {
    private final TeamDao teamDao = new TeamDao();
    private final ContestDao contestDao = new ContestDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            //获取创建队伍需要的参数
            String teamName = request.getParameter("team_name");
            int contestId = Integer.parseInt(request.getParameter("contest_id"));
            int captainId = (int) request.getSession().getAttribute("userId");
            // 判断1，是否在比赛期限内
            if (contestDao.getContestStutasById(contestId) != 2) {
                out.print(Result.error("不在报名时间内"));
                return;
            }
            // 判断2，同一比赛中队伍名称是否已存在
            if (teamDao.isExistTeamNameInContest(teamName, contestId)) {
                out.print(Result.error("队伍名称已存在，请重新填写"));
                return;
            }
            // 判断3，是否已经创建过队伍
            if (teamDao.isTeamCaptainOfContest(captainId, contestId)) {
                out.print(Result.error("该比赛中您已创建队伍，不可再次创建"));
                return;
            }

            // 开始创建队伍
            if (teamDao.createTeam(teamName, captainId, contestId)) {
                out.print(Result.success(teamDao.queryTeamByCaptainIdContestId(captainId, contestId)));
            } else {
                out.print(Result.error("未知错误，创建队伍失败"));
            }
        } catch (Exception e) {
            out.print(Result.error("系统错误"));
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
