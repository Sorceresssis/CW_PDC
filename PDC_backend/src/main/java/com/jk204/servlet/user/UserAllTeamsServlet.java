package com.jk204.servlet.user;

import com.jk204.dao.TeamDao;
import com.jk204.domain.Team;
import com.jk204.pojo.ContestTeamWork;
import com.jk204.pojo.Result;
import com.jk204.service.ContestTeamWorkService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 得到用户参加过的比赛的所有队伍
 */
@WebServlet(name = "UserAllTeamsServlet", value = "/user/UserAllTeamsServlet")
public class UserAllTeamsServlet extends HttpServlet {
    private final TeamDao teamDao = new TeamDao();

    private final ContestTeamWorkService contestTeamWorkService = new ContestTeamWorkService();
    private final Boolean[] OPTION = {true, false, true};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            // 获取用户参加过的比赛的所有队伍
            List<Team> teams = teamDao.queryTeamsByUserId((int) request.getSession().getAttribute("userId"));
            List<ContestTeamWork> ctws = new ArrayList<>(teams.size());
            for (Team t : teams) {
                ctws.add(contestTeamWorkService.getContestTeamWork(t, OPTION));
            }
            response.getWriter().print(Result.success(ctws));
        } catch (Exception e) {
            out.print(Result.error("系统错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
