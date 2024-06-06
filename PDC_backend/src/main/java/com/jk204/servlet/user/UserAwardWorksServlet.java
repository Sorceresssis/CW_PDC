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
 * 用户所有的获奖作品, 需要返回ContestTitle, WorkFullInfo, TeamFullInfo
 * 1. 根据用户id查询所有获奖的队伍
 */
@WebServlet(name = "UserAwardWorksServlet", value = "/user/UserAwardWorksServlet")
public class UserAwardWorksServlet extends HttpServlet {
    private final TeamDao teamDao = new TeamDao();
    private final ContestTeamWorkService contestTeamWorkService = new ContestTeamWorkService();
    private final Boolean[] OPTION = {true, true, true};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            // 获取所有获奖的队伍
            List<Team> awardTeams = teamDao.queryTeamOfAwardByUserId((int) request.getSession().getAttribute("userId"));

            List<ContestTeamWork> ctws = new ArrayList<>(awardTeams.size());
            // 根据队伍获取所有的信息作品
            for (Team t : awardTeams) {
                ctws.add(contestTeamWorkService.getContestTeamWork(t, OPTION));
            }
            out.print(Result.success(ctws));
        } catch (Exception e) {
            out.print(Result.error("服务器错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
